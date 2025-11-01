package com.ashvin.accounting.dbdl.dto;

import java.util.*;

public class Receipt implements java.io.Serializable, Comparable<Receipt>
{
private Integer receiptNumber;
private Date receiptDate;
private int customerCode;
private int amount;
public Receipt()
{
this.receiptNumber=0;
this.receiptDate=null;
this.customerCode=0;
this.amount=0;
}
public void setReceiptNumber(java.lang.Integer receiptNumber)
{
this.receiptNumber=receiptNumber;
}
public java.lang.Integer getReceiptNumber()
{
return this.receiptNumber;
}
public void setReceiptDate(java.util.Date receiptDate)
{
this.receiptDate=receiptDate;
}
public java.util.Date getReceiptDate()
{
return this.receiptDate;
}
public void setCustomerCode(int customerCode)
{
this.customerCode=customerCode;
}
public int getCustomerCode()
{
return this.customerCode;
}
public void setAmount(int amount)
{
this.amount=amount;
}
public int getAmount()
{
return this.amount;
}
public boolean equals(Object obj)
{
if(obj==null) return false;
if(!(obj instanceof Receipt)) return false;
Receipt other=(Receipt)obj;
if(other.receiptNumber==null && this.receiptNumber==null) return true;
if(other.receiptNumber==null || this.receiptNumber==null) return false;
return this.receiptNumber.equals(other.receiptNumber);
}
public int compareTo(Receipt other)
{
if(other==null) return 1;
if(this.receiptNumber==null && other.receiptNumber==null) return 0;
if(this.receiptNumber==null) return -1;
if(other.receiptNumber==null) return 1;
return this.receiptNumber.compareTo(other.receiptNumber);
}
public int hashCode()
{
return this.receiptNumber.hashCode();
}
}
