//Tool to generate setter/getter and constructor

package com.thinking.machines.utils;

import java.lang.reflect.*;
import java.io.*;
public class TMSetterGetterGenerator
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage: [java -classpath path_to_class:. com.thiking.machines.utils.TMSetterGetterGenerator class_name_to_generate_setter_getter]");
return ;
}
String className=gg[0];
try
{
Class c=Class.forName(className);
Field [] fields=c.getDeclaredFields();
Field field;
String fieldName;
Class fieldType;
String fieldTypeName;
String line,tmp;
TMList<String> list=new TMArrayList<String>();		//using DataStructure

for(int i=0;i<fields.length;i++)
{
field=fields[i];
fieldName=field.getName();
fieldType=field.getType();
fieldTypeName=fieldType.getName();
if(fieldName.charAt(0)>=97 && fieldName.charAt(0)<=122)
{
tmp=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
}
else
{
tmp=fieldName;
}
String setterName,getterName;
setterName="set"+tmp;
getterName="get"+tmp;
line="public void "+setterName+"("+fieldTypeName+" "+fieldName+")";
list.add(line);
list.add("{");
line="this."+fieldName+"="+fieldName+";";
list.add(line);
list.add("}");
line="public "+fieldTypeName+" "+getterName+"()";
list.add(line);
list.add("{");
line="return this."+fieldName+";";
list.add(line);
list.add("}");
}

File file=new File("tmp.tmp");
if(file.exists()) file.delete();
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");

TMIterator<String> iter=list.iterator();
while(iter.hasNext())
{
String data=iter.next();
randomAccessFile.writeBytes(data+"\r\n");	//'\r\n' because we wants to show new line effect while reading from file
}
randomAccessFile.close();
System.out.println("Setter getter for "+c.getSimpleName()+" class has been generated in 'tmp.tmp' file.");
}catch(ClassNotFoundException classNotFoundException)
{
System.out.println("Class "+classNotFoundException.getMessage()+" not found.");
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}
}

