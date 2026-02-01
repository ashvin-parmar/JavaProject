import java.util.*;
import java.io.*;
import java.net.*;

class Client
{
private Application application;
private String connectionId;
Client(Application application)
{
this.application=application;
}
public boolean connect()
{
Socket socket=new Socket("localhost",5050);
OutputStream outputStream=socket.getOutputStream();
OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
String request="CONNECT#";
outputStreamWriter.writer(request);
outputStreamWriter.flush();

InputStream inputStream=socket.getInputStream();
InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
StringBuffer stringBuffer;
int x=0;
int i=0;
while(true)
{
x=inputStreamReader.read();
if(x=='#' || i==100) break;
stringBuffer.append((char)x);
i++;
}
if(x!='#')
{
System.out.println("Unable to connect to the server socket 1.");
socket.close();
return;
}
String response=stringBuffer.toString();
if(response.equals("INVALID"))
{
System.out.println("Invalid request send, Unable to connect to the server socket 1");
socket.close();
return ;
}
this.connectionId=response;


Socket socket2=new Socket("localhost",4040);
OutputStream outputStream2=socket2.getOutputStream();
OutputStreamWriter outputStreamWriter2=new OutputStreamWriter(outputStream2);
String request2="CONNECT#";
outputStreamWriter2.writer(request2);
outputStreamWriter2.flush();

InputStream inputStream2=socket2.getInputStream();
InputStreamReader inputStreamReader2=new InputStreamReader(inputStream2);
while(true)
{
x=inputStreamReader2.read();
if(x=='#' || i==100) break;
stringBuffer.append((char)x);
i++;
}
if(x!='#')
{
System.out.println("Unable to connect to the server socket 2.");
socket2.close();
return;
}
String response2=stringBuffer.toString();
if(response2.equals("INVALID"))
{
System.out.println("Invalid request or id send, Unable to connect to the server socket 2");
socket2.close();
return ;
}
//Over here complete connection created, now create sender and receiver objects and activate them 

}

}
