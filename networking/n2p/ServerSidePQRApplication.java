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
return null;
}
public void onResponseBytes(String id,byte bytes[])
{
}
public void onConnected(String id)
{
}
public static void main(String args[])
{
ServerSidePQRApplication pqrApplication=new ServerSidePQRApplication();
pqrApplication.start();
}
}
