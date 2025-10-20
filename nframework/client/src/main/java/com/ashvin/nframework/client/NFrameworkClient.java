package com.ashvin.nframework.client;
import com.ashvin.nframework.common.exceptions.*;
import com.ashvin.nframework.common.*;
import java.net.*;
import java.nio.charset.*;
import java.io.*;
public class NFrameworkClient
{
private String host;
private int port;
public NFrameworkClient(String host,int port)
{
this.host=host;
this.port=port;
}
public Object execute(String servicePath,Object ...arguments) throws Throwable
{
try
{
Socket socket=new Socket(host,port);
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
byte header[]=new byte[1024];
byte tmp[]=new byte[1024];
byte ack[]=new byte[1];
ack[0]=(byte)(1);
int i,k,l;
long j;
int bytesReadCount;
int chunkSize=1024;
Request request=new Request();
request.setServicePath(servicePath);
request.setArguments(arguments);
String requestJSONString=JSONUtil.toJSON(request);
byte requestBytes[]=requestJSONString.getBytes(StandardCharsets.UTF_8);
long requestLength=requestBytes.length;
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
int bytesToRecieve=1024;
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
long responseLength=0;
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
byte responseBytes[]=new byte[(int)responseLength];
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
socket.close();
String responseJSONString=new String(responseBytes,StandardCharsets.UTF_8);
//System.out.println(responseJSONString);
Response response=JSONUtil.fromJSON(responseJSONString,Response.class);
if(response.getSuccess()==true)
{
return response.getResult();
}
else
{
throw response.getException();
//Throwable t=response.getException();
//System.out.println(t);
//throw response.getException();
//Class c=Class.forName(t.getClass().getName());
//throw (Throwable) (c.getConstructor(String.class).newInstance(t.getMessage()));
}
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
return null;
}
}
}
