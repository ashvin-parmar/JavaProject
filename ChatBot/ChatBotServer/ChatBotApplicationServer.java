import java.io.*;

class ChatBotApplicationServer implements Application
{
private HashMap<String,User> users;
Server server;
public ChatBotServer()
{
users=new HashMap<>();
server=new Server(this);
}
public start()
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
User user=null;
while(randomAccessFile.getFilePointer()<file.length())
{
line=randomAccessFile.readLine();
indexOfFoundComma=line.indexOf(',');
username=line.substring(0,indexOfFoundComma);
password=line.substring(indexOfFoundComma+1);
user=new User();
user.setUsername(username);
user.setPassword(password);
users.put(username,user);
//System.out.println("Username: "+username+", Password: "+password);
}
randomAccessFile.close();
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}
server.start();
}
public byte[] onRequestBytes(String id,byte bytes[])
{
return null;
}
public void onResponseBytes(String id,byte bytes[])
{
}
public void onConnected(String id)
{
}
}
