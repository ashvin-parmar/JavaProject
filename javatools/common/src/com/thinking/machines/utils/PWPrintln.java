package com.thinking.machines.utils;
import java.io.*;

public class PWPrintln
{
public static void main(String gg[])
{
try
{
String filename=gg[0];
File file=new File(filename);
if(file.exists()==false)
{
System.out.println("File: "+filename+" does not exists");
return ;
}
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
String line;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
line=randomAccessFile.readLine();
line=line.replaceAll("\"","\\\\\"");
tmpRandomAccessFile.writeBytes("pw.println(\""+line+"\");\r\n");
}
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(Exception exception)
{
System.out.println("Problem: "+exception.getMessage());
}
}
} 
