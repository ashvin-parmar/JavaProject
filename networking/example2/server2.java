//Multithreaded server

import java.io.*;
import java.net.*;
import java.util.*;
class RequestProcessor extends Thread
{
Socket socket;
RequestProcessor(Socket socket)
{
this.socket=socket;
start();
}
public void run()
{
try
{
InputStream inputStream=this.socket.getInputStream();
InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
StringBuffer stringBuffer=new StringBuffer();
int x;
while(true)
{
x=inputStreamReader.read();
if(x==-1) break;
if(x=='#') break;
stringBuffer.append((char)x);
}
String request=stringBuffer.toString();
int c1,c2;
c1=request.indexOf(',',0);
c2=request.indexOf(',',c1+1);
String pc1,pc2,pc3;
pc1=request.substring(0,c1);
pc2=request.substring(c1+1,c2);
pc3=request.substring(c2+1);
int rollNumber=Integer.parseInt(pc1);
String name=pc2;
String gender=pc3;
System.out.println("Roll Number: "+rollNumber);
System.out.println("Name: "+name);
System.out.println("Gender: "+gender);

String response="Data Saved#";
OutputStream os=socket.getOutputStream();
OutputStreamWriter osw=new OutputStreamWriter(os);
osw.write(response);
osw.flush();
socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}

class Server2
{
ServerSocket serverSocket;
List<RequestProcessor> requestThreads;
Server2()
{
try
{
serverSocket=new ServerSocket(5050);
startListening();
}catch(Exception exception)
{
System.out.println(exception);
}
}
private void startListening() throws Exception
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Server start to accept request on port 5050");
socket=serverSocket.accept();
requestProcessor=new RequestProcessor(socket);
if(requestThreads!=null) requestThreads.add(requestProcessor);
}
}
public void joinThread()
{
for(RequestProcessor requestThread:requestThreads)
{
try
{
requestThread.join();
}
catch(InterruptedException ie)
{
System.out.println(ie);
}
}
}
public static void main(String gg[])
{
Server2 server2=new Server2();
server2.joinThread();
}
}
