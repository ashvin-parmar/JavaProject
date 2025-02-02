package com.ashvin.hr.nexus.bl.exceptions;

import java.util.*;

public class BLException extends Exception
{
private String genericException;
private Map<String,String> exceptions;
public BLException()
{
this.exceptions=new TreeMap<>();
this.genericException=null;
}
public void setGenericException(String genericException)
{
this.genericException=genericException;
}
public String getGenericException()
{
if(this.genericException==null) return "";
return this.genericException;
}
public String getMessage()
{
if(this.genericException==null) return "";
return this.genericException;
}
public void setPropertyException(String property,String exception)
{
this.exceptions.put(property,exception);
}
public String getPropertyException(String property)
{
return this.exceptions.get(property);
}
List<String> getExceptionProperties()
{
List<String> properties=new ArrayList<>();
this.exceptions.forEach((k,v)->{
properties.add(k);
});
return properties;
}
public int getExceptionCount()
{
if(this.genericException!=null) return this.exceptions.size()+1;
return this.exceptions.size();
}
public boolean hasExceptions()
{
return this.getExceptionCount()>0;
}
public boolean hasGenericException()
{
return this.genericException!=null;
}
}
