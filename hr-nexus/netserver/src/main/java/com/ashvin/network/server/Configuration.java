package com.ashvin.network.server;
import org.xml.sax.*;
import javax.xml.xpath.*;
import java.io.*;
import com.ashvin.network.common.exceptions.*;
class Configuration 
{
private static int port=-1;
private static boolean fileMissing=false;
private static boolean malformed=false;
static public int getPort() throws NetworkException
{
if(fileMissing) throw new NetworkException("Missing file server.xml, read documentation.");
if(malformed) throw new NetworkException("server.xml not configured according to documentation.");
if(port<0 || port>49151) throw new NetworkException("server.xml contain invalid port number, read documentation.");
return port;
}
static
{
try
{
File file=new File("server.xml");
if(file.exists()) 
{
InputSource inputSource=new InputSource("server.xml");
XPath xpath=XPathFactory.newInstance().newXPath();
String port=xpath.evaluate("//server/@port",inputSource);
Configuration.port=Integer.parseInt(port);
}
else
{
System.out.println("Missing file server.xml, read documentation.");
fileMissing=true;
}
}catch(Exception exception)
{
malformed=true;
}
}
}
