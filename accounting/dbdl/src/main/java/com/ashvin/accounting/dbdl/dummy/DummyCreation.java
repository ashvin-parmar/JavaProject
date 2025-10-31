package com.ashvin.accounting.dbdl.dummy;
import com.ashvin.accounting.dbdl.dao.*;
import com.ashvin.accounting.dbdl.dto.*;

import java.util.*;
import java.time.*;

public class DummyCreation
{
private String alphabetsStr="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
private String numericStr="1234567890";
private String specialStr="!@#$%^&*_+-";
private Random random;
public DummyCreation()
{
random=new Random();
}
public void createDummyCustomers100()
{
int countRecords=0;
StringBuilder sb=null;
String dummyStr="";
int range=alphabetsStr.length();
int randomInteger;
CustomerDAO customerDAO=new CustomerDAO();
Customer customer=null;

while(countRecords<100)
{
sb=new StringBuilder();
for(int j=0;j<50;j++)
{
randomInteger=random.nextInt(range);
sb.append((alphabetsStr.charAt(randomInteger)));
}
dummyStr=sb.toString();
//System.out.println(dummyStr);
customer=new Customer();
customer.setName(dummyStr);
try
{
customerDAO.addCustomer(customer);
countRecords++;
System.out.println("Customer "+countRecords+" added");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
public void createDummyItems400()
{
int countRecords=0;
StringBuilder sb=null;
String dummyStr="";
int range=alphabetsStr.length();
int randomInteger;
ItemDAO itemDAO=new ItemDAO();
Item item=null;
while(countRecords<400)
{
sb=new StringBuilder();
for(int j=0;j<50;j++)
{
randomInteger=random.nextInt(range);
sb.append((alphabetsStr.charAt(randomInteger)));
}
dummyStr=sb.toString();
//System.out.println(dummyStr);
item=new Item();
item.setName(dummyStr);
try
{
itemDAO.addItem(item);
countRecords++;
System.out.println("Item: "+countRecords+" added");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
public void createDummySuppliers100()
{
int countRecords=0;
StringBuilder sb=null;
String dummyStr="";
int range=alphabetsStr.length();
int randomInteger;
SupplierDAO supplierDAO=new SupplierDAO();
Supplier supplier=null;

while(countRecords<100)
{
sb=new StringBuilder();
for(int j=0;j<50;j++)
{
randomInteger=random.nextInt(range);
sb.append((alphabetsStr.charAt(randomInteger)));
}
dummyStr=sb.toString();
//System.out.println(dummyStr);
supplier=new Supplier();
supplier.setName(dummyStr);
try
{
supplierDAO.addSupplier(supplier);
countRecords++;
System.out.println("Supplier: "+countRecords+" added");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
public void createDummySales3000(java.util.Date startDate)
{
Date tmpDate=null;
Duration duration=Duration.ofMillis(0);
duration=duration.plusMillis(startDate.getTime());
long durationRange=Duration.ofMinutes(20).toMillis();
long randomInteger;
SaleDAO saleDAO=new SaleDAO();
Sale sale=null;
int countRecords=0;
duration=duration.minusDays(1);
int records=0;
for(int i=0;i<30;i++)
{
duration=duration.plusDays(1);
//System.out.println("---------------Day ends --------");
countRecords=0;
while(countRecords<100)
{
randomInteger=random.nextLong(durationRange)+Duration.ofMinutes(2).toMillis();
duration=duration.plusMillis(randomInteger);
tmpDate=new Date(duration.toMillis());
//System.out.println("RandomNumber: "+randomInteger+", Millies: "+duration.toMillis()+", Date: "+tmpDate);
try
{
sale=new Sale();
sale.setBillDate(tmpDate);
sale.setCustomerCode(random.nextInt(100)+1);
sale.setItemCode(random.nextInt(400)+1);
sale.setQuantity(random.nextInt(10)+1);
sale.setRate(random.nextInt(10)+10);
saleDAO.addSale(sale);
countRecords++;
records++;
System.out.println("Sale: "+records+" added");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
}
public void createDummyPurchases4000()
{
long randomInteger;
PurchaseDAO purchaseDAO=new PurchaseDAO();
String billNumber="";
Purchase purchase=null;
int countRecords=0;
Calendar calendar=Calendar.getInstance();
calendar.setTime(new java.util.Date());
int records=0;
for(int j=1;j<=30;j++)
{
calendar.add(Calendar.DATE,random.nextInt(3)+1);
countRecords=0;
while(countRecords<70)
{
calendar.add(Calendar.MINUTE,random.nextInt(20)+1);
try
{
billNumber=UUID.randomUUID().toString();
if(billNumber.length()>25) billNumber=billNumber.substring(0,25);
purchase=new Purchase();
purchase.setBillNumber(billNumber);
purchase.setBillDate(new java.sql.Date(calendar.get(Calendar.YEAR)-1900,calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE)));
purchase.setSupplierCode(random.nextInt(100)+1);
purchase.setItemCode(random.nextInt(400)+1);
purchase.setQuantity(random.nextInt(10)+1);
purchase.setRate(random.nextInt(10)+10);
purchaseDAO.addPurchase(purchase);
countRecords++;
records++;
System.out.println("Purchase: "+records+" added");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
}
public void createDummyReceipts100(java.util.Date startDate)
{
Date tmpDate=null;
Duration duration=Duration.ofMillis(0);
duration=duration.plusMillis(startDate.getTime());
long durationRange=Duration.ofHours(10).toMillis();
long randomInteger;
ReceiptDAO receiptDAO=new ReceiptDAO();
Receipt receipt=null;
int countRecords=0;
while(countRecords<100)
{
randomInteger=random.nextLong(durationRange);
duration=duration.plusMillis(randomInteger);
tmpDate=new Date(duration.toMillis());
try
{
receipt=new Receipt();
receipt.setReceiptDate(tmpDate);
receipt.setCustomerCode(random.nextInt(100)+1);
receipt.setAmount(random.nextInt(50)+100);
receiptDAO.addReceipt(receipt);
countRecords++;
System.out.println("Receipts: "+countRecords+" added");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
public void createDummyPayments100(java.util.Date startDate)
{
Date tmpDate=null;
Duration duration=Duration.ofMillis(0);
duration=duration.plusMillis(startDate.getTime());
long durationRange=Duration.ofHours(10).toMillis();
long randomInteger;
PaymentDAO paymentDAO=new PaymentDAO();
Payment payment=null;
int countRecords=0;
while(countRecords<100)
{
randomInteger=random.nextLong(durationRange);
duration=duration.plusMillis(randomInteger);
tmpDate=new Date(duration.toMillis());
try
{
payment=new Payment();
payment.setPaymentDate(tmpDate);
payment.setSupplierCode(random.nextInt(100)+1);
payment.setAmount(random.nextInt(50)+100);
paymentDAO.addPayment(payment);
countRecords++;
System.out.println("Payment: "+countRecords+" added");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
}
