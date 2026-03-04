package com.ashvin.chat.client;

import java.util.*;
import java.io.*;
import java.net.*;
import com.ashvin.n2p;

class WChatClientApplication implements Application
{
private Client client;
private String server;
private int portNumber1,portNumber2;
public WChatClientApplication(String server,int portNumber1,int portNumber2)
{
this.server=server;
this.portNumber1=portNumber1;
this.portNumber2=portNumber2;
}
public void start()
{
try
{
client=new Client(this,server,portNumber1,portNumber2);
client.connect();
}catch(ConnectionException ce)
{
System.out.println(ce);
}
}
public byte[] onRequestBytes(String id,byte bytes[])
{
return null;
}
public void onResponseBytes(String id,byte bytes[])
{
}
public void onConnected(String id)
{
}
public static void main(String gg[])
{
WChatClientApplication clientApplication=new WChatClientApplication("localhost",5050,4040);
clientApplication.start();
}
}

