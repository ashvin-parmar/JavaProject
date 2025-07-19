package com.ashvin.network.server;
import java.net.*;
import java.io.*;
import com.ashvin.network.common.*;
import com.ashvin.network.common.exceptions.*;
public class NetworkServer
{
RequestHandlerInterface requestHandler;
public NetworkServer(RequestHandlerInterface requestHandler) throws NetworkException 
{
if(requestHandler==null)
{
throw new NetworkException("RequestHandler required.");
}
this.requestHandler=requestHandler;
}
public void start() throws NetworkException
{
ServerSocket serverSocket=null;
int port=Configuration.getPort();
try
{
serverSocket=new ServerSocket(port);
}catch(Exception exception)
{
throw new NetworkException("Unable to create socket on port: "+port);
}
Socket socket=null;
RequestProcessor requestProcessor=null;
try
{
while(true)
{
System.out.println("Server is ready to accept request on port: "+port);
socket=serverSocket.accept();
requestProcessor=new RequestProcessor(socket,this.requestHandler);
}
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
