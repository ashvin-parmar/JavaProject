//File Transfer Server
//Networking + Thread + Swing
import java.net.*;
import java.io.*;

//Swing 
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class RequestProcessor extends Thread
{
private Socket socket;
private JTextArea ta;
RequestProcessor(Socket socket,JTextArea ta)
{
this.socket=socket;
this.ta=ta;
start();
}
public void run()
{
try
{
int i,x,k;
long j;
InputStream inputStream=socket.getInputStream();
byte header[]=new byte[1024];
byte tmp[]=new byte[1024];
int bytesToRead;
int bytesReadCount=0;
j=0;
i=0;
//Header receive
bytesToRead=1024;
while(j<bytesToRead)
{
bytesReadCount=inputStream.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j+=bytesReadCount;
}
//Extract length from header
long fileLength;
fileLength=0;
i=0;
j=1;
while(header[i]!=',')
{
fileLength+=(header[i]*j);
j=j*10;
i++;
}
i++;	//',' reads
StringBuffer sb=new StringBuffer();
while(i<1024)
{
sb.append((char)header[i]);
i++;
}
String fileName=sb.toString();
fileName=fileName.trim();		//Truncate Those end spaces

//Sends acknowledgement
byte ack[]=new byte[1];
OutputStream os=socket.getOutputStream();
os.write(ack,0,1);
os.flush();
//Receive Data as request
//byte request[]=new byte[requestLength];
 
File file=new File("uploads"+File.separator+fileName);		//Separator
		//Seperator (NO), Separator (YES)
if(file.exists()==true) file.delete();
FileOutputStream fos=new FileOutputStream(file);
// Check file created or not
j=0;
int chunkSize=4096;
byte bytes[]=new byte[chunkSize];
while(j<fileLength)
{
bytesReadCount=inputStream.read(bytes);
if(bytesReadCount==-1) continue;
//write in file
fos.write(bytes,0,bytesReadCount);
fos.flush();
j+=bytesReadCount;
}
fos.close();
//aclknowledgement NOT to sends   [MOST IMPORTANT]
ack[0]=1;
os.write(ack,0,1);
os.flush();

System.out.println("File created : "+file.getAbsolutePath()+" of length: "+fileLength);
this.ta.append("File created : "+file.getAbsolutePath()+" of length: "+fileLength+"\n");
socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}

class FTServer 
{
private ServerSocket serverSocket;
private int port;
private JTextArea textArea;
public FTServer(int port,JTextArea ta)
{
this.textArea=ta;
try
{
serverSocket=new ServerSocket(port);
this.port=port;
startListening();
}catch(Exception exception)
{
System.out.println(exception);
}
}
public void setPort(int port)
{
this.port=port;
}
public int getPort()
{
return this.port;
}
public void close()
{
try
{
this.serverSocket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
private void startListening()
{
try
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Server is ready to accept request on port "+port);
this.textArea.append("Server is ready to accept request on port "+port+"\n");
socket=serverSocket.accept();
requestProcessor=new RequestProcessor(socket,textArea);
}
}catch(Exception e)
{
System.out.println(e);
}
}
}


class ServerPsp extends JFrame
{
private JLabel portNumberLabel;
private JTextField portNumberTextField;
private JButton startServer;
private JTextArea details;
private Container container;
private FTServer ftServer;
private enum MODE{NONE,START,STOP};
private MODE mode;
ServerPsp()
{
init();

setSize(400,600);
setLocation(200,100);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void init()
{
portNumberLabel=new JLabel("Port Number: ");
portNumberTextField=new JTextField(5);
startServer=new JButton("Start");
details=new JTextArea();
mode=MODE.NONE;
startServer.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
if(mode==MODE.NONE)
{
int port=Integer.parseInt(portNumberTextField.getText());
if(port<=1024 || port>=65545) 
{
JOptionPane.showMessageDialog(ServerPsp.this," Port: "+port+" is invalid, must inside range [1025,65544].","Invalid Port",JOptionPane.INFORMATION_MESSAGE);
return;
}
startServer.setText("Stop");
SwingUtilities.invokeLater(new Runnable(){
public void run()
{
ftServer=new FTServer(port,ServerPsp.this.details);
}
});
mode=MODE.START;
return;
}
if(mode==MODE.START)
{
SwingUtilities.invokeLater(new Runnable(){
public void run()
{
ftServer.close();
}
});
startServer.setText("Start");
mode=MODE.NONE;
}
}
});

container=getContentPane();
container.setLayout(new FlowLayout());
container.add(portNumberLabel);
container.add(portNumberTextField);
container.add(startServer);
container.add(details);

}

public static void main(String gg[])
{
ServerPsp serverPsp=new ServerPsp();
}
}
