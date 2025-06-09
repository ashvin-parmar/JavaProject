import java.net.*;
import java.io.*;
class FTClient
{
public static void main(String gg[])
{
try
{
String filename=gg[0];
File file=new File(filename);
if(file.exists()==false) 
{
System.out.println("File: "+filename+" does not exists.");
return;
}
if(file.isDirectory())
{
System.out.println(filename+" is a directory, not a file.");
return ;
}
long fileLength=file.length();
String name=file.getName();
int i,j;
long x;
byte[] header=new byte[1024];

x=fileLength;
i=0;

while(x>0)
{
header[i]=(byte)(x%10);
x/=10;
i++;
}
header[i]=(byte)',';
i++;
for(j=0;j<name.length();j++)
{
header[i]=(byte)(name.charAt(j));
i++;
}
while(i<1024)
{
header[i]=(byte)32;
i++;
}
//Header written complete
Socket socket=new Socket("localhost",5050);
OutputStream os=socket.getOutputStream();
os.write(header,0,1024);
os.flush();


InputStream is=socket.getInputStream();
int bytesReadCount;
//Ack receive
byte ack[]=new byte[1];
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}

FileInputStream fis=new FileInputStream(file);
int chunkSize=4096;
byte bytes[]=new byte[chunkSize];
i=0;
x=0;
while(x<fileLength)
{
bytesReadCount=fis.read(bytes);
if(bytesReadCount==-1) continue;
os.write(bytes,0,bytesReadCount);
os.flush();
x+=bytesReadCount;
}
fis.close();

while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
System.out.println("File: "+name+" uploaded at : "+file.getAbsolutePath());
socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
