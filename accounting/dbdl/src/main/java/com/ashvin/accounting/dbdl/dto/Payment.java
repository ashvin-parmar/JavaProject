package com.ashvin.accounting.dbdl.dto;

import java.util.*;

public class Payment implements java.io.Serializable, Comparable<Payment>
{
private Integer paymentNumber;
private Date paymentDate;
private int supplierCode;
private int amount;
public Payment()
{
this.paymentNumber=0;
this.paymentDate=null;
this.supplierCode=0;
this.amount=0;
}
public void setPaymentNumber(java.lang.Integer paymentNumber)
{
this.paymentNumber=paymentNumber;
}
public java.lang.Integer getPaymentNumber()
{
return this.paymentNumber;
}
public void setPaymentDate(java.util.Date paymentDate)
{
this.paymentDate=paymentDate;
}
public java.util.Date getPaymentDate()
{
return this.paymentDate;
}
public void setSupplierCode(int supplierCode)
{
this.supplierCode=supplierCode;
}
public int getSupplierCode()
{
return this.supplierCode;
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
if(!(obj instanceof Payment)) return false;
Payment other=(Payment)obj;
if(other.paymentNumber==null && this.paymentNumber==null) return true;
if(other.paymentNumber==null || this.paymentNumber==null) return false;
return this.paymentNumber.equals(other.paymentNumber);
}
public int compareTo(Payment other)
{
if(other==null) return 1;
if(this.paymentNumber==null && other.paymentNumber==null) return 0;
if(this.paymentNumber==null) return -1;
if(other.paymentNumber==null) return 1;
return this.paymentNumber.compareTo(other.paymentNumber);
}
public int hashCode()
{
return this.paymentNumber.hashCode();
}
}
