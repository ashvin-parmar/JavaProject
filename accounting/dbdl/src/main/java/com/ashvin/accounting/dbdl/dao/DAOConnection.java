package com.ashvin.accounting.dbdl.dao;
import java.sql.*;

public class DAOConnection
{
public static Connection getConnection() throws DAOException
{
Connection connection=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/accountingdb","accountinguser1","accounting#User1");
return connection;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}
}
