package com.ashvin.chess.server;

import java.util.*;
import com.ashvin.chess.server.dl.*;

public class ChessServer
{
public Map<String,Member> members;
public Set<String> loggedInMembers;
public Set<String> playingMembers;
public Map<String,Message> inboxes;
public Map<String,Game> games;
public ChessServer()
{
populateDataStructures();
}
public void populateDataStructures()
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
//Create serviecs to enable client to perform login/logout actions

/*
public static void main(String gg[])
{
new ChessServer();
}
*/
}
