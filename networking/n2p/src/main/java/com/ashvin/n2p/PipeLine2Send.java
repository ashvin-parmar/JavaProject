package com.ashvin.n2p;
import java.util.*;
import java.net.*;
import java.io.*;

public class PipeLine2Send extends Thread
{
private Application application;
private String clientId;
private Socket socket;
private InputStream inputStream;
private InputStreamReader inputStreamReader;
private OutputStream outputStream;
private OutputStreamWriter outputStreamWriter;
private boolean clientConnected;
private List<Job> jobQueue;
public PipeLine2Send(Application application,String clientId,Socket socket,InputStream inputStream,InputStreamReader inputStreamReader,OutputStream outputStream,OutputStreamWriter outputStreamWriter)
{
this.clientConnected=true;
this.application=application;
this.clientId=clientId;
this.socket=socket;
this.inputStream=inputStream;
this.inputStreamReader=inputStreamReader;
this.outputStream=outputStream;
this.outputStreamWriter=outputStreamWriter;
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
byte[] bytes;
InputStream is=this.inputStream;
OutputStream os=this.outputStream;
byte header[]=null;
byte tmp[]=new byte[1024];
byte ack[]=new byte[1];
int i,k,l;
long j;
int bytesReadCount;
int bytesToRecieve;
int chunkSize=1024;
byte requestBytes[]=null;
byte responseBytes[]=null;
long requestLength;
long responseLength;
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
header=new byte[1024];
ack[0]=(byte)(1);
requestBytes=bytes;
requestLength=requestBytes.length;
i=1023;
j=requestLength;
while(i>=0)
{
header[i]=(byte)(j%10);
j/=10;
i--;
}
os.write(header,0,1024);
os.flush();
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
j=0;
while(j<requestLength)
{
if((requestLength-j)<chunkSize) chunkSize=(int)(requestLength-j);
os.write(requestBytes,(int)j,chunkSize);
os.flush();
j+=chunkSize;
}

header=new byte[1024];
j=0;
k=0;
bytesToRecieve=1024;
while(j<bytesToRecieve)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(i=0;i<bytesReadCount;i++)
{
header[k]=tmp[i];
k++;
}
j+=bytesReadCount;
}
responseLength=0;
i=1023;
j=1;
while(i>=0)
{
responseLength+=(header[i]*j);
j*=10;
i--;
}
os.write(ack);
os.flush();
responseBytes=new byte[(int)responseLength];
j=0;
k=0;
while(j<responseLength)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(i=0;i<bytesReadCount;i++)
{
responseBytes[k]=tmp[i];
k++;
}
j+=bytesReadCount;
}
os.write(ack);
os.flush();
this.application.onResponseBytes(job.id,responseBytes);
}
}catch(Exception exception)
{
this.clientConnected=false;
}
}
}
