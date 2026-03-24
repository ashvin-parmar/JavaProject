import com.ashvin.n2p.*;
import java.io.*;
import java.util.*;
import java.net.*;

class ServerSidePQRApplication implements Application
{
private Server server;
public ServerSidePQRApplication()
{
server=new Server(this);
}
public void start()
{
server.start();
}
public byte[] onRequestBytes(String id,byte bytes[])
{
System.out.println("Id on server side: "+id);
System.out.println("bytes on request: "+bytes.toString());
String req="Here are the request from server";
return req.getBytes();
}
public void onResponseBytes(String id,byte bytes[])
{
System.out.println("Id on server side: "+id);
System.out.println("bytes on response: "+bytes.toString());
}
public void onConnected(String id)
{
System.out.println("Connection id: "+id);
}
public static void main(String args[])
{
ServerSidePQRApplication pqrApplication=new ServerSidePQRApplication();
pqrApplication.start();
}
}
