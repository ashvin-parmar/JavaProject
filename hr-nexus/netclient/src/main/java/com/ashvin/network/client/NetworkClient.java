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
Response response=null;
try
{
String host="localhost";
int port=5050;

//host=Configuration.getHost();
//port=Configuration.getPort();	//Here, this type of class created for which host and port static get methods are provided. Data for port and host are extracted at the time of loading class Configuration, one time only in complete cycle of Application. static initializer blocks are the solution for this.
//We have to extract data from client.cfg file and if file does not exist or open ==> close the application --> "System.exit(0);"

Gson gson;
String requestJson,responseJson;
byte requestBytes[],responseBytes[];
byte header[],tmp[],bytes[];
long length;
int i,j,k;
long x;
int bytesReadCount,chunkSize;

gson=new Gson();
requestJson=gson.toJson(request);
System.out.println(requestJson);
requestBytes=requestJson.getBytes();
length=requestBytes.length;
j=0;
i=1023;
x=length;
header=new byte[1024];
while(x>0)
{
header[i]=(byte)(x%10);
i--;
x/=10;
}

Socket socket=new Socket(host,port);
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();

os.write(header,0,1024);
os.flush();

bytes=new byte[1024];
bytesReadCount=0;
chunkSize=1024;
x=0;
i=0;
while(x<length)
{
if(length-x<chunkSize) chunkSize=(int)(length-x);
os.write(requestBytes,(int)x,chunkSize);
os.flush();
x+=chunkSize;
}

length=1024;
tmp=new byte[1024];
header=new byte[1024];
x=0;
j=0;
while(x<1024)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(i=0;i<bytesReadCount;i++) header[j++]=tmp[i];
x+=bytesReadCount;
}

x=0;
i=1023;
j=1;
while(i>=0)
{
x=x+(header[i]*j);
j*=10;
i--;
}

responseBytes=new byte[(int)x];
length=x;
i=0;
j=0;
x=0;
while(x<length)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(i=0;i<bytesReadCount;i++) responseBytes[j++]=tmp[i];
x+=bytesReadCount;
}

os.close();
is.close();
socket.close();
responseJson=responseBytes.toString();
response=(Response)gson.fromJson(responseJson,Response.class);
/*
Wrap all the network/socket programming code over here.
1. Serialize Request Object.
2. Connect to the server.
3. Send header and then the serialized form in chunks.
4. Receive back header and then the serialized form of response. 
5. return the reference of Response object.
*/
}catch(Exception exception)
{
System.out.println(exception);
throw new NetworkException(exception.getMessage());
}
return response;
}
}
