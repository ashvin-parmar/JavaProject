package com.ashvin.hr.nexus.server.main;
import com.ashvin.hr.nexus.server.*;
import com.ashvin.network.server.*;
import com.ashvin.network.common.exceptions.*;

public class Main
{
public static void main(String gg[])
{
try
{
RequestHandler requestHandler=new RequestHandler();

NetworkServer server=new NetworkServer(requestHandler);
server.start();

}catch(NetworkException networkException)
{
System.out.println(networkException.getMessage());
}
}
}
