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
System.out.println("Size: "+dlMembers.size());
for(MemberDTO memberDTO:dlMembers)
{
member=new Member();
member.setUsername(memberDTO.getUsername());
member.setPassword(memberDTO.getPassword());
System.out.println(member.getUsername()+", "+member.getPassword());
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
public void logout(String username)
{
loggedInMembers.remove(username);
//playingMembers.remove(username);
}

@Path("/getMembers")
public java.util.List<String> getMembers(String username)
{
java.util.List<String> availableMembers=new LinkedList<>();
for(String user:loggedInMembers)
{
if(playingMembers.contains(user)==false && user.equals(username)==false) availableMembers.add(user);
}
return availableMembers;
}
@Path("/inviteMember")
public void inviteMember(String fromUsername,String toUsername)
{
Message message=new Message();
message.setFromUsername(fromUsername);
message.setToUsername(toUsername);
message.setMessageType(MESSAGE_TYPE.CHALLENGE);
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
}
