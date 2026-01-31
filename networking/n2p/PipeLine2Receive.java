import java.util.*;
import java.io.*;
import java.net.*;


class PipeLine2Receive extends Thread
{
private Application application;
private Socket socket;
private String clientId;
private InputStream inputStream;
private OutputStream outputStream;
private InputStreamReader inputStreamReader;
private OutputStreamWriter outputStreamWriter;
private boolean clientConnected;
public PipeLine2Receive(Application application,Socket socket,String clientId)
{
this.application=application;
this.socket=socket;
this.clientId=clientId;
this.clientConnected=true;
try
{
this.inputStream=socket.getInputStream();
this.outputStream=socket.getOutputStream();
}catch(IOException ioException)
{
this.clientConnected=false;
}
this.inputStreamReader=new InputStreamReader(this.inputStream);
this.outputStreamWriter=new OutputStreamWriter(this.outputStream);
}
public boolean isClientConnected()
{
return this.clientConnected;
}
public String getClientId()
{
return this.clientId;
}
public void closeConnection()
{
try
{
socket.close();
this.clientConnected=false;
}catch(Exception exception)
{
//do nothing
}
}
public void run()
{
try
{
byte bytes[];
int x;
while(true)
{
x=inputStreamReader.read();
if(x==-1) continue;

// Code to extract data from request and create 
// header+content into byte[]
// then call the server method through variable application
// deliver the byte[].
byte[] responseBytes=application.onBytes(bytes);
// send back response bytes
}
}catch(Exception exception)
{
this.clientConnected=false;
}
}
}
