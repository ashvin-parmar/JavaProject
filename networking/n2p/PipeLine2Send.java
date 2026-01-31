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
private List<Job> jobQueue;
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
jobQueue=Collections.synchronizedList(new ArrayList<Job>());
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
public boolean addData(byte []bytes)
{
Job job;
if(this.clientConnected==false) return false;
job=new Job();
job.id=UUID.randomUUID().toString();
job.bytes=bytes;
jobQueue.add(job);
this.resume();		//If Thread is on suspended, it will be resumed.
return true;
}
public void run()
{
Job job;
byte bytes[];
try
{
while(true)
{
if(jobQueue.size()==0)
{
Thread.sleep(500);
continue;
}
job=jobQueue.get(0);
bytes=job.bytes;
//Send data to server side
//code to send header with data length 
// code to send data in chunks of 1024
// application.onBytes(job.id,bytes);
}
}catch(Exception exception)
{
this.clientConnected=false;
}
}
}
