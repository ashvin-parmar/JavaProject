package com.ashvin.chess.client;

import com.ashvin.nframework.client.*;
public class Main 
{
public static void main(String args[])
{
if(args.length<2)
{
System.out.println("Usage: com.ashvin.chess.client.Main [username] [password]");
return;
}
try
{
NFrameworkClient client=new NFrameworkClient();
String username=args[0];
String password=args[1];
Object[] arguments={username,password};
boolean authentic=(boolean)client.execute("/ChessServer/memberAuthentic",arguments);
if(!authentic)
{
System.out.println("Invalid username/password");
return ;
}
ChessUI chessUI=new ChessUI(username);
chessUI.showUI();
}catch(Throwable t)
{
System.out.println("Some problem");
System.out.println(t);
System.out.println(t.toString());
}
}
}
