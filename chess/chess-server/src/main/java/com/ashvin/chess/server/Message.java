package com.ashvin.chess.server;

public class Message implements java.io.Serializable
{
private String fromUsername;
private String toUsername;
private MESSAGE_TYPE messageType;
public void setFromUsername(String fromUsername)
{
this.fromUsername=fromUsername;
}
public void setToUsername(String toUsername)
{
this.toUsername=toUsername;
}
public void setMessageType(MESSAGE_TYPE messageType)
{
this.messageType=messageType;
}
public String getFromUsername()
{
return this.fromUsername;
}
public String getToUsername()
{
return this.toUsername;
}
public MESSAGE_TYPE getMessageType()
{
return this.messageType;
}
}
