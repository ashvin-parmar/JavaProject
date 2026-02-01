import java.io.*;
import java.net.*;
import java.util.*;

class Pipelines
{
public String connectionId;
public PipeLine2Send pipeLine2Send;
public PipeLine2Receive pipeLine2Receive;
}
class Server
{
private HashMap<String,Pipelines> pipelines;
private HashMap<String,Object[]> socketStreams;
private ServerSocket serverSocket1;
private ServerSocket serverSocket2;
private Application application;
private Thread threadForSocket1;
private Thread threadForSocket2; 
public Server(Application application)
{
this.pipelines=new HashMap<>();
this.socketStreams=new HashMap<>();
this.application=application;
}
public void start()
{
try
{
this.serverSocket1=new ServerSocket(5050);
this.serverSocket2=new ServerSocket(4040);
this.threadForSocket1=new Thread(()->{
Socket socket=null;
InputStream inputStream=null;
OutputStream outputStream=null;
InputStreamReader inputStreamReader=null;
OutputStreamWriter outputStreamWriter=null;
StringBuffer stringBuffer=null;
String connectionId;
int x=0;
int i=0;
String request,response;
while(true)
{
try
{
System.out.println("Server socket 1 is ready to accept request on port 5050");
socket=serverSocket1.accept();
inputStream=socket.getInputStream();
inputStreamReader=new InputStreamReader(inputStream);
i=0;
while(true)
{
x=inputStreamReader.read();
if(x=='#' || i==10) break;
stringBuffer.append((char)x);
i++;
}
if(x!='#')
{
response="INVALID#";
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
socket.close();
continue;
}
request=stringBuffer.toString();
if(request.equals("CONNECT")==false)
{
response="INVALID#";
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
socket.close();
continue;
}
connectionId=UUID.randomUUID().toString();
response=connectionId+"#";
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
Object objects[]=new Object[6];
objects[0]=socket;
objects[1]=inputStream;
objects[2]=inputStreamReader;
objects[3]=outputStream;
objects[4]=outputStreamWriter;
objects[5]=new Date();
socketStreams.put(connectionId,objects);
}catch(Exception exception)
{
System.out.println(exception);
}
}
});
this.threadForSocket1.start();
this.threadForSocket2=new Thread(()->{
Socket socket2=null;
InputStream inputStream=null;
InputStreamReader inputStreamReader=null;
OutputStream outputStream=null;
OutputStreamWriter outputStreamWriter=null;
int x=0,i=0;
String request="";
String response="";
StringBuffer stringBuffer=null;
while(true)
{
try
{
System.out.println("Server socket 2 is ready to accept request on port 4040");
socket2=serverSocket2.accept();
inputStream=socket2.getInputStream();
inputStreamReader=new InputStreamReader(inputStream);

while(true)
{
x=inputStreamReader.read();
if(x=='#' || i==100) break;
stringBuffer.append((char)x);
i++;
}
if(x!='#') 
{
response="INVALID#";
outputStream=socket2.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
socket2.close();
continue;
}
request=stringBuffer.toString();
String connectionId=request;
Object[] objects=socketStreams.get(connectionId);
if(objects==null)
{
response="INVALID#";
outputStream=socket2.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
socket2.close();
continue;
}
/* 
Extract the streams from objects and call the sender and receiver 
parameterized contructors from it with 
	1) application, 
	1.5) connectionId,	[clientId]
	2) socket, 
	3) inputStream
	4) inputStreamReader
	5) outputStream
	6) outputStreamWriter	
because we can not create multiple stream to fetch and send data. 
Create objects of Sender and Receiver and activate those thread.
add the clientId, socket1 and socket2 to the hashmap with name 
*/	

}catch(Exception exception)
{
System.out.println(exception);
}
}
});
this.threadForSocket2.start();
}catch(Exception exception)
{
System.out.println(exception);
}
}

}
