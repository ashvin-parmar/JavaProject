package com.ashvin.accounting.dbdl.dao;

import com.ashvin.accounting.dbdl.dto.*;
import java.sql.*;

public class SupplierDAO
{
public void addSupplier(Supplier supplier) throws DAOException
{
if(supplier==null) throw new DAOException("Supplier required");
String name=supplier.getName();
if(name==null) throw new DAOException("Supplier name required");
name=name.trim();
if(name.length()==0) throw new DAOException("Supplier name required");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("insert into supplier (name,total_purchase,total_payment) values(?,0.0,0.0)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.executeUpdate();
ResultSet resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int code=resultSet.getInt(1);
supplier.setCode(code);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}
