package com.thinking.machines.utils;

import java.io.*;
public class Keyboard
{
private BufferedReader bufferedReader;
public Keyboard()
{
bufferedReader=new BufferedReader(new InputStreamReader(System.in));
}
public String getString()
{
String str;
try
{
str=bufferedReader.readLine();
}catch(IOException ioException)
{
str="";
}
return str;
}
public String getString(String messege)
{
System.out.print(messege);
return getString();
}
public char getCharacter()
{
return getString().charAt(0);
}
public char getCharacter(String messege)
{
System.out.print(messege);
return getCharacter();
}
public long getLong()
{
long l;
try
{
l=Long.parseLong(getString());
}catch(NumberFormatException  numberFormatException)
{
l=0;
}
return l;
}
public long getLong(String messege)
{
System.out.print(messege);
return getLong();
}
public int getInt()
{
int i;
try
{
i=Integer.parseInt(getString());
}catch(NumberFormatException numberFormateException)
{
i=0;
}
return i;
}
public int getInt(String messege)
{
System.out.print(messege);
return getInt();
}
public short getShort()
{
short x;
try
{
x=Short.parseShort(getString());
}catch(NumberFormatException numberFormatException)
{
x=0;
}
return x;
}
public short getShort(String messege)
{
System.out.print(messege);
return getShort();
}
public byte getByte()
{
byte x;
try
{
x=Byte.parseByte(getString());
}catch(NumberFormatException numberFormatException)
{
x=0;
}
return x;
}
public byte getByte(String messege)
{
System.out.print(messege);
return getByte();
}
public float getFloat()
{
float x;
try
{
x=Float.parseFloat(getString());
}catch(NumberFormatException numberFormatException)
{
x=0.0f;
}
return x;
}
public float getFloat(String messege)
{
System.out.print(messege);
return getFloat();
}
public double getDouble()
{
double x;
try
{
x=Double.parseDouble(getString());
}catch(NumberFormatException numberFormatException)
{
x=0.0;
}
return x;
}
public double getDouble(String messege)
{
System.out.print(messege);
return getDouble();
}
public boolean getBoolean()
{
boolean x;
try
{
x=Boolean.parseBoolean(getString());
}catch(NumberFormatException numberFormatException)
{
x=false;
}
return x;
}
public boolean getBoolean(String messege)
{
System.out.print(messege);
return getBoolean();
}
}
