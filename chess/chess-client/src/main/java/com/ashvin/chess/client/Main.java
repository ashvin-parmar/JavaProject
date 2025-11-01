package com.ashvin.chess.client;

import com.ashvin.nframework.client.*;

public class Main
{
public static void main(String args[])
{
if(args.length<2) 
{
System.out.println("Usage: com.ashvin.chess.client.Main [username] [password]");
return ;
}
String username=args[0];
String password=args[1];
try
{
NFrameworkClient client=new NFrameworkClient("localhost",5050);
Object[] obj={username,password};
boolean b=(boolean)client.execute("/ChessServer/login",obj);
if(b==false)
{
System.out.println("Invalid username/password");
return ;
}
ChessUI chessUI=new ChessUI(username);
chessUI.showUI();
}catch(Throwable t)
{
System.out.println(t.toString());
}
}
}
