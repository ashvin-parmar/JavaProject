package com.ashvin.accounting.dbdl.dto;

import java.util.*;

public class Purchase implements java.io.Serializable, Comparable<Purchase>
{
private Integer referenceNumber;
private String billNumber;
private int supplierCode;
private int itemCode;
private int quantity;
private int rate;
public Purchase()
{
this.referenceNumber=0;
this.billNumber="";
this.supplierCode=0;
this.itemCode=0;
this.quantity=0;
this.rate=0;
}
public void setReferenceNumber(java.lang.Integer referenceNumber)
{
this.referenceNumber=referenceNumber;
}
public java.lang.Integer getReferenceNumber()
{
return this.referenceNumber;
}
public void setBillNumber(java.lang.String billNumber)
{
this.billNumber=billNumber;
}
public java.lang.String getBillNumber()
{
return this.billNumber;
}
public void setSupplierCode(int supplierCode)
{
this.supplierCode=supplierCode;
}
public int getSupplierCode()
{
return this.supplierCode;
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
if(!(obj instanceof Purchase)) return false;
Purchase other=(Purchase)obj;
if(other.referenceNumber==null && this.referenceNumber==null) return true;
if(other.referenceNumber==null || this.referenceNumber==null) return false;
return this.referenceNumber.equals(other.referenceNumber);
}
public int compareTo(Purchase other)
{
if(other==null) return 1;
if(this.referenceNumber==null && other.referenceNumber==null) return 0;
if(this.referenceNumber==null) return -1;
if(other.referenceNumber==null) return 1;
return this.referenceNumber.compareTo(other.referenceNumber);
}
public int hashCode()
{
return this.referenceNumber.hashCode();
}
}
