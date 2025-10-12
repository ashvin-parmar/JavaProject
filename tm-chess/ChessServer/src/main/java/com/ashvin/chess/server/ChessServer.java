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
this.loggedInMembers=new HashSet<>();
this.playingMembers=new HashSet<>();
this.inboxes=new HashMap<>();
this.games=new HashMap<>();
}
public boolean isMemberAuthentic(String username,String password)
{
Member member=this.members.get(username);
if(member==null || password.equals(member.password)==false) return false;
this.loggedInMembers.add(username);
return true;
} 
public void logout(String username)
{
this.loggedInMembers.remove(username);
//this.playingMembers.remove(username);
}
public List<String> getAvailableMember(String username)
{
List<String> availableMembers=new LinkedList<>();
for(String u:this.loggedInMembers)
{
if(playingMembers.contains(u)==false && u.equals(username)==false) availableMembers.add(username);
}
return availableMembers;
}
public void inviteMember(String fromUsername,String toUsername)
{
Message message=new Message();
message.fromUsername=fromUsername;
message.toUsername=toUsername;
message.type=MESSAGE_TYPE.CHALLENGE;
List<Message> messages=this.inboxes.get(toUsername);
if(messages==null)
{
messages=new LinkedList<Message>();
this.inboxes.put(toUsername,messages);
}
messages.add(message);
}
public List<Message> getMessages(String username)
{
List<Message> messages=this.inboxes.get(username);
if(messages!=null && messages.size()>0)
{
inboxes.put(username,new LinkedList<Message>());
}
return messages;
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
