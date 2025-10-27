package com.ashvin.accounting.dbdl.dummy;
import com.ashvin.accounting.dbdl.dao.*;
import com.ashvin.accounting.dbdl.dto.*;

import java.util.*;
public class DummyCreation
{
private String alphabetsStr="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
private String numbericStr="1234567890";
private String specialStr="!@#$%^&*_+-";
private Random random;
public DummyCreation()
{
random=new Random();
}
public void createDummyCustomer100()
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
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}

}
public void createDummyItem400()
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
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}

}
public void createDummySupplier100()
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
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}

}

}
