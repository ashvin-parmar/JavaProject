package com.ashvin.chess.client;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;

import com.ashvin.chess.common.*;
import com.ashvin.nframework.client.*;

public class ChessUI extends JFrame
{
private JList availableMembersList;
private String username;
private NFrameworkClient client;
private javax.swing.Timer timer;
private Container container;
public ChessUI(String username)
{
super(username);
this.username=username;
initComponents();
setAppearance();
addEventListeners();
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int w=500;
int h=400;
setSize(w,h);
setLocation(d.width/2-w/2,d.height/2-h/2);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void initComponents()
{
container=getContentPane();
container.setLayout(new BorderLayout());

this.availableMembersList=new JList();
this.client=new NFrameworkClient("localhost",5050);

JPanel p1=new JPanel(new BorderLayout());
p1.add(new JLabel("Members"),BorderLayout.NORTH);
p1.add(availableMembersList);
container.add(p1,BorderLayout.EAST);
}
private void setAppearance()
{

}
private void addEventListeners()
{
timer=new javax.swing.Timer(4000,new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
//setting list of available members to JList

try
{
java.util.List<String> members=(java.util.List<String>)client.execute("/ChessServer/getMembers",username);
Vector v=new Vector();
for(String member:members)
{
v.add(member);
}
availableMembersList.setListData(v);
}catch(Throwable t)
{
System.out.println(t.toString());
}

}
});

//after performing all operations, start the timer
timer.start();
}
public void showUI()
{
setVisible(true);
}
}
