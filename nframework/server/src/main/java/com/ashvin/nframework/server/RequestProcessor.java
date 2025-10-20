package com.ashvin.nframework.server;
import com.ashvin.nframework.common.*;
import com.ashvin.nframework.server.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.nio.charset.*;
import java.lang.reflect.*;
class RequestProcessor extends Thread //accessed only inside package
{
private NFrameworkServer server;
private Socket socket;
RequestProcessor(NFrameworkServer server,Socket socket)
{
this.server=server;
this.socket=socket;
start();
}
public void run()
{
try
{
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
int bytesReadCount;
long bytesToRecieve;
long bytesToSend;
Request request;
Response response;
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
//Over here,1) we have to transfer that requestBytes to JSONString with UTF Standard
// 	    2) convert it to object -> 
//	    3) invoke method from respective object, get result as object, transfer from object to JSONString, create byte array with UTF 
// 	    4) send that byte array to client side.
// That's how we make it data-layer independent framework
String requestJSONString=new String(requestBytes,StandardCharsets.UTF_8);
request=JSONUtil.fromJSON(requestJSONString,Request.class);
String servicePath=request.getServicePath();
TCPService tcpService=this.server.getTCPService(servicePath);
response=new Response();
if(tcpService==null)
{
response.setSuccess(false);
response.setResult("");
response.setException(new RuntimeException("Invalid path: "+servicePath));
}
else
{
Class c=tcpService.c;
Method method=tcpService.method;
try
{
//Here more to add related to the processing of new object created using getClassObject method
Object serviceObject=null;
try
{
Method m=c.getMethod("get"+c.getSimpleName());
serviceObject=m.invoke(c);
}catch(Throwable t)
{
//do nothing
}
if(serviceObject==null)
{
serviceObject=c.newInstance();
}
Object result=method.invoke(serviceObject,request.getArguments());
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}catch(InstantiationException | IllegalAccessException g)
{
response.setSuccess(false);
response.setResult("");
response.setException(new RuntimeException("Unable to create object to service class assiciated with the path : "+servicePath));
}
catch(InvocationTargetException invocationTargetException)
{
Throwable cause=invocationTargetException.getCause();
//if(cause instanceof ???) 		//We can use this for specific type of exception handling
response.setSuccess(false);
response.setResult("");
response.setException(cause);
}
}

String responseJSONString=JSONUtil.toJSON(response);
responseBytes=responseJSONString.getBytes(StandardCharsets.UTF_8);
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
socket.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}
}
