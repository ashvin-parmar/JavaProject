
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
stringBuffer.append(x);
}
ByteArrayInputStream bais=new ByteArrayInputStream(stringBuffer);
ObjectInputStream ois=new ObjectInputStream(bais);
Student student=(Student)ois.readObject();

System.out.println("Roll Number: "+student.rollNumber);
System.out.println("Name: "+student.name);
System.out.println("Gender: "+student.gender);
System.out.println("City code: "+student.city.code);
System.out.println("City name: "+student.city.name);

String response="Data Saved";
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(response);
oos.flush();
byte b[];
b=baos.toByteArray();
OutputStream os=socket.getOutputStream();
OutputStreamWriter osw=new OutputStreamWriter(os);
osw.write(b.toString());
osw.flush();
socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}

class Server3
{
ServerSocket serverSocket;
List<RequestProcessor> requestThreads;
Server3()
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
