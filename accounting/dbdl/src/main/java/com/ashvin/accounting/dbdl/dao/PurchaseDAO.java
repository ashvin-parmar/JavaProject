package com.ashvin.accounting.dbdl.dao;

import com.ashvin.accounting.dbdl.dto.*;
import java.sql.*;
import java.util.*;
import java.math.*;

public class PurchaseDAO
{
public PurchaseDAO()
{
}
public void addPurchase(Purchase purchase) throws DAOException
{
if(purchase==null) throw new DAOException("Sale required");
String billNumber=purchase.getBillNumber();
if(billNumber==null) throw new DAOException("Bill number required");
java.util.Date billDate=purchase.getBillDate();
if(billDate==null) throw new DAOException("Bill date required");
int supplierCode=purchase.getSupplierCode();
if(supplierCode<=0) throw new DAOException("Invalid supplier code");
int itemCode=purchase.getItemCode();
if(itemCode<=0) throw new DAOException("Invalid item code");
int quantity=purchase.getQuantity();
if(quantity<=0) throw new DAOException("Qunatity cannot be negative or zero");	
int rate=purchase.getRate();
if(rate<=0) throw new DAOException("Rate cannot be negative or zero");

try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("insert into purchase (bill_number,purchase_date,supplier_code,item_code,quantity,rate) values(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,billNumber);
preparedStatement.setDate(2,new java.sql.Date(billDate.getTime()));
preparedStatement.setInt(3,supplierCode);
preparedStatement.setInt(4,itemCode);
preparedStatement.setInt(5,quantity);
preparedStatement.setInt(6,rate);
preparedStatement.executeUpdate();
ResultSet resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int referenceNumber=resultSet.getInt(1);
purchase.setReferenceNumber(referenceNumber);
resultSet.close();
preparedStatement.close();
//preparedStatement=connection.prepareStatement("update supplier set total_purchase=total_purchase+? where code=?");
//preparedStatement.setBigDecimal(1,new BigDecimal(quantity*rate));
//preparedStatement.setInt(2,supplierCode);
//preparedStatement.executeUpdate();
//preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}
