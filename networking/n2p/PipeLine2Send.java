import java.util.*;
import java.net.*;
import java.io.*;

class PipeLine2Send extends Thread
{
private String clientId;
private Socket socket;
private InputStream inputStream;
private InputStreamReader inputStreamReader;
private OutputStream outputStream;
private OutputStreamWriter outputStreamWriter;
private boolean clientConnected;
private List<byte[]> dataQueue;
public PipeLine2Send(Socket socket,String clientId)
{
this.clientConnected=true;
this.socket=socket;
this.clientId=clientId;
try
{
inputStream=socket.getInputStream();
outputStream=socket.getOutputStream();
}catch(IOException ioException)
{
this.clientConnected=false;
}
inputStreamReader=new InputStreamReader(inputStream);
outputStreamWriter=new OutputStreamWriter(outputStream);
dataQueue=Collections.synchronizedList(new ArrayList<byte[]>());
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
}catch(IOException ioException)
{
//do nothing
}
}
public boolean addData(byte []data)
{
if(this.clientConnected==false) return false;
dataQueue.add(data);
this.resume();		//If Thread is on suspended, it will be resumed.
return true;
}
public void run()
{
try
{
byte data[];
while(true)
{
if(dataQueue.size()==0)
{
Thread.sleep(500);
continue;
}
data=dataQueue.get(0);
//Send data to server side
}
}catch(Exception exception)
{
this.clientConnected=false;
}
}
}
