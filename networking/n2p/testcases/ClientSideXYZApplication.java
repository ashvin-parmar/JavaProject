import com.ashvin.n2p.*;
import java.util.*;
import java.io.*;
import java.net.*;

class ClientSideXYZApplication implements Application
{
private Client client;
private String server;
private int portNumber1,portNumber2;
private static String connectionId;
public ClientSideXYZApplication(String server,int portNumber1,int portNumber2)
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
System.out.println("Id on client side: "+id);
System.out.println("bytes on client side request: "+bytes.toString());
String req="Here are the request from client side";
return req.getBytes();
}
public void onResponseBytes(String id,byte bytes[])
{
System.out.println("Id on client side: "+id);
System.out.println("bytes on client side response: "+bytes.toString());
}
public void onConnected(String id)
{
System.out.println("Connection id: "+id);
this.connectionId=id;
}
public static void main(String gg[])
{
ClientSideXYZApplication xyzApplication=new ClientSideXYZApplication("localhost",5050,4040);
xyzApplication.start();
String abcd="ABCD";
xyzApplication.onRequestBytes(connectionId,abcd.getBytes());
}
}
