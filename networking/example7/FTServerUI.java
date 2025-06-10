import java.util.*;	//UUID
import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class RequestProcessor extends Thread
{
private Socket socket;
private String clientID;
private FTServerFrame fsf;
RequestProcessor(Socket socket,FTServerFrame fsf,String clientID)
{
this.socket=socket;
this.clientID=clientID;
this.fsf=fsf;
start();		//This thread has to be start [YES]
}
public void run()
{
try
{
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
int bytesToReceive=1024;
byte tmp[]=new byte[1024];
byte header[]=new byte[1024];
int bytesReadCount=0;
int i,j,k;
i=0;
j=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j+=bytesReadCount;
}
long lengthOfFile=0;
i=0;
j=1;
while(header[i]!=',')
{
lengthOfFile+=(header[i]*j);
j*=10;
i++;
}
i++;
StringBuffer sb=new StringBuffer();
while(i<1024)
{
sb.append((char)header[i]);
i++;
}
byte ack[]=new byte[1];
os.write(ack,0,1);
os.flush();

String fileName=sb.toString().trim();
File file=new File(fileName);
if(file.exists()) file.delete();
SwingUtilities.invokeLater(()->{
fsf.updateLog("File received: "+fileName);
});
FileOutputStream fos=new FileOutputStream(file);
j=0;
int chunkSize=4096;
byte bytes[]=new byte[chunkSize];
while(j<lengthOfFile)
{
bytesReadCount=is.read(bytes);
if(bytesReadCount==-1) continue;
fos.write(bytes,0,bytesReadCount);
fos.flush();
j+=bytesReadCount;
}
fos.close();

os.write(ack,0,1);
os.flush();

System.out.println("File created: "+file.getAbsolutePath()+" of length: "+lengthOfFile);
long lof=lengthOfFile;
SwingUtilities.invokeLater(new Thread(){
public void run()
{
fsf.updateLog("File created: "+file.getAbsolutePath()+" of length: "+lof);
fsf.updateLog("Client ID: "+clientID+" Connection were closed.");
}
});
socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
class FTServer extends Thread
{
private ServerSocket serverSocket;
private FTServerFrame fsf;
private int port;
FTServer(int port,FTServerFrame fsf)
{
this.fsf=fsf;
this.port=port;
}
public void run()
{
try
{
serverSocket=new ServerSocket(port);
startListening();
}catch(Exception e)
{
System.out.println(e);
}
}
private void startListening()
{
try{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Server is ready to accept request on port: "+port);
SwingUtilities.invokeLater(()->{
fsf.updateLog("Server started and is listening on port "+FTServer.this.port);
});
socket=serverSocket.accept();
requestProcessor=new RequestProcessor(socket,fsf,UUID.randomUUID().toString());
}
}catch(Exception exception)
{
System.out.println(exception);
}
}
}

class FTServerFrame extends JFrame implements ActionListener
{
private FTServer server;
private JButton button;
private Container container;
private JScrollPane jsp;
private JTextArea ta;
private JTextField tf;
private JLabel l;
private boolean serverState=false;
FTServerFrame()
{
container=getContentPane();
container.setLayout(new BorderLayout());
button=new JButton("Start");
tf=new JTextField(5);
l=new JLabel("Port Number: ");
ta=new JTextArea();
jsp=new JScrollPane(ta,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
Panel p1=new Panel(new GridLayout(1,2));
p1.add(l);
p1.add(tf);
button.addActionListener(this);
container.add(p1,BorderLayout.NORTH);
container.add(button,BorderLayout.SOUTH);
container.add(jsp,BorderLayout.CENTER);
setSize(400,400);
setLocation(200,100);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public void actionPerformed(ActionEvent ev)
{
if(serverState==false)
{
int port=Integer.parseInt(tf.getText().trim());		//No Validation for now,
if(port<=1024 || port>=65545) 
{
JOptionPane.showMessageDialog(this,"Invalid port number: "+port+", Range: [1025,65544]","Invalid Port",JOptionPane.INFORMATION_MESSAGE);
return;
}
server=new FTServer(port,this);
server.start();
button.setText("Stop");
serverState=true;
}
else
{
server.stop();
button.setText("Start");
serverState=false;
}
}
public void updateLog(String message)
{
this.ta.append(message+"\n");
}
public static void main(String gg[])
{
FTServerFrame fsf=new FTServerFrame();
}
}
