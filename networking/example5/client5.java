//File Transfer Using Networking

import java.net.*;
import java.io.*;

class Client5
{
public static void main(String gg[])
{
try
{
String fileName=gg[0];
File file=new File(fileName);
if(file.exists()==false)
{
System.out.println("File: "+fileName+" does not exists.");
return;
}
if(file.isDirectory()==true)
{
System.out.println(fileName+" is a directory,not a file.");
return ;
}
long requestLength=file.length();
String name=file.getName();
byte[] header=new byte[1024];
int i,k;
long x,j;
i=0;
x=requestLength;
while(x>0)
{
header[i]=(byte)(x%10);
i++;
x/=10;
}
header[i]=(byte)',';
i++;
for(k=0;k<name.length();k++)
{
header[i]=(byte)name.charAt(k);
i++;
}
while(i<1024)
{
header[i]=(byte)32;
i++;
}

Socket socket=new Socket("localhost",5050);
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();

//Header Sends
os.write(header,0,1024);
os.flush();

//Acknowledgement Receive
byte ack[]=new byte[1];
int byteReadCount;
while(true)
{
byteReadCount=is.read(ack);
if(byteReadCount==-1) continue;
break;
}
//Data sends to Server side
FileInputStream fis=new FileInputStream(file);

long bytesToSend=requestLength;
int chunkSize=1024;
byte[] objectsByte=new byte[1024];
j=0;
int bytesReadCount;
while(j<bytesToSend)
{
bytesReadCount=fis.read(objectsByte);
if(bytesReadCount==-1) continue;
os.write(objectsByte,0,bytesReadCount);
os.flush();
j=j+bytesReadCount;
}
fis.close();
//Acknowledgement Receive
while(true)
{
byteReadCount=is.read(ack);
if(byteReadCount==-1) continue;
break;
}
System.out.println("File uploaded");
socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
