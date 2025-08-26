package com.ashvin.nframework.server;
import java.net.*;
import java.util.*;
import java.lang.reflect.*;
import java.io.*;
import com.ashvin.nframework.server.annotations.*;
public class NFrameworkServer
{
private ServerSocket serverSocket;
private Set<Class> tcpNetworkServiceClasses;
private Map<String,TCPService> services; 
public NFrameworkServer()
{
tcpNetworkServiceClasses=new HashSet<>();
services=new HashMap<>();
}
public void registerClass(Class c)
{
tcpNetworkServiceClasses.add(c);
Path pathOnType;
Path pathOnMethod;
Method methods[];
String fullPath;
TCPService tcpService=null;
pathOnType=(Path)c.getAnnotation(Path.class);
if(pathOnType==null) return;
methods=c.getMethods();
for(Method method:methods)
{
pathOnMethod=(Path)method.getAnnotation(Path.class);
if(pathOnMethod==null) continue;
fullPath=pathOnType.value()+pathOnMethod.value();
tcpService=new TCPService();
tcpService.method=method;
tcpService.c=c;
tcpService.path=fullPath;
services.put(fullPath,tcpService);
}
}
public TCPService getTCPService(String path)
{
return services.get(path);
}
public void start()
{
try
{
serverSocket=new ServerSocket(5050);
RequestProcessor requestProcessor;
Socket socket;
while(true)
{
socket=serverSocket.accept();
requestProcessor=new RequestProcessor(this,socket);
}
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
