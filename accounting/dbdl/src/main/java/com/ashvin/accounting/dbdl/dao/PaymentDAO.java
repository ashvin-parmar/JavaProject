package com.ashvin.accounting.dbdl.dao;

import com.ashvin.accounting.dbdl.dto.*;
import java.sql.*;
import java.util.*;
import java.math.*;

public class PaymentDAO
{
public PaymentDAO()
{
}
public void addPayment(Payment payment) throws DAOException
{
if(payment==null) throw new DAOException("Payment required");
java.util.Date paymentDate=payment.getPaymentDate();
if(paymentDate==null) throw new DAOException("Payment Date required");
int supplierCode=payment.getSupplierCode();
if(supplierCode<=0) throw new DAOException("Invalid supplier code");
int amount=payment.getAmount();
if(amount<=0) throw new DAOException("Amount cannot be negative or zero");
java.sql.Date sqlPaymentDate=new java.sql.Date(paymentDate.getYear(),paymentDate.getMonth(),paymentDate.getDate());
sqlPaymentDate.setTime(paymentDate.getTime());
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("insert into payment (payment_date,supplier_code,amount) values(?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setDate(1,sqlPaymentDate);
preparedStatement.setInt(2,supplierCode);
preparedStatement.setInt(3,amount);
preparedStatement.executeUpdate();
ResultSet resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int paymentNumber=resultSet.getInt(1);
payment.setPaymentNumber(paymentNumber);
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update supplier set total_payment=total_payment+? where code=?");
preparedStatement.setBigDecimal(1,new BigDecimal(amount));
preparedStatement.setInt(2,supplierCode);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}
