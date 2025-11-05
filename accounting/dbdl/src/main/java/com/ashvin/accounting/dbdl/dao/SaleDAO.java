package com.ashvin.accounting.dbdl.dao;

import com.ashvin.accounting.dbdl.dto.*;
import java.sql.*;
import java.util.*;
import java.math.*;

public class SaleDAO
{
public SaleDAO()
{
}
public void addSale(Sale sale) throws DAOException
{
if(sale==null) throw new DAOException("Sale required");
java.util.Date billDate=sale.getBillDate();
if(billDate==null) throw new DAOException("Bill Date required");
int customerCode=sale.getCustomerCode();
if(customerCode<=0) throw new DAOException("Invalid customer code");
int itemCode=sale.getItemCode();
if(itemCode<=0) throw new DAOException("Invalid item code");
int quantity=sale.getQuantity();
if(quantity<=0) throw new DAOException("Quantity cannot be negative or zero");
int rate=sale.getRate();
if(rate<=0) throw new DAOException("Rate cannot be negative or zero");
java.sql.Date sqlBillDate=new java.sql.Date(billDate.getYear(),billDate.getMonth(),billDate.getDate());
sqlBillDate.setTime(billDate.getTime());
//System.out.println(sqlBillDate);
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("insert into sale (bill_date,customer_code,item_code,quantity,rate) values(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setDate(1,sqlBillDate);
preparedStatement.setInt(2,customerCode);
preparedStatement.setInt(3,itemCode);
preparedStatement.setInt(4,quantity);
preparedStatement.setInt(5,rate);
preparedStatement.executeUpdate();
ResultSet resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int billNumber=resultSet.getInt(1);
sale.setBillNumber(billNumber);
resultSet.close();
preparedStatement.close();
//preparedStatement=connection.prepareStatement("update customer set total_sale=total_sale+? where code=?");
//preparedStatement.setBigDecimal(1,new BigDecimal(quantity*rate));
//preparedStatement.setInt(2,customerCode);
//preparedStatement.executeUpdate();
//preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}
