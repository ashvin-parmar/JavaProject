package com.ashvin.chess.server;

import java.util.*;
import com.ashvin.chess.server.dl.*;

public class ChessServer
{
public Map<String,Member> members;
public Set<String> loggedInMembers;
public Set<String> playingMembers;
public Map<String,List<Message>> inboxes;
public Map<String,Game> games;
public ChessServer()
{
populateDataStructures();
}
private void populateDataStructures()
{
Member member;
this.members=new HashMap<>();
MemberDAO memberDAO=new MemberDAO();
List<MemberDTO> members=memberDAO.getAll();
for(MemberDTO memberDTO:members)
{
member=new Member();
member.username=memberDTO.username;
member.password=memberDTO.password;
this.members.put(memberDTO.username,member);
}
}


public boolean isUserAuthentic(String username,String password)
{
return false;
} 
public void logout(String username)
{

}
public List<String> getAvailableUser(String username)
{
return null;
}
public void inviteUser(String fromUsername,String toUsername)
{

}
public List<Message> getMessages(String username)
{
return null;
}
public String getGameId(String username)
{
return "abc";
}
/*
public void rejectInvitation(String byUsername,String invitedByUsername)
{
}
public void acceptInvitation(String byUsername,String invitedByUsername)
{
}
*/
public boolean canIPlay(String gameId,String username)
{
return false;
}
public void submitMove(String byUsername,byte piece,int fromX,int fromY,int toX,int toY)
{

}
public Move getOpponentMove(String username)
{
return null;
}



//Create serviecs to enable client to perform login/logout actions

/*
public static void main(String gg[])
{
new ChessServer();
}
*/
}
