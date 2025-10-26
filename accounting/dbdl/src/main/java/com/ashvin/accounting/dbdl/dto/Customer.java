package com.ashvin.accounting.dbdl.dto;
public class Customer implements java.io.Serializable, Comparable<Customer>
{
private Integer code;
private String name;
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
public boolean equals(Object obj)
{
if(obj==null) return false;
if(!(obj instanceof Customer)) return false;
Customer other=(Customer)obj;
if(other.code==null && this.code==null) return true;
if(other.code==null || this.code==null) return false;
return this.code.equals(other.code);
}
public int compareTo(Customer other)
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
