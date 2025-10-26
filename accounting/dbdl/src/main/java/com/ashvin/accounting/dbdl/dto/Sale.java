package com.ashvin.accounting.dbdl.dto;

import java.util.*;

public class Sale implements java.io.Serializable, Comparable<Sale>
{
private Integer billNumber;
private Date billDate;
private int customerCode;
private int itemCode;
private int quantity;
private int rate;
public Sale()
{
this.billNumber=0;
this.billDate=null;
this.customerCode=0;
this.itemCode=0;
this.quantity=0;
this.rate=0;
}
public void setBillNumber(java.lang.Integer billNumber)
{
this.billNumber=billNumber;
}
public java.lang.Integer getBillNumber()
{
return this.billNumber;
}
public void setBillDate(java.util.Date billDate)
{
this.billDate=billDate;
}
public java.util.Date getBillDate()
{
return this.billDate;
}
public void setCustomerCode(int customerCode)
{
this.customerCode=customerCode;
}
public int getCustomerCode()
{
return this.customerCode;
}
public void setItemCode(int itemCode)
{
this.itemCode=itemCode;
}
public int getItemCode()
{
return this.itemCode;
}
public void setQuantity(int quantity)
{
this.quantity=quantity;
}
public int getQuantity()
{
return this.quantity;
}
public void setRate(int rate)
{
this.rate=rate;
}
public int getRate()
{
return this.rate;
}
public boolean equals(Object obj)
{
if(obj==null) return false;
if(!(obj instanceof Sale)) return false;
Sale other=(Sale)obj;
if(other.billNumber==null && this.billNumber==null) return true;
if(other.billNumber==null || this.billNumber==null) return false;
return this.billNumber.equals(other.billNumber);
}
public int compareTo(Sale other)
{
if(other==null) return 1;
if(this.billNumber==null && other.billNumber==null) return 0;
if(this.billNumber==null) return -1;
if(other.billNumber==null) return 1;
return this.billNumber.compareTo(other.billNumber);
}
public int hashCode()
{
return this.billNumber.hashCode();
}
}
