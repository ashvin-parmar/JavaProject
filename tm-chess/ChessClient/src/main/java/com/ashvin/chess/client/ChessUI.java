package com.ashvin.chess.client;

import com.ashvin.nframework.client.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChessUI extends JFrame
{
private String username;
private javax.swing.Timer timer;
private JList availableMembersList;
private Container container;
private NFrameworkClient client;
public ChessUI(String username)
{
super(username);
this.username=username;
client=new NFrameworkClient();
initComponents();
setAppearance();
initEventListeners();
int w=500;
int h=400;
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
this.setSize(w,h);
this.setLocation(d.width/2-w/2,d.height/2-h/2);
}
private void initComponents()
{
JPanel p1=new JPanel(new BorderLayout());
p1.add(new JLabel("Members"),BorderLayout.NORTH);
availableMembersList=new JList();
p1.add(availableMembersList);
container=getContentPane();
setLayout(new BorderLayout());
container.add(p1,BorderLayout.EAST);
}
private void setAppearance()
{
//do nothing
}
private void initEventListeners()
{
this.timer=new javax.swing.Timer(1000,new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
try
{
Object[] args={username};
java.util.List<String> members=(java.util.List<String>)client.execute("/ChessServer/getMembers",args);
Vector v=new Vector();
for(String member:members)
{
v.add(member);
}
availableMembersList.setListData(v);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
//Change message later on
}
}
});
addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent we)
{
try
{
Object[] args={username};
ChessUI.this.client.execute("/ChessServer/logout",args);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
//Change message later on
}
System.exit(0);
}
});

//After all set-up, Let us start the timer
timer.start();
}
public void showUI()
{
this.setVisible(true);
}
}
