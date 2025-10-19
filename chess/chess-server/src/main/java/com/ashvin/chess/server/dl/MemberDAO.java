package com.ashvin.chess.server.dl;

import java.sql.*;

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
public List<MemberDTO> getAll()
{
List<MemberDTO> members=new LinkedList<>();
MemberDTO memberDTO=null;
String username;
String password;
try
{
Connection connection=DAOConnection.getDAOConnection();
Statement statement=c.getStatement();
ResultSet resultSet=statement.executeQuery("select * from Member");
while(resultSet.next())
{
memberDTO=new MemberDTO();
username=resultSet.getString("uname");
password=resultSet.getString("pwd");
memberDTO.setUsername(username);
memberDTO.setPassword(password);
members.add(memberDTO);
}
resultSet.close();
statment.close();
connection.close();
}catch(SQLException sqlException)
{
members.clear();
}
return members;
}

//More methods here
}
