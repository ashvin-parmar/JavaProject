package com.ashvin.chess.server;

import com.ashvin.nframework.server.*;
import com.ashvin.nframework.common.*;

@Path("\ChessServer")
public class ChessServer
{
private Map<String,Member> members;
private Set<String> loggedInMembers;
private Set<String> playingMembers;
private Map<String,List<Message>> inboxes;
private Map<String,Game> games;
private static ChessServer chessServer;
private ChessServer()
{
members=new HashMap<>();
loggedInMembers=new HashSet<>();
playingMembers=new HashSet<>();
inboxes=new HashMap<>();
games=new HashMap<>();
}
public static ChessServer getChessServer()
{
if(chessServer==null) chessServer=new ChessServer();
return chessServer;
}


public boolean isUserAuthentic(String username,String password)
{
if(username==null || password==null) return false;
username=username.trim();
MemberDTO member=new MemberDTO();
member.setUsername(username);
member.setPassword(password);
return this.members.get(member)!=null;
}
public boolean login(String username,String password)
{
return isUserAuthentic(username,password);
}
public void logout(String username)
{

}
@Path("\getMembers")
public List<Member> getMembers()
{
List<Member> members=new LinkedList<>();

}


}
