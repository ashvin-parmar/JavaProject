// Networking + Multi-Threading + Serializable Data + Communication 
import java.net.*;
import java.io.*;

class RequestProcessor extends Thread
{
private Socket socket;
RequestProcessor(Socket socket)
{
this.socket=socket;
start();
}
public void run()
{
try
{
int i,j,x,k;
InputStream inputStream=socket.getInputStream();
byte header[]=new byte[1024];
byte tmp[]=new byte[1024];
int chunkSize;
int bytesToRead;
int bytesReadCount=0;
j=0;
i=0;
//Header receive
bytesToRead=1024;
while(j<bytesToRead)
{
bytesReadCount=inputStream.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j+=bytesReadCount;
}
//Extract length from header
int requestLength;
requestLength=0;
i=1023;
j=1;
while(i>=0)
{
requestLength=requestLength+(header[i]*j);
j=j*10;
i--;
}
//Sends acknowledgement
byte ack[]=new byte[1];
OutputStream os=socket.getOutputStream();
os.write(ack,0,1);
os.flush();

//Receive Data as request
byte request[]=new byte[requestLength];
chunkSize=1024;
j=0;
i=0;
while(j<requestLength)
{
bytesReadCount=inputStream.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++) 
{
request[i]=tmp[k];
i++;
}
j+=bytesReadCount;
}

//aclknowledgement NOT to sends   [MOST IMPORTANT]
//ack[0]=1;
//os.write(ack,0,1);
//os.flush();

ByteArrayInputStream bais=new ByteArrayInputStream(request);
ObjectInputStream ois=new ObjectInputStream(bais);
Student student=(Student)ois.readObject();
System.out.println("Roll number: "+student.rollNumber);
System.out.println("Name: "+student.name);
System.out.println("Gender: "+student.gender);
System.out.println("City code: "+student.city.code);
System.out.println("City name: "+student.city.name);

String responseString="Data saved";
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(responseString);
oos.flush();
byte[] response=baos.toByteArray();
int responseLength=response.length;
//header created to set response length
header=new byte[1024];
i=1023;
x=responseLength;
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
//Header sends
os.write(header,0,1024);
os.flush();

//Acknowlegement receive
while(true)
{
bytesReadCount=inputStream.read(ack);
if(bytesReadCount==-1) continue;
break;
}

//Response Data send
int bytesToSend=responseLength;
i=0;
j=0;
chunkSize=1024;
while(j<bytesToSend)
{
if((bytesToSend-j)<=chunkSize) chunkSize=bytesToSend-j;
os.write(response,0,chunkSize);
os.flush();
j+=chunkSize;
}
//Acknowledgement receive
while(true)
{
bytesReadCount=inputStream.read(ack);
if(bytesReadCount==-1) continue;
break;
}
socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}

class Server4
{
private ServerSocket serverSocket;
public Server4()
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
private void startListening()
{
try
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Server is ready to accept request on port 5050");
socket=serverSocket.accept();
requestProcessor=new RequestProcessor(socket);
}
}catch(Exception e)
{
System.out.println(e);
}
}
public static void main(String gg[])
{
Server4 server=new Server4();
}
}
