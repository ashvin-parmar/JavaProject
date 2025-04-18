package com.ashvin.hr.nexus.dl.dao;
import java.sql.*;
import com.ashvin.hr.nexus.dl.exceptions.*;
public class DAOConnection
{
private DAOConnection(){}
public static Connection getConnection() throws DAOException
{
Connection connection=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/hr_nexus_db","hrnexususer1","HR-Nexus#user1");
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return connection;
}
}
