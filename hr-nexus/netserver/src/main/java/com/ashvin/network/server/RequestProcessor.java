package com.ashvin.network.server;
import com.ashvin.network.common.*;
import com.ashvin.network.common.exceptions.*;
import java.net.*;
import java.io.*;

public class RequestProcessor extends Thread
{
private Socket socket;
private RequestHandlerInterface requestHandler;
public RequestProcessor(Socket socket,RequestHandlerInterface requestHandler)
{
this.socket=socket;
this.requestHandler=requestHandler;
start();
}
public void run()
{
try
{
int i,j,x,k;
InputStream inputStream=socket.getInputStream();
byte header[]=new byte[1024];
byte tmp[]=new byte[1024];
int chunkSize;
int bytesToRead;
int bytesReadCount=0;
j=0;
i=0;
//Header receive
bytesToRead=1024;
while(j<bytesToRead)
{
bytesReadCount=inputStream.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j+=bytesReadCount;
}
//Extract length from header
int requestLength;
requestLength=0;
i=1023;
j=1;
while(i>=0)
{
requestLength=requestLength+(header[i]*j);
j=j*10;
i--;
}
//Sends acknowledgement
byte ack[]=new byte[1];
OutputStream os=socket.getOutputStream();
os.write(ack,0,1);
os.flush();

//Receive Data as request
byte requestBytes[]=new byte[requestLength];
chunkSize=1024;
j=0;
i=0;
while(j<requestLength)
{
bytesReadCount=inputStream.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++) 
{
requestBytes[i]=tmp[k];
i++;
}
j+=bytesReadCount;
}

ByteArrayInputStream bais=new ByteArrayInputStream(requestBytes);
ObjectInputStream ois=new ObjectInputStream(bais);
Request request=(Request)ois.readObject();
Response response=requestHandler.process(request);
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(response);
oos.flush();
byte[] responseBytes=baos.toByteArray();
int responseLength=responseBytes.length;
header=new byte[1024];
i=1023;
x=responseLength;
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
//Header sends
os.write(header,0,1024);
os.flush();

//Acknowlegement receive
while(true)
{
bytesReadCount=inputStream.read(ack);
if(bytesReadCount==-1) continue;
break;
}

//Response Data send
int bytesToSend=responseLength;
i=0;
j=0;
chunkSize=1024;
while(j<bytesToSend)
{
if((bytesToSend-j)<=chunkSize) chunkSize=bytesToSend-j;
os.write(responseBytes,0,chunkSize);
os.flush();
j+=chunkSize;
}
//Acknowledgement receive
while(true)
{
bytesReadCount=inputStream.read(ack);
if(bytesReadCount==-1) continue;
break;
}
socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
