package com.ashvin.chess.server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import com.ashvin.chess.server.dl.*;
import com.ashvin.nframework.server.*;
import com.ashvin.nframework.server.annotations.*;

@Path("/ChessServer")
public class ChessServer
{
    static private Map<String,Member> members;
    static private Set<String> loggedInMembers;
    private static Set<String> playingMembers;
    private static Map<String,List<Message>> inboxes;
    private static Map<String,Game> games;

    public ChessServer() {}

    static
    {
        populateDataStructures();
    }

    private static void populateDataStructures()
    {
        Member member;
        members=new ConcurrentHashMap<>();
        MemberDAO memberDAO=new MemberDAO();
        List<MemberDTO> dlMembers=memberDAO.getAll();
        for(MemberDTO memberDTO:dlMembers)
        {
            member=new Member();
            member.username=memberDTO.username;
            member.password=memberDTO.password;
            members.put(memberDTO.username,member);
        }
        loggedInMembers=Collections.synchronizedSet(new HashSet<>());
        playingMembers=Collections.synchronizedSet(new HashSet<>());
        inboxes=new ConcurrentHashMap<>();
        games=new ConcurrentHashMap<>();
    }

    @Path("/memberAuthentic")
    public boolean isMemberAuthentic(String username,String password)
    {
        Member member=members.get(username);
        if(member==null || password.equals(member.password)==false) return false;
        loggedInMembers.add(username);
        return true;
    }

    @Path("/logout")
    public void logout(String username)
    {
        loggedInMembers.remove(username);
        playingMembers.remove(username);
        
        // End any active game they are in, so the opponent isn't stuck forever
        for (Game game : games.values()) {
            if (game.player1.equals(username) || game.player2.equals(username)) {
                endGame(game.id, username + " disconnected. You win!");
            }
        }
    }

    @Path("/getMembers")
    public List<String> getAvailableMember(String username)
    {
        List<String> availableMembers=new LinkedList<>();
        synchronized(loggedInMembers) {
            for(String u:loggedInMembers)
            {
                if(playingMembers.contains(u)==false && u.equals(username)==false) availableMembers.add(u);
            }
        }
        return availableMembers;
    }

    @Path("/inviteMember")
    public void inviteMember(String fromUsername,String toUsername)
    {
        Message message=new Message();
        message.fromUsername=fromUsername;
        message.toUsername=toUsername;
        message.type=MESSAGE_TYPE.CHALLENGE;
        inboxes.computeIfAbsent(toUsername, k -> Collections.synchronizedList(new LinkedList<>())).add(message);
    }

    @Path("/getMessages")
    public List<Message> getMessages(String username)
    {
        List<Message> messages=inboxes.get(username);
        if(messages!=null && messages.size()>0)
        {
            List<Message> copy;
            synchronized(messages) {
                copy = new LinkedList<>(messages);
                messages.clear();
            }
            return copy;
        }
        return null;
    }

    @Path("/getGameId")
    public String getGameId(String username)
    {
        for(Game game:games.values()) {
            if(game.player1.equals(username) || game.player2.equals(username)) return game.id;
        }
        return null;
    }

    @Path("/acceptInvitation")
    public void acceptInvitation(String byUsername,String invitedByUsername)
    {
        Message message=new Message();
        message.fromUsername=byUsername;
        message.toUsername=invitedByUsername;
        
        // Prevent race condition if either user entered another game
        if (playingMembers.contains(byUsername) || playingMembers.contains(invitedByUsername)) {
            message.type=MESSAGE_TYPE.CHALLENGE_REJECTED;
            inboxes.computeIfAbsent(invitedByUsername, k -> Collections.synchronizedList(new LinkedList<>())).add(message);
            return;
        }
        
        message.type=MESSAGE_TYPE.CHALLENGE_ACCEPTED;
        inboxes.computeIfAbsent(invitedByUsername, k -> Collections.synchronizedList(new LinkedList<>())).add(message);
        
        playingMembers.add(byUsername);
        playingMembers.add(invitedByUsername);

        Game game=new Game();
        game.id=UUID.randomUUID().toString();
        game.player1=invitedByUsername; // Player 1 (sender of challenge) is white
        game.player2=byUsername; // Player 2 (accepter) is black
        game.activePlayer=1; // 1 for player1, 2 for player2
        game.moves=Collections.synchronizedList(new LinkedList<Move>());
        games.put(game.id,game);
    }

    @Path("/rejectInvitation")
    public void rejectInvitation(String byUsername,String invitedByUsername)
    {
        Message message=new Message();
        message.fromUsername=byUsername;
        message.toUsername=invitedByUsername;
        message.type=MESSAGE_TYPE.CHALLENGE_REJECTED;
        inboxes.computeIfAbsent(invitedByUsername, k -> Collections.synchronizedList(new LinkedList<>())).add(message);
    }

    @Path("/canIPlay")
    public boolean canIPlay(String gameId,String username)
    {
        Game game=games.get(gameId);
        if(game==null) return false;
        if(game.player1.equals(username) && game.activePlayer==1) return true;
        if(game.player2.equals(username) && game.activePlayer==2) return true;
        return false;
    }

    @Path("/submitMove")
    public void submitMove(String gameId, String byUsername,byte piece,byte fromX,byte fromY,byte toX,byte toY)
    {
        Game game=games.get(gameId);
        if(game==null) return;
        Move move=new Move();
        move.player= (byte) (game.player1.equals(byUsername)?1:2);
        move.piece=piece;
        move.fromX=fromX;
        move.fromY=fromY;
        move.toX=toX;
        move.toY=toY;
        game.moves.add(move);
        game.activePlayer=(byte)(game.activePlayer==1?2:1);
    }

    @Path("/getOpponentMove")
    public Move getOpponentMove(String gameId, String username)
    {
        Game game=games.get(gameId);
        if(game==null || game.moves.size()==0) return null;
        Move lastMove;
        synchronized(game.moves) {
             lastMove = game.moves.get(game.moves.size()-1);
        }
        byte myPlayerId = (byte) (game.player1.equals(username)?1:2);
        if(lastMove.player != myPlayerId) return lastMove;
        return null;
    }
    
    @Path("/getMoveCount")
    public int getMoveCount(String gameId) {
        Game game = games.get(gameId);
        if (game == null) return 0;
        return game.moves.size();
    }
    
    @Path("/getMoveAt")
    public Move getMoveAt(String gameId, int index) {
        Game game = games.get(gameId);
        if (game == null || index < 0 || index >= game.moves.size()) return null;
        return game.moves.get(index);
    }
    
    private static Map<String, String> gameOutcomes = new ConcurrentHashMap<>();
    
    @Path("/isGameActive")
    public boolean isGameActive(String gameId)
    {
        return games.containsKey(gameId);
    }
    
    @Path("/getGameOutcome")
    public String getGameOutcome(String gameId) {
        return gameOutcomes.get(gameId);
    }
    
    @Path("/endGame")
    public void endGame(String gameId, String outcome)
    {
        Game game = games.remove(gameId);
        if (game != null) {
            playingMembers.remove(game.player1);
            playingMembers.remove(game.player2);
            gameOutcomes.put(gameId, outcome);
        }
    }
}
