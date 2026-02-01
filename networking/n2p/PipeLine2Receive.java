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
public PipeLine2Receive(Application application,String clientId,Socket socket,InputStream inputStream,InputStreamReader inputStreamReader,OutputStream outputStream,OutputStreamWriter outputStreamWriter)
{
this.clientConnected=true;
this.application=application;
this.clientId=clientId;
this.socket=socket;
this.inputStream=inputStream;
this.inputStreamReader=inputStreamReader;
this.outputStream=outputStream;
this.outputStreamWriter=outputStreamWriter;
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
//byte[] responseBytes=application.onBytes(bytes);
// send back response bytes
}
}catch(Exception exception)
{
this.clientConnected=false;
}
}
}
