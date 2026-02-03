import java.io.*;
import java.net.*;
import java.util.*;

class Server
{
private HashMap<String,PipeLines> pipeLinesMap;
private HashMap<String,Object[]> socketStreams;
private ServerSocket serverSocket1;
private ServerSocket serverSocket2;
private Application application;
private Thread threadForSocket1;
private Thread threadForSocket2; 
public Server(Application application)
{
this.pipeLinesMap=new HashMap<>();
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
stringBuffer=new StringBuffer();
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
Socket socket=null;
InputStream inputStream=null;
InputStreamReader inputStreamReader=null;
OutputStream outputStream=null;
OutputStreamWriter outputStreamWriter=null;
int x=0,i=0;
String request="";
String response="";
StringBuffer stringBuffer=null;
PipeLines pipeLines;
PipeLine2Send pipeLine2Send;
PipeLine2Receive pipeLine2Receive;
while(true)
{
try
{
System.out.println("Server socket 2 is ready to accept request on port 4040");
socket=serverSocket2.accept();
inputStream=socket.getInputStream();
inputStreamReader=new InputStreamReader(inputStream);
stringBuffer=new StringBuffer();
i=0;
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
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
socket.close();
continue;
}
request=stringBuffer.toString();
String connectionId=request;
Object[] objects=socketStreams.get(connectionId);
if(objects==null)
{
response="INVALID#";
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
socket.close();
continue;
}
this.socketStreams.remove(connectionId);
//with 4040 socket
pipeLine2Send=new PipeLine2Send(application,connectionId,socket,inputStream,inputStreamReader,outputStream,outputStreamWriter);
//with 5050 sockets
pipeLine2Receive=new PipeLine2Receive(application,connectionId,(Socket)objects[0],(InputStream)objects[1],(InputStreamReader)objects[2],(OutputStream)objects[3],(OutputStreamWriter)objects[4]);
pipeLines=new PipeLines();
pipeLines.connectionId=connectionId;
pipeLines.pipeLine2Send=pipeLine2Send;
pipeLines.pipeLine2Receive=pipeLine2Receive;
pipeLine2Send.start();
pipeLine2Receive.start();
pipeLinesMap.put(connectionId,pipeLines);
response="CONNECTED#";
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(response);
outputStreamWriter.flush();
application.onConnected(connectionId);
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
