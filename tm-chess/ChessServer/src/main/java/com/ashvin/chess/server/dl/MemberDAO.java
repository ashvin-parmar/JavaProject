package com.ashvin.chess.server.dl;
import java.util.*;

public class MemberDAO 
{
public List<MemberDTO> getAll()
{
List<MemberDTO> members=new LinkedList<>();
MemberDTO member;
member=new MemberDTO();
member.username="Ajay";
member.password="ajay";
members.add(member);
member=new MemberDTO();
member.username="Bob";
member.password="bob";
members.add(member);
member=new MemberDTO();
member.username="suman";
member.password="suman";
members.add(member);
member=new MemberDTO();
member.username="Sweta";
member.password="sweta";
members.add(member);
member=new MemberDTO();
member.username="Priyal";
member.password="priyal";
members.add(member);
member=new MemberDTO();
member.username="Yashraj";
member.password="yashraj";
members.add(member);
member=new MemberDTO();
member.username="Aman";
member.password="aman";
members.add(member);
member=new MemberDTO();
member.username="Ankit";
member.password="ankit";
members.add(member);
return members;
}
}
