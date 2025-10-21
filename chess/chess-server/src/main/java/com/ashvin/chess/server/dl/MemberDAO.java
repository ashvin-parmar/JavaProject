package com.ashvin.chess.server.dl;

import java.sql.*;
import java.util.*;

public class MemberDAO 
{
private static MemberDAO memberDAO=null;
private MemberDAO()
{

}
public static MemberDAO getMemberDAO()
{
if(memberDAO==null) memberDAO=new MemberDAO();
return memberDAO;
}
public java.util.List<MemberDTO> getAll()
{
java.util.List<MemberDTO> members=new LinkedList<>();
MemberDTO memberDTO=null;
String username;
String password;
try
{
Connection connection=DAOConnection.getDAOConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from Member");
while(resultSet.next())
{
memberDTO=new MemberDTO();
username=resultSet.getString("uname").trim();
password=resultSet.getString("pwd").trim();
memberDTO.setUsername(username);
memberDTO.setPassword(password);
members.add(memberDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{
members.clear();
//System.out.println(sqlException.getMessage());
}
return members;
}

//More methods here
}
