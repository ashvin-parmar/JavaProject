package com.ashvin.network.common;
import com.google.gson.*;

public class Configuration implements java.io.Serializable
{
static private int port;
static private String host;
static public int getPort()
{
return port;
}
static public String getHost()
{
return host;
}
public void setHost(String h)
{
host=h;
}
public void setPort(int p)
{
port=p;
}
static
{
//Here, this type of class created for which host and port static get methods are provided. Data for port and host are extracted at the time of loading class Configuration, one time only in complete cycle of Application. static initializer blocks are the solution for this.
//We have to extract data from client.cfg file and if file does not exist or open ==> close the application --> "System.exit(0);"
String filename="/client.cfg";
File file=new File(filename);
if(file.exist()==false) 
{
System.out.println("client.cfg file not available, Client configuration not matched.");
System.exit(0);
}
int bytesReadCount=0;
int length=file.length();
Byte bytes[]=new Byte[length];
Byte tmp[]=new Byte[1024];
int i,j;
long x;
FileInputStream fis=new FileInputStream();
x=0;
j=0;
while(x<length)
{
bytesReadCount=fis.read(tmp);
if(bytesReadCount==-1) continue;
for(i=0;i<bytesReadCount;i++) bytes[j]=tmp[i];
x+=bytesReadCount;
}
String jsonString=bytes.toString();

Gson gson=new Gson();
Configuration c=(Configuration)gson.fromJson(jsonString,Class.Configuration);
port=c.getPort();
host=c.getHost();
}
}
