//Tool to generate setter/getter and constructor

package com.thinking.machines.utils;

import java.lang.reflect.*;
import java.io.*;
public class TMSetterGetterGenerator
{
private static String getDefaultValue(Class c)
{
String className=c.getName();
if(className.equals("java.lang.Long") || className.equals("long")) return "(long)0";
if(className.equals("java.lang.Integer") || className.equals("int")) return "0";
if(className.equals("java.lang.Short") || className.equals("short")) return "0";
if(className.equals("java.lang.Byte") || className.equals("byte")) return "0";
if(className.equals("java.lang.Float") || className.equals("float")) return "0.0f";
if(className.equals("java.lang.Double") || className.equals("double")) return "0.0";
if(className.equals("java.lang.Character") || className.equals("char")) return "' '";
if(className.equals("java.lang.Boolean") || className.equals("boolean")) return "false";
if(className.equals("java.lang.String")) return "\"\"";
return "null";
}
public static void main(String gg[])
{
if(gg.length!=1 && gg.length!=2)
{
System.out.println("Usage: [java -classpath path_to_class:. com.thiking.machines.utils.TMSetterGetterGenerator class_name_to_generate_setter_getter contructor=false/true]");
return ;
}
if(gg.length==2 && !gg[1].equalsIgnoreCase("constructor=false") && !gg[1].equalsIgnoreCase("constructor=true"))
{
System.out.println("Usage: [java -classpath path_to_class:. com.thiking.machines.utils.TMSetterGetterGenerator class_name_to_generate_setter_getter contructor=false/true]");
return ;
}
String className=gg[0];
try
{
Class c=Class.forName(className);
TMList<String> list=new TMArrayList<String>();		//using DataStructure
String line,tmp;
Field [] fields=c.getDeclaredFields();
Field field;
String fieldName;
Class fieldType;
String fieldTypeName;

if(gg.length==1 || gg[1].equalsIgnoreCase("constructor=true"))
{
line="public "+c.getSimpleName()+"()";
list.add(line);
list.add("{");
for(int i=0;i<fields.length;i++)
{
line="this."+fields[i].getName()+"="+getDefaultValue(fields[i].getType())+";";
list.add(line);
}
list.add("}");
}

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

