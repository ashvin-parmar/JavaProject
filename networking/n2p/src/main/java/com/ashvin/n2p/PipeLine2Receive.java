package com.ashvin.n2p;
import java.util.*;
import java.io.*;
import java.net.*;


public class PipeLine2Receive extends Thread
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
OutputStream os=this.outputStream;
InputStream is=this.inputStream;
int bytesReadCount;
long bytesToRecieve;
long bytesToSend;
long requestLength;
long responseLength;
byte requestBytes[];
byte responseBytes[];
byte ack[]=new byte[1];
byte header[]=new byte[1024];
byte tmp[]=new byte[1024];
int i,k,l;
long j;
bytesToRecieve=1024;
j=0;
k=0;
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
requestLength=0;
i=1023;
j=1;
while(i>=0)
{
requestLength=requestLength+(header[i]*j);
j*=10;
i--;
}
ack[0]=(byte)1;
os.write(ack,0,1);
os.flush();
requestBytes=new byte[(int)requestLength];
j=0;
k=0;
while(j<requestLength)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(i=0;i<bytesReadCount;i++)
{
requestBytes[k]=tmp[i];
k++;
}
j+=bytesReadCount;
}
responseBytes=application.onRequestBytes(clientId,requestBytes);
responseLength=responseBytes.length;
i=1023;
j=responseLength;
header=new byte[1024];
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
int chunkSize=1024;
j=0;
while(j<responseLength)
{
if((responseLength-j)<chunkSize) chunkSize=(int)(responseLength-j);
os.write(responseBytes,(int)j,chunkSize);
os.flush();
j+=chunkSize;
}
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
}catch(Exception exception)
{
this.clientConnected=false;
}
}
}
