package com.ashvin.accounting.dbdl.dao;

import com.ashvin.accounting.dbdl.dto.*;
import java.sql.*;

public class CustomerDAO
{
public void addCustomer(Customer customer) throws DAOException
{
if(customer==null) throw new DAOException("Customer required");
String name=customer.getName();
if(name==null) throw new DAOException("Customer name required");
name=name.trim();
if(name.length()==0) throw new DAOException("Customer name required");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("insert into customer (name) values(?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.executeUpdate();
ResultSet resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int code=resultSet.getInt(1);
customer.setCode(code);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}
