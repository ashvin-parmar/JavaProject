package com.ashvin.chess.client;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

import java.util.*;

import com.ashvin.chess.common.*;
import com.ashvin.nframework.client.*;

public class ChessUI extends JFrame
{
private JTable availableMembersList;
private AvailableMembersListModel availableMembersListModel;
private JScrollPane availableMembersListScrollPane;
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
this.client=new NFrameworkClient("localhost",5050);

container=getContentPane();
container.setLayout(new BorderLayout());
availableMembersListModel=new AvailableMembersListModel();
availableMembersList=new JTable(availableMembersListModel);
availableMembersListScrollPane=new JScrollPane(availableMembersList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


JPanel p1=new JPanel(new BorderLayout());
//p1.add(new JLabel("Members"),BorderLayout.NORTH);
p1.add(availableMembersListScrollPane,BorderLayout.NORTH);
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
availableMembersListModel.setMembers(members);
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

//inner classes
class AvailableMembersListModel extends AbstractTableModel
{
private String[] title={"Members"," "};
private java.util.List<String> members;
private java.util.List<JButton> inviteButtons;
public AvailableMembersListModel()
{
members=new LinkedList<>();
inviteButtons=new LinkedList<>();
}
public int getColumnCount()
{
return this.title.length;
}
public int getRowCount()
{
return this.members.size();
}
public String getColumnName(int column)
{
return this.title[column];
}
public Object getValueAt(int row,int column)
{
if(column==0) return members.get(row);
return this.inviteButtons.get(row);
}
public void setValueAt(Object value,int row,int column)
{
String text=(String)value;
/*
if(text.equalsIgnoreCase("Invited"))
{
for(int i=0;i<inviteButtons.size();i++)
{
inviteButtons.get(i).setEnabled(false);
fireTableDataChanged();
}
}
if(text.equalsIgnoreCase("Invite"))
{

}*/

}
public void setMembers(java.util.List<String> members)
{
this.members=members;
fireTableDataChanged();
this.inviteButtons.clear();
for(int i=0;i<members.size();i++)
{
this.inviteButtons.add(new JButton("Invite"));
fireTableDataChanged();
}
}
}
class InvitationMemberButtonCellRenderer implements TableCellRenderer
{
public Component getTableCellRendererComponent(JTable table,Object value,boolean a,boolean b,int row,int column)
{
return (JButton)value;
}
}
//class InvitationMemberButtonCellEditor extends DefaultCellEditor
//{
//
//}
}
