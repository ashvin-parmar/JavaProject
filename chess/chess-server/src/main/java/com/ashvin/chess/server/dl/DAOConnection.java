package com.ashvin.chess.server.dl;

import java.sql.*;

public class DAOConnection
{
private DAOConnection()
{

}
public static Connection getDAOConnection() throws SQLException
{
Connection connection=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/chessdb","chessuser01","ChessUser#01");
return connection;
}catch(SQLException sqlException)
{
throw sqlException;
}catch(Exception e)
{
throw new SQLException(e.getMessage());
}
}
}
