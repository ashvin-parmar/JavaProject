package com.ashvin.network.common;
public class Response implements java.io.Serializable
{
private boolean success;
private Object error;
private Object exception;
public void setSuccess(boolean success)
{
this.success=success;
}
public boolean getSuccess()
{
return this.success;
}
public void setError(Object error)
{
this.error=error;
}
public Object getError()
{
return this.error;
}
public void setException(Object exception)
{
this.exception=exception;
}
public Object getException()
{
return this.exception;
}
public boolean hasException()
{
return this.success==false;
}
}
