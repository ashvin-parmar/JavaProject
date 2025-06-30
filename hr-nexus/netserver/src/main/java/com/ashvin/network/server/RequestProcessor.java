package com.ashvin.network.server;
import com.ashvin.network.common.*;
import com.ashvin.network.common.exceptions.*;
import java.net.*;
import java.io.*;

public class RequestProcessor extends Thread
{
private Socket socket;
private RequestHandlerInterface requestHandler;
public RequestProcessor(Socket socket,RequestHandlerInterface requestHandler)
{
this.socket=socket;
this.requestHandler=requestHandler;
start();
}
public void run()
{

}
}
