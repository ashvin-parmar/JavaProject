package com.ashvin.n2p;
import java.util.*;
import java.io.*;
import java.net.*;

public class Client
{
private Application application;
private String clientId;
private String server;
private int portNumber1,portNumber2;
public Client(Application application,String server,int portNumber1,int portNumber2)
{
this.application=application;
this.server=server;
this.portNumber1=portNumber1;
this.portNumber2=portNumber2;
}
public void connect() throws ConnectionException
{
try
{
Socket socket1=new Socket(server,portNumber1);
OutputStream outputStream1=socket1.getOutputStream();
OutputStreamWriter outputStreamWriter1=new OutputStreamWriter(outputStream1);
String request="CONNECT#";
outputStreamWriter1.write(request);
outputStreamWriter1.flush();

InputStream inputStream1=socket1.getInputStream();
InputStreamReader inputStreamReader1=new InputStreamReader(inputStream1);
StringBuffer stringBuffer=new StringBuffer();
int x=0;
while(true)
{
x=inputStreamReader1.read();
if(x=='#') break;
stringBuffer.append((char)x);
}
String response=stringBuffer.toString();
if(response.equals("INVALID"))
{
throw new ConnectionException("Unable to connect, invalid response1");
}
String connectionId=response;

Socket socket2=new Socket(server,portNumber2);
OutputStream outputStream2=socket2.getOutputStream();
OutputStreamWriter outputStreamWriter2=new OutputStreamWriter(outputStream2);
String request2=connectionId+"#";
outputStreamWriter2.write(request2);
outputStreamWriter2.flush();

InputStream inputStream2=socket2.getInputStream();
InputStreamReader inputStreamReader2=new InputStreamReader(inputStream2);
stringBuffer=new StringBuffer();
while(true)
{
x=inputStreamReader2.read();
if(x=='#') break;
stringBuffer.append((char)x);
}
if(x!='#')
{
throw new ConnectionException("Unable to connect");
}
String response2=stringBuffer.toString();
if(response2.equals("INVALID"))
{
throw new ConnectionException("Unable to connect, invalid response2");
//socket2.close();			//Because TM's solution does not have this
}
//Over here complete connection created, now create sender and receiver objects and activate them 
PipeLines pipeLines;
PipeLine2Send pipeLine2Send;
PipeLine2Receive pipeLine2Receive;
pipeLines=new PipeLines();
//with port 5050
pipeLine2Send=new PipeLine2Send(application,connectionId,socket1,inputStream1,inputStreamReader1,outputStream1,outputStreamWriter1);
// with port 4040
pipeLine2Receive=new PipeLine2Receive(application,connectionId,socket2,inputStream2,inputStreamReader2,outputStream2,outputStreamWriter2);

pipeLine2Send.start();
pipeLine2Receive.start();
pipeLines=new PipeLines();
pipeLines.connectionId=connectionId;
pipeLines.pipeLine2Send=pipeLine2Send;
pipeLines.pipeLine2Receive=pipeLine2Receive;
this.application.onConnected(connectionId);
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
