import java.io.*;
import java.net.*;

class Server1
{
ServerSocket serverSocket;
Socket socket;
public Server1()
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
private void startListening() throws IOException
{
//try
//{
InputStream is;
InputStreamReader isr;
OutputStream os;
OutputStreamWriter osw;
String response,request;
StringBuffer sb;
int x,c1,c2;
String pc1,pc2,pc3;
int rollNumber;
String name,gender;
while(true)
{
System.out.println("Server is ready to accept request at 5050");
socket=serverSocket.accept();
is=socket.getInputStream();
isr=new InputStreamReader(is);
sb=new StringBuffer();
while(true)
{
x=isr.read();
if(x==-1) break;
if(x=='#') break;
sb.append((char)x);
}
request=sb.toString();
c1=request.indexOf(',');
c2=request.indexOf(',',c1+1);
pc1=request.substring(0,c1);
pc2=request.substring(c1+1,c2);
pc3=request.substring(c2+1);
rollNumber=Integer.parseInt(pc1);
name=pc2;
gender=pc3;
System.out.println("Roll number: "+rollNumber+",Name: "+name+",Gender: "+gender);
response="Data saved#";
os=socket.getOutputStream();
osw=new OutputStreamWriter(os);
osw.write(response);
osw.flush();
socket.close();
}
//}catch(Exception exception)
//{
//System.out.println(exception);
//}
}
public static void main(String gg[])
{
Server1 server=new Server1();
}
}
