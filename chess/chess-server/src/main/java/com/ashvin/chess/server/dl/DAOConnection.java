package com.ashvin.chess.server.dl;

import java.sql.*;

public class DAOConnection
{
private static Connection connection;
private DAOConnection()
{

}
public static Connection getDAOConnection() throws SQLException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/chessdb","chessuser01","ChessUser#01");
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
