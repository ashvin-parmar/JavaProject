package com.ashvin.accounting.dbdl.services;
import com.ashvin.accounting.dbdl.dto.*;
import com.ashvin.accounting.dbdl.dao.DAOConnection;
import com.ashvin.accounting.dbdl.dao.DAOException;

import java.io.*;
import java.sql.*;
import java.math.*;
import java.time.*;


public class ReportService
{
public void getCustomerReports(File file) throws DAOException
{
if(file==null) throw new DAOException("File required to get the report");
int customerCode;
String customerName;
int toReceive;
int advancePaid;
int remainToReceive;
String toWrite;
try
{
Connection connection=DAOConnection.getConnection();
String sqlStatement="select customer.code,customer.name as customer_name, coalesce(tmp_sale.total_amount,0) as to_receive, coalesce(tmp_receipt.amount_paid,0) as advance_paid, coalesce(tmp_sale.total_amount,0)-coalesce(tmp_receipt.amount_paid,0) as remain_to_receive from customer left join ( select sale.customer_code, sum(sale.quantity*sale.rate) as total_amount from sale group by customer_code) as tmp_sale on customer.code=tmp_sale.customer_code left join (select receipt.customer_code,sum(receipt.amount) as amount_paid from receipt group by receipt.customer_code) as tmp_receipt on customer.code=tmp_receipt.customer_code order by customer.code";
PreparedStatement preparedStatement=connection.prepareStatement(sqlStatement);
ResultSet resultSet=preparedStatement.executeQuery();

RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
toWrite="customerCode,customerName,toReceive,advancePaid,remainToReceive\n";
randomAccessFile.writeBytes(toWrite);
while(resultSet.next())
{
customerCode=resultSet.getInt("code");
customerName=resultSet.getString("customer_name").trim();
toReceive=resultSet.getInt("to_receive");
advancePaid=resultSet.getInt("advance_paid");
remainToReceive=resultSet.getInt("remain_to_receive");
toWrite=(customerCode+","+customerName+","+toReceive+","+advancePaid+","+remainToReceive+"\n");
randomAccessFile.writeBytes(toWrite);
//randomAccessFile.flush();
}
randomAccessFile.close();
resultSet.close();
preparedStatement.close();
connection.close();

}catch(IOException ioException)
{
//System.out.println("File-handling related issue: "+ioException.getMessage());
throw new DAOException("Unable to create file: "+file.getPath());
}catch(SQLException sqlException)
{
//System.out.println("SQL-handling related issue: "+sqlException.getMessage());
throw new DAOException("Unable to create file: "+file.getPath());
}catch(Exception exception)
{
//System.out.println("Some problem: "+exception.getMessage());
throw new DAOException("Some problem");
}
}
public void getSupplierReports(File file) throws DAOException
{
if(file==null) throw new DAOException("File required to get the report");
int supplierCode;
String supplierName;
int toPay;
int advancePaid;
int remainToPay;
String toWrite;
try
{
Connection connection=DAOConnection.getConnection();
String sqlStatement="select supplier.code,supplier.name as supplier_name,coalesce(tmp_purchase.total_amount,0) as to_pay,coalesce(tmp_payment.amount_paid,0) as advance_paid, coalesce(tmp_purchase.total_amount,0)-coalesce(tmp_payment.amount_paid,0) as remain_to_pay from supplier left join (select purchase.supplier_code,sum(purchase.quantity*purchase.rate) as total_amount from purchase group by purchase.supplier_code) as tmp_purchase on tmp_purchase.supplier_code=supplier.code left join (select payment.supplier_code,sum(payment.amount) as amount_paid from payment group by payment.supplier_code) as tmp_payment on tmp_payment.supplier_code=supplier.code order by supplier.code";
PreparedStatement preparedStatement=connection.prepareStatement(sqlStatement);
ResultSet resultSet=preparedStatement.executeQuery();

RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
toWrite="supplierCode,supplierName,toPay,amountPaid,remainToPay\n";
randomAccessFile.writeBytes(toWrite);
while(resultSet.next())
{
supplierCode=resultSet.getInt("code");
supplierName=resultSet.getString("supplier_name").trim();
toPay=resultSet.getInt("to_pay");
advancePaid=resultSet.getInt("advance_paid");
remainToPay=resultSet.getInt("remain_to_pay");
toWrite=(supplierCode+","+supplierName+","+toPay+","+advancePaid+","+remainToPay+"\n");
randomAccessFile.writeBytes(toWrite);
}
randomAccessFile.close();
resultSet.close();
preparedStatement.close();
connection.close();
}
catch(Exception exception)
{
//System.out.println("Some problem: "+exception.getMessage());
throw new DAOException("Some problem");
}
}
public void printCustomerReportsDirectly()
{
int customerCode;
String customerName;
BigDecimal toReceive;
BigDecimal advancePaid;
String toWrite;
try
{
Connection connection=DAOConnection.getConnection();
String sqlStatement="select * from customer order by code";
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery(sqlStatement);
while(resultSet.next())
{
customerCode=resultSet.getInt("code");
customerName=resultSet.getString("name").trim();
toReceive=resultSet.getBigDecimal("total_sale");
advancePaid=resultSet.getBigDecimal("total_receipt");
System.out.printf("%4d) %15s %10s %10s,%8s\n",customerCode,customerName,toReceive.toPlainString(),advancePaid.toPlainString(),toReceive.subtract(advancePaid).toPlainString());
}
resultSet.close();
statement.close();
connection.close();
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}
public void printSupplierReportsDirectly()
{
int supplierCode;
String supplierName;
BigDecimal toPay;
BigDecimal advancePaid;
try
{
Connection connection=DAOConnection.getConnection();
String sqlStatement="select * from supplier order by code";
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery(sqlStatement);
while(resultSet.next())
{
supplierCode=resultSet.getInt("code");
supplierName=resultSet.getString("name").trim();
toPay=resultSet.getBigDecimal("total_purchase");
advancePaid=resultSet.getBigDecimal("total_payment");
System.out.printf("%3d) %15s %10s %10s %10s\n",supplierCode,supplierName,toPay.toPlainString(),advancePaid.toPlainString(),toPay.subtract(advancePaid).toPlainString());
}
resultSet.close();
statement.close();
connection.close();
}
catch(Exception exception)
{
//System.out.println("Some problem: "+exception.getMessage());
//throw new DAOException("Some problem");
}
}
public void printCustomerReports()	//Long ways
{
Instant startTime=Instant.now();
int customerCode;
String customerName;
BigDecimal toReceive;
BigDecimal advancePaid;
String toWrite;
try
{
ResultSet resultSet2;
PreparedStatement preparedStatement;
Connection connection=DAOConnection.getConnection();
String sqlStatement="select * from customer order by code";
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery(sqlStatement);
while(resultSet.next())
{
customerCode=resultSet.getInt("code");
customerName=resultSet.getString("name").trim();
//toReceive=resultSet.getBigDecimal("total_sale");
//advancePaid=resultSet.getBigDecimal("total_receipt");
preparedStatement=connection.prepareStatement("select sum(quantity*rate) as sm from sale where customer_code=?");
preparedStatement.setInt(1,customerCode);
resultSet2=preparedStatement.executeQuery();
resultSet2.next();
toReceive=new BigDecimal(String.valueOf(resultSet2.getInt("sm")));
preparedStatement.close();
resultSet2.close();
preparedStatement=connection.prepareStatement("select sum(amount) as sm from receipt where customer_code=?");
preparedStatement.setInt(1,customerCode);
resultSet2=preparedStatement.executeQuery();
resultSet2.next();
advancePaid=new BigDecimal(String.valueOf(resultSet2.getInt("sm")));
preparedStatement.close();
resultSet2.close();
System.out.printf("%4d) %15s %10s %10s,%8s\n",customerCode,customerName,toReceive.toPlainString(),advancePaid.toPlainString(),toReceive.subtract(advancePaid).toPlainString());
}
resultSet.close();
statement.close();
connection.close();
Instant endTime=Instant.now();
Duration difference=Duration.between(startTime,endTime);
System.out.println("Duration: "+difference.toSeconds()+" seconds");
System.out.println("Duration: "+difference.toMillis()+" milli seconds");
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}
public void printSupplierReports()
{
Instant startTime=Instant.now();
int supplierCode;
String supplierName;
BigDecimal toPay;
BigDecimal advancePaid;
try
{
Connection connection=DAOConnection.getConnection();
String sqlStatement="select * from supplier order by code";
Statement statement=connection.createStatement();
PreparedStatement preparedStatement;
ResultSet resultSet2;
ResultSet resultSet=statement.executeQuery(sqlStatement);
while(resultSet.next())
{
supplierCode=resultSet.getInt("code");
supplierName=resultSet.getString("name").trim();
//toPay=resultSet.getBigDecimal("total_purchase");
//advancePaid=resultSet.getBigDecimal("total_payment");
preparedStatement=connection.prepareStatement("select sum(quantity*rate) as sm from purchase where supplier_code=?");
preparedStatement.setInt(1,supplierCode);
resultSet2=preparedStatement.executeQuery();
resultSet2.next();
toPay=new BigDecimal(String.valueOf(resultSet2.getInt("sm")));
resultSet2.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select sum(amount) as sm from payment where supplier_code=?");
preparedStatement.setInt(1,supplierCode);
resultSet2=preparedStatement.executeQuery();
resultSet2.next();
advancePaid=new BigDecimal(String.valueOf(resultSet2.getInt("sm")));
resultSet2.close();
preparedStatement.close();
System.out.printf("%3d) %15s %10s %10s %10s\n",supplierCode,supplierName,toPay.toPlainString(),advancePaid.toPlainString(),toPay.subtract(advancePaid).toPlainString());
}
resultSet.close();
statement.close();
connection.close();
Instant endTime=Instant.now();
Duration diff=Duration.between(startTime,endTime);
System.out.println("Duration: "+diff.toSeconds()+" seconds");
System.out.println("Duration: "+diff.toMillis()+" milli seconds");
}
catch(Exception exception)
{
System.out.println("Some problem: "+exception.getMessage());
//throw new DAOException("Some problem");
}
}
}
