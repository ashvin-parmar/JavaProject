import java.io.*;



class abcd
{
public static void main(String args[])
{
try
{
File file=new File("user.data");
if(file.exists()==false) 
{
System.out.println("Users configuration file not available");
return ;
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
String line;
String username;
String password;
int indexOfFoundComma;
while(randomAccessFile.getFilePointer()<file.length())
{
line=randomAccessFile.readLine();
indexOfFoundComma=line.indexOf(',');
username=line.substring(0,indexOfFoundComma);
password=line.substring(indexOfFoundComma+1);
System.out.println("Username: "+username+", Password: "+password);
}
randomAccessFile.close();
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}
}
