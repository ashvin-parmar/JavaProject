import java.io.*;
import java.net.*;
class Client2
{
public static void main(String ggp[])
{
try
{
int rollNumber=Integer.parseInt(ggp[0]);
String name=ggp[1];
String gender=ggp[2];
String request=rollNumber+","+name+","+gender+'#';
Socket socket=new Socket("localhost",5050);
OutputStream os=socket.getOutputStream();
OutputStreamWriter osw=new OutputStreamWriter(os);
osw.write(request);
osw.flush();

InputStream is=socket.getInputStream();
InputStreamReader isr=new InputStreamReader(is);
int x;
StringBuffer sb=new StringBuffer();
while(true)
{
x=isr.read();
if(x==-1) break;
if(x=='#') break;
sb.append((char)x);
}
String response=sb.toString();
System.out.println(response);
socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
