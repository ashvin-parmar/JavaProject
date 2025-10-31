package com.ashvin.accounting.dbdl.dao;

import com.ashvin.accounting.dbdl.dto.*;
import java.sql.*;
import java.util.*;
import java.math.*;

public class ReceiptDAO
{
public ReceiptDAO()
{
}
public void addReceipt(Receipt receipt) throws DAOException
{
if(receipt==null) throw new DAOException("Receipt required");
java.util.Date receiptDate=receipt.getReceiptDate();
if(receiptDate==null) throw new DAOException("Receipt Date required");
int customerCode=receipt.getCustomerCode();
if(customerCode<=0) throw new DAOException("Invalid customer code");
int amount=receipt.getAmount();
if(amount<=0) throw new DAOException("Amount cannot be negative or zero");
java.sql.Date sqlReceiptDate=new java.sql.Date(receiptDate.getYear(),receiptDate.getMonth(),receiptDate.getDate());
sqlReceiptDate.setTime(receiptDate.getTime());
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("insert into receipt (receipt_date,customer_code,amount) values(?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setDate(1,sqlReceiptDate);
preparedStatement.setInt(2,customerCode);
preparedStatement.setInt(3,amount);
preparedStatement.executeUpdate();
ResultSet resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int receiptNumber=resultSet.getInt(1);
receipt.setReceiptNumber(receiptNumber);
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update customer set total_receipt=total_receipt+? where code=?");
preparedStatement.setBigDecimal(1,new BigDecimal(amount));
preparedStatement.setInt(2,customerCode);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}
