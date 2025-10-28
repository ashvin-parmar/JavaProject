package com.ashvin.accounting.dbdl.services;
import com.ashvin.accounting.dbdl.dto.*;
import com.ashvin.accounting.dbdl.dao.DAOConnection;
import com.ashvin.accounting.dbdl.dao.DAOException;

import java.io.*;
import java.sql.*;

public class ReportService
{
public void getCustomerReports(File file) throws DAOException
{
if(file==null) throw new DAOException("File required to get the report");
int customerCode;
String customerName;
int toReceive;
int advance;
int toPay;
String toWrite;
try
{
Connection connection=DAOConnection.getConnection();
String sqlStatement="select customer.code,customer.name as customer_name, coalesce(tmp_sale.total_amount,0) as to_receive, coalesce(tmp_receipt.amount_paid,0) as advance, coalesce(tmp_sale.total_amount,0)-coalesce(tmp_receipt.amount_paid,0) as to_pay from customer left join ( select sale.customer_code, sum(sale.quantity*sale.rate) as total_amount from sale group by customer_code) as tmp_sale on customer.code=tmp_sale.customer_code left join (select receipt.customer_code,sum(receipt.amount) as amount_paid from receipt group by receipt.customer_code) as tmp_receipt on customer.code=tmp_receipt.customer_code order by customer.code";
PreparedStatement preparedStatement=connection.prepareStatement(sqlStatement);
ResultSet resultSet=preparedStatement.executeQuery();

RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
toWrite="customer_code,customer_name,to_receive,advance,to_pay\n\r";
randomAccessFile.writeBytes(toWrite);
while(resultSet.next())
{
customerCode=resultSet.getInt("code");
customerName=resultSet.getString("customer_name").trim();
toReceive=resultSet.getInt("to_receive");
advance=resultSet.getInt("advance");
toPay=resultSet.getInt("to_pay");
toWrite=(customerCode+","+customerName+","+toReceive+","+advance+","+toPay+"\n\r");
randomAccessFile.writeBytes(toWrite);
//randomAccessFile.flush();
}
randomAccessFile.close();
resultSet.close();
preparedStatement.close();
connection.close();

}catch(IOException ioException)
{
throw new DAOException("File-handling related issue: "+ioException.getMessage());
}catch(SQLException sqlException)
{
throw new DAOException("SQL-handling related issue: "+sqlException.getMessage());
}catch(Exception exception)
{
throw new DAOException("Some problem: "+exception.getMessage());
}
}
}
