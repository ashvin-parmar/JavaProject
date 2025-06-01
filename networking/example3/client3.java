//Reason: Why We are not using OutputStreamWriter and InputStreamReader for reade and write in the message transfer --> Cannot allow byte[] to build connection
//+ Much more hiddent things cannot accessed. 

//Example 4: Using inputStream.read(_size_) and outputStream.write(byte[],_from_where(index)_,_how_many_)
// This allow more complexities with the data sharing things.

import java.io.*;
import java.net.*;
class City
{
public int code;
public String name;
}
class Student 
{
public int rollNumber;
public String name;
public char gender;
public City city;
}
class Client3
{
public static void main(String ggp[])
{
try
{
int rollNumber=Integer.parseInt(ggp[0]);
String name=ggp[1];
String gender=ggp[2];
Student student=new Student();
student.rollNumber=rollNumber;
student.name=name;
student.gender=gender.charAt(0);
student.city=new City();
student.city.code=Integer.parseInt(ggp[3]);
student.city.name=ggp[4];

ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(student);
byte[] b=baos.toByteArray();
oos.flush();

Socket socket=new Socket("localhost",5050);
OutputStream outputStream=socket.getOutputStream();
OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(b.toString());	//Here Problem
outputStreamWriter.flush();

InputStream is=socket.getInputStream();
InputStreamReader isr=new InputStreamReader(is);
StringBuffer sb=new StringBuffer();
int x;
while(true)
{
x=isr.read();
if(x==-1) break;
sb.append(x);
}
ByteArrayInputStream bais=new ByteArrayInputStream(sb);   //Here Problem
ObjectInputStream ois=new ObjectInputStream(bais);
String response=(String)ois.readObject();
System.out.println("Response: "+response);
socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
