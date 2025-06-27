import java.util.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

class FileUploadEvent
{
private String uploaderId;
private File file;
private long numberOfBytesUploaded;
public void setUploaderId(String uploaderId)
{
this.uploaderId=uploaderId;
}
public String getUploaderId()
{
return this.uploaderId;
}
public void setFile(File file)
{
this.file=file;
}
public File getFile()
{
return this.file;
}
public void setNumberOfBytesUploaded(long numberOfBytesUploaded)
{
this.numberOfBytesUploaded=numberOfBytesUploaded;
}
public long getNumberOfBytesUploaded()
{
return this.numberOfBytesUploaded;
}
}
interface FileUploadListener
{
public void fileUploadStatusChanged(FileUploadEvent fileUploadEvent);
}

class FileModel extends AbstractTableModel
{
private ArrayList<File> files;
FileModel()
{
files=new ArrayList<>();
}
public ArrayList<File> getFiles()
{
return this.files;
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
public ArrayList<File> getFiles()
{
return this.model.getFiles();
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
class FileUploadViewPanel extends JPanel implements ActionListener,FileUploadListener
{
private JButton filesUploadButton;
private ArrayList<File> files;
private JPanel progressIndicatorsPanel;
private JScrollPane jsp;
private ArrayList<ProgressIndicator> progressIndicators;
private ArrayList<FileUploadThread> fileUploadThreads;

FileUploadViewPanel()
{
filesUploadButton=new JButton("Upload File(s)");
setLayout(new BorderLayout());
filesUploadButton.addActionListener(FileUploadViewPanel.this);
add(filesUploadButton,BorderLayout.NORTH);
}
public void actionPerformed(ActionEvent ae)
{
files=fileSelectionPanel.getFiles();
if(files.size()==0)
{
JOptionPane.showMessageDialog(FTClientFrame.this,"No file selected");
return;
}
String uploaderId;
ProgressIndicator progressIndicator;
FileUploadThread fileUploadThread;
progressIndicatorsPanel=new JPanel(new GridLayout(files.size(),1));
progressIndicators=new ArrayList<>();
fileUploadThreads=new ArrayList<>();

for(File file:files)
{
uploaderId=UUID.randomUUID().toString();
progressIndicator=new ProgressIndicator(file,uploaderId);
progressIndicators.add(progressIndicator);
progressIndicatorsPanel.add(progressIndicator);
fileUploadThread=new FileUploadThread(FileUploadViewPanel.this,file,uploaderId,port,host);
fileUploadThreads.add(fileUploadThread);
}
jsp=new JScrollPane(progressIndicatorsPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//add(filesUploadButton,BorderLayout.NORTH);
add(jsp,BorderLayout.CENTER);
revalidate();
repaint();
for(FileUploadThread fut:fileUploadThreads)
{
fut.start();
}

}
public void fileUploadStatusChanged(FileUploadEvent fileUploadEvent)
{
long numberOfBytesUploaded=fileUploadEvent.getNumberOfBytesUploaded();
File file=fileUploadEvent.getFile();
String uploaderId=fileUploadEvent.getUploaderId();
for(ProgressIndicator progressIndicator:this.progressIndicators)
{
if(progressIndicator.getUploaderId().equals(uploaderId)==true)
{
progressIndicator.updateProgressBar(numberOfBytesUploaded);
}
}
}
class ProgressIndicator extends JPanel
{
private File file;
private JLabel fileNameLabel;
private JProgressBar progressBar;
private String uploaderId;
private long fileLength;
ProgressIndicator(File file,String uploaderId)
{
this.uploaderId=uploaderId;
this.file=file;
this.fileLength=file.length();
fileNameLabel=new JLabel("Uploading: "+file.getAbsolutePath());
progressBar=new JProgressBar(1,100);
setLayout(new GridLayout(2,1));
add(fileNameLabel);
add(progressBar);
}
public String getUploaderId()
{
return this.uploaderId;
}
public void updateProgressBar(long bytesUploaded)
{
int percentage=0;
if(bytesUploaded==fileLength) percentage=100;
else percentage=(int)((bytesUploaded*100)/fileLength);
progressBar.setValue(percentage);
if(percentage==100) 
{
fileNameLabel.setText("Uploaded: "+file.getAbsolutePath());
fileNameLabel.setForeground(Color.GREEN);
}
}
}
}
//Inner-Classes ends
public static void main(String gg[])
{
FTClientFrame fcf=new FTClientFrame("localhost",5050);
}
}

class FileUploadThread extends Thread
{
FileUploadListener fileUploadListener;
private File file;
private String uploaderId;
private int port; 
private String host;
FileUploadThread(FileUploadListener fileUploadListener,File file,String uploaderId,int port,String host)
{
this.fileUploadListener=fileUploadListener;
this.port=port;
this.host=host;
this.file=file;
this.uploaderId=uploaderId;
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
Socket socket=new Socket(host,port);
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
long brc=x;
SwingUtilities.invokeLater(()->{
FileUploadEvent fileUploadEvent=new FileUploadEvent();
fileUploadEvent.setFile(file);
fileUploadEvent.setNumberOfBytesUploaded(brc);
fileUploadEvent.setUploaderId(uploaderId);
fileUploadListener.fileUploadStatusChanged(fileUploadEvent);
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
System.out.println("File: "+name+" uploaded at : "+file.getAbsolutePath());
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
