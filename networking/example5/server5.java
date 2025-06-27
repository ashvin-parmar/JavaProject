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
int i,x,k;
int j;
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
long requestLength;
requestLength=0;
i=0;
j=1;
while(header[i]!=',')
{
requestLength=requestLength+(header[i]*j);
j=j*10;
i++;
}
i++;	//',' reads
String fileName="";
while(i<1024)
{
fileName+=(char)header[i];
i++;
}
fileName=fileName.trim();

//Sends acknowledgement
byte ack[]=new byte[1];
OutputStream os=socket.getOutputStream();
os.write(ack,0,1);
os.flush();
//Receive Data as request
//byte request[]=new byte[requestLength];
 
File file=new File("uploads"+File.separator+fileName);
if(file.exists()==true) file.delete();
FileOutputStream fos=new FileOutputStream(file);
// Check file created or not
j=0;
while(j<requestLength)
{
bytesReadCount=inputStream.read(tmp);
if(bytesReadCount==-1) continue;
//write in file
fos.write(tmp,0,bytesReadCount);
j+=bytesReadCount;
}
fos.close();
//aclknowledgement NOT to sends   [MOST IMPORTANT]
ack[0]=1;
os.write(ack,0,1);
os.flush();

socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}

class Server5
{
private ServerSocket serverSocket;
public Server5()
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
Server5 server=new Server5();
}
}
