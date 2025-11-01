package com.ashvin.chess.server;

import com.ashvin.nframework.server.*;
public class Main
{
public static void main(String args[])
{
try
{
NFrameworkServer nfs=new NFrameworkServer();
nfs.registerClass(ChessServer.class);
nfs.start(5050);
}catch(Exception e)
{
System.out.println(e);
}
}
}
