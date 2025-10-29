package com.ashvin.chess.server;

import com.ashvin.nframework.server.*;
import com.ashvin.nframework.server.annotations.*;
import com.ashvin.chess.common.*;
import com.ashvin.chess.server.dl.*;
import java.util.*;

@Path("/ChessServer")
public class ChessServer
{
private Map<String,Member> members;
private Set<String> loggedInMembers;
private Set<String> playingMembers;
private Map<String,java.util.List<Message>> inboxes;
private Map<String,Game> games;
private static ChessServer chessServer;
private ChessServer()
{
populateDataStructures();
}
public static ChessServer getChessServer()
{
if(chessServer==null) chessServer=new ChessServer();
return chessServer;
}
private void populateDataStructures()
{
members=new HashMap<>();
Member member=null;
java.util.List<MemberDTO> dlMembers=(MemberDAO.getMemberDAO().getAll());
for(MemberDTO memberDTO:dlMembers)
{
member=new Member();
member.setUsername(memberDTO.getUsername());
member.setPassword(memberDTO.getPassword());
this.members.put(member.getUsername(),member);
}
loggedInMembers=new HashSet<>();
playingMembers=new HashSet<>();
inboxes=new HashMap<>();
games=new HashMap<>();
}


//public methods
@Path("/authenticUser")
public boolean isUserAuthentic(String username,String password)
{
if(username==null || password==null) return false;
username=username.trim();
Member member=this.members.get(username);
if(member==null) return false;
String pass=member.getPassword();
if(pass!=null) return pass.equals(password);
return false;
}
@Path("/login")
public boolean login(String username,String password)
{
if(username==null || password==null) return false;
username=username.trim();
boolean authenticUser=chessServer.isUserAuthentic(username,password);
if(authenticUser)
{
loggedInMembers.add(username);
}
return authenticUser;
}
@Path("/logout")
public void logout(String username)
{
loggedInMembers.remove(username);
java.util.List<Message> currentInbox=inboxes.get(username);
if(currentInbox!=null) currentInbox.clear();
/*
String toUsernames[]=inboxes.getKeys();
for(int i=0;i<toUsernames.size();i++)
{
inbox=inboxes.get(toUsernames.get(i));
for(Message message:inbox)
{
if(message.getFromUsername().equals(username))
{
//inbox.remove(message);
message.setMessageType(MESSAGE_TYPE.NOT_AVAILABLE);
}
}
}
*/
inboxes.forEach((toUsername,inbox) ->{
for(Message message:inbox)
{
if(message.getFromUsername().equals(username))
{
//inbox.remove(message);
message.setMessageType(MESSAGE_TYPE.NOT_AVAILABLE);
}
}
});
//playingMembers.remove(username);
}
/*
private void removeMessage(java.util.List<Message> inbox,String username)
{
}
*/
@Path("/getMembers")
public java.util.List<String> getMembers(String username)
{
java.util.List<String> availableMembers=new LinkedList<>();
for(String user:loggedInMembers)
{
if(/*playingMembers.contains(user)==false &&*/ user.equals(username)==false) availableMembers.add(user);
}
return availableMembers;
}
@Path("/inviteMember")
public void inviteMember(Message message)
{
if(message==null) return;
List<Message> messages=inboxes.get(message.getToUsername());
if(messages==null)
{
messages=new LinkedList<Message>();
inboxes.put(message.getToUsername(),messages);
}
messages.add(message);
}
@Path("/getInvitationStatus")
public MESSAGE_TYPE getInvitationStatus(String fromUsername,String toUsername)
{
MESSAGE_TYPE messageType=MESSAGE_TYPE.NOT_AVAILABLE;
List<Message> messages=inboxes.get(toUsername);
if(messages==null)
{
messages=new LinkedList<Message>();
inboxes.put(toUsername,messages);
}
for(Message message:messages)
{
if(message.getFromUsername().equals(fromUsername))
{
messageType=message.getMessageType();
break;
}
}
return messageType;
}
@Path("/acceptInvitation")
public void acceptMember(Message message)
{
List<Message> messages=inboxes.get(message.getToUsername());
if(messages==null)
{
return ;
}
messages.clear();
messages.add(message);
System.out.println("Member accepted");
}
@Path("/rejectInvitation")
public void rejectMember(Message message)
{
List<Message> messages=inboxes.get(message.getToUsername());
if(messages==null)
{
messages=new LinkedList<Message>();
}
//messages.remove(message);
String fromUsername=message.getFromUsername();
messages.forEach((m)->{
if(m.getFromUsername().equals(fromUsername))
{
m.setMessageType(MESSAGE_TYPE.CHALLENGE_REJECTED);
return;
}
});
}
@Path("/getMessagesToUsernames")
public List<String> getMessagesToUsernames(String toUsername)
{
List<Message> messages=inboxes.get(toUsername);
if(messages==null)
{
messages=new LinkedList<>();
inboxes.put(toUsername,messages);
}
List<String> fromUsernames=new LinkedList<>();
for(Message message:messages)
{
//if(message.getMessageType()!=MESSAGE_TYPE.CHALLENGE)
 fromUsernames.add(message.getFromUsername());
}
return fromUsernames;
}
}
