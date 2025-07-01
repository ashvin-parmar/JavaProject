package com.ashvin.network.client;
import com.ashvin.network.common.*;
import com.ashvin.network.common.exceptions.*;

import java.net.*;
import java.io.*;
import com.google.gson.*;

public class NetworkClient 
{
public Response send(Request request) throws NetworkException
{
Response res=null;
try
{
String host=Configuration.getHost();
int port=Configuration.getPort();

ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(request);
byte[] objectsByte=baos.toByteArray();
int requestLength=objectsByte.length;
byte[] header=new byte[1024];
int i,x,j,k;

i=1023;
x=requestLength;
while(x>0)
{
header[i]=(byte)(x%10);
i--;
x/=10;
}

Socket socket=new Socket(host,port);
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();

//Header Sends
os.write(header,0,1024);
os.flush();

//Acknowledgement Receive
byte ack[]=new byte[1];
int byteReadCount;
while(true)
{
byteReadCount=is.read(ack);
if(byteReadCount==-1) continue;
break;
}

//Data sends to Server side
int bytesToSend=requestLength;
int chunkSize=1024;
j=0;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize=bytesToSend-j;
os.write(objectsByte,j,chunkSize);
os.flush();
j=j+chunkSize;
}

//Response Header received from server
int bytesToReceive=1024;
header=new byte[1024];
byte tmp[]=new byte[1024];
int bytesReadCount;
i=0;
j=0;
while(j<bytesToReceive)		//Read data until we get given amount of data
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}

//Create ResponseLength extract from header
int responseLength=0;
i=1023;
j=1;
while(i>=0)
{
responseLength=responseLength+(header[i]*j);
j=j*10;
i--;
}

//Sends Acknowledgement
ack[0]=1;
os.write(ack,0,1);
os.flush();

//Response Data Receive
byte response[]=new byte[responseLength];
bytesToReceive=responseLength;
i=0;
j=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
response[i]=tmp[k];
i++;
}
j+=bytesReadCount;
}

//Acknowledgement sends
ack[0]=1;
os.write(ack,0,1);
os.flush();

socket.close();
ByteArrayInputStream bais=new ByteArrayInputStream(response);
ObjectInputStream ois=new ObjectInputStream(bais);
res=(Response)ois.readObject();
}catch(Exception exception)
{
System.out.println(exception);
throw new NetworkException(exception.getMessage());
}
return res;
}
}
