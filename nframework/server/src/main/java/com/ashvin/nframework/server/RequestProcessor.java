package com.ashvin.nframework.server;
import com.ashvin.nframework.common.*;
import com.ashvin.nframework.common.exceptions.*;
import java.io.*;
import java.util.*;
import java.net.*;

class RequestProcessor extends Thread
{
private NFrameworkServer server;
private Socket socket;
RequestProcessor(NFrameworkServer server,Socket socket)
{
this.server=server;
this.socket=socket;
start();
}
public void run()
{

}
}
