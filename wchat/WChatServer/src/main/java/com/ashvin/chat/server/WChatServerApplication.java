package com.ashvin.chat.server;

import java.io.*;
import java.util.*;
import java.net.*;

import com.ashvin.n2p.*;

public class WChatServerApplication implements Application
{
private static final WChatServerApplication serverApplication=new WChatServerApplication();
private Server server;
private WChatServerApplication()
{
server=new Server(this);
}
public static WChatServerApplication getServerApplication()
{
  return WChatServerApplication.serverApplication;
}
public void start()
{
server.start();
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
}
