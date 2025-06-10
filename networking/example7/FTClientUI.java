import java.util.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

class FileModel extends AbstractTableModel
{
private ArrayList<File> files;
FileModel()
{
files=new ArrayList<>();
}
public int getRowCount()
{
return files.size();
}
public int getColumnCount()
{
return 2;	//"S.No" and file
}
public Class getColumnClass(int c)
{
if(c==0) return Integer.class;
return String.class;
}
public String getColumnName(int c)
{
if(c==0) return "S.No";
return "File";
}
public Object getValueAt(int r,int c)
{
if(c==0) return (r+1);
return files.get(r).getAbsolutePath();
}
public boolean isCellEditable(int r,int c)
{
return false;
}
public void add(File file){
this.files.add(file);
fireTableDataChanged();
}
}
class FTClientFrame extends JFrame
{
private String host;
private int port;
private FileSelectionPanel fileSelectionPanel;
private FileUploadViewPanel fileUploadViewPanel; 
private Container container;
FTClientFrame(String host,int port)
{
this.host=host;
this.port=port;
container=getContentPane();
container.setLayout(new GridLayout(1,2));

fileSelectionPanel=new FileSelectionPanel();
fileUploadViewPanel=new FileUploadViewPanel();
container.add(fileSelectionPanel);
container.add(fileUploadViewPanel);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=1000;
int height=600;
int w=(d.width/2)-(width/2);
int h=(d.height/2)-(height/2);
setSize(width,height);
setLocation(w,h);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public void updateProgressBar(String clientId,long bytesReadCount)
{
//Yet to implement
}
public void updateUploadFile(String clientId)
{
//Yet to implement
}
//Inner-Classes starts
class FileSelectionPanel extends JPanel implements ActionListener
{
private JLabel titleLabel;
private JButton addFileButton;
private JTable table;
private JScrollPane jsp;
private FileModel model;
FileSelectionPanel()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font dataFont=new Font("Verdana",Font.PLAIN,14);
Font headerFont=new Font("Times New Roman",Font.BOLD,16);

setLayout(new BorderLayout());
titleLabel=new JLabel("Selected File(s)");
model=new FileModel();

table=new JTable(model);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.setRowSelectionAllowed(true);
table.setFont(dataFont);
table.setRowHeight(30);

JTableHeader header=table.getTableHeader();
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
header.setFont(headerFont);

TableColumnModel columnModel=header.getColumnModel();
columnModel.getColumn(0).setPreferredWidth(40);
columnModel.getColumn(1).setPreferredWidth(400);




jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
addFileButton=new JButton("+");
addFileButton.addActionListener(this);
add(titleLabel,BorderLayout.NORTH);
add(jsp,BorderLayout.CENTER);
add(addFileButton,BorderLayout.SOUTH);
}
public void actionPerformed(ActionEvent ae)
{
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
int selectedOption=jfc.showOpenDialog(this);
if(selectedOption==jfc.APPROVE_OPTION)
{
File selectedFile=jfc.getSelectedFile();
model.add(selectedFile);
}
}
}
class FileUploadViewPanel extends JPanel
{

}
//Inner-Classes ends
public static void main(String gg[])
{
FTClientFrame fcf=new FTClientFrame("localhost",5050);
}
}

class FileUploadThread extends Thread
{
private FTClientFrame fcf;
private File file;
private String clientId;
private int port; 
private String host;
FileUploadThread(int port,String host,FTClientFrame fcf,File file,String clientId)
{
this.port=port;
this.host=host;
this.fcf=fcf;
this.file=file;
this.clientId=clientId;
}
public void run()
{
try
{
long fileLength=file.length();
String name=file.getName();
int i,j;
long x;
byte[] header=new byte[1024];

x=fileLength;
i=0;

while(x>0)
{
header[i]=(byte)(x%10);
x/=10;
i++;
}
header[i]=(byte)',';
i++;
for(j=0;j<name.length();j++)
{
header[i]=(byte)(name.charAt(j));
i++;
}
while(i<1024)
{
header[i]=(byte)32;
i++;
}
//Header written complete
Socket socket=new Socket("localhost",5050);
OutputStream os=socket.getOutputStream();
os.write(header,0,1024);
os.flush();


InputStream is=socket.getInputStream();
int bytesReadCount;
//Ack receive
byte ack[]=new byte[1];
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}

FileInputStream fis=new FileInputStream(file);
int chunkSize=4096;
byte bytes[]=new byte[chunkSize];
i=0;
x=0;
while(x<fileLength)
{
bytesReadCount=fis.read(bytes);
if(bytesReadCount==-1) continue;
os.write(bytes,0,bytesReadCount);
os.flush();
x+=bytesReadCount;
long brc=bytesReadCount;
SwingUtilities.invokeLater(()->{
fcf.updateProgressBar(clientId,brc);
});
}
fis.close();

while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
socket.close();
SwingUtilities.invokeLater(()->{
fcf.updateUploadFile(clientId);
});
System.out.println("File: "+name+" uploaded at : "+file.getAbsolutePath());
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
