package com.ashvin.chess.server;

import java.util.*;
import com.ashvin.chess.server.dl.*;
import com.ashvin.nframework.server.*;
import com.ashvin.nframework.server.annotations.*;

@Path("/ChessServer")
public class ChessServer
{
static private Map<String,Member> members;
static private  Set<String> loggedInMembers;
private static Set<String> playingMembers;
private static Map<String,List<Message>> inboxes;
private static Map<String,Game> games;
public ChessServer()
{
}
static
{
populateDataStructures();
}
private static void populateDataStructures()
{
Member member;
members=new HashMap<>();
MemberDAO memberDAO=new MemberDAO();
List<MemberDTO> dlMembers=memberDAO.getAll();
for(MemberDTO memberDTO:dlMembers)
{
member=new Member();
member.username=memberDTO.username;
member.password=memberDTO.password;
members.put(memberDTO.username,member);
}
loggedInMembers=new HashSet<>();
playingMembers=new HashSet<>();
inboxes=new HashMap<>();
games=new HashMap<>();
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
//playingMembers.remove(username);
}
@Path("/getMembers")
public List<String> getAvailableMember(String username)
{
List<String> availableMembers=new LinkedList<>();
for(String u:loggedInMembers)
{
if(playingMembers.contains(u)==false && u.equals(username)==false) availableMembers.add(u);
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
List<Message> messages=inboxes.get(toUsername);
if(messages==null)
{
messages=new LinkedList<Message>();
inboxes.put(toUsername,messages);
}
messages.add(message);
}
@Path("/getMessages")
public List<Message> getMessages(String username)
{
List<Message> messages=inboxes.get(username);
if(messages!=null && messages.size()>0)
{
inboxes.put(username,new LinkedList<Message>());
}
return messages;
}
@Path("/getGameId")
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
@Path("/canIPlay")
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
}
