package com.ashvin.accounting.dbdl.dto;
import java.math.*;
public class Supplier implements java.io.Serializable, Comparable<Supplier>
{
private Integer code;
private String name;
private BigDecimal totalPurchases;
private BigDecimal totalPayments;
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setTotalPurchases(BigDecimal totalPurchases)
{
this.totalPurchases=totalPurchases;
}
public BigDecimal getTotalPurchases()
{
return this.totalPurchases;
}
public void setTotalPayments(BigDecimal totalPayments)
{
this.totalPayments=totalPayments;
}
public BigDecimal getTotalPayments()
{
return this.totalPayments;
}
public boolean equals(Object obj)
{
if(obj==null) return false;
if(!(obj instanceof Supplier)) return false;
Supplier other=(Supplier)obj;
if(other.code==null && this.code==null) return true;
if(other.code==null || this.code==null) return false;
return this.code.equals(other.code);
}
public int compareTo(Supplier other)
{
if(other==null) return 1;
if(this.code==null && other.code==null) return 0;
if(this.code==null) return -1;
if(other.code==null) return 1;
return this.code.compareTo(other.code);
}
public int hashCode()
{
return this.code.hashCode();
}
}
