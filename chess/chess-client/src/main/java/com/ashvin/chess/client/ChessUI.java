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
//setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void initComponents()
{
this.client=new NFrameworkClient("localhost",5050);

container=getContentPane();
container.setLayout(new BorderLayout());
availableMembersListModel=new AvailableMembersListModel();
availableMembersList=new JTable(availableMembersListModel);
availableMembersListScrollPane=new JScrollPane(availableMembersList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
availableMembersList.getColumn(" ").setCellRenderer(new AvailableMembersListCellRenderer());

availableMembersList.getColumn(" ").setCellEditor(new AvailableMembersListCellEditor());

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
timer.stop();
try
{
java.util.List<String> members=(java.util.List<String>)client.execute("/ChessServer/getMembers",username);
availableMembersListModel.setMembers(members);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
}
timer.start();
}
});
addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent we)
{
try
{
client.execute("/ChessServer/logout",username);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
}
System.exit(0);
}
});
//after performing all operations, start the timer
timer.start();
}
public void showUI()
{
setVisible(true);
}
//Important methods for features of application
public void sendInvitation(String toUsername)
{
try
{
client.execute("/ChessServer/inviteMember",username,toUsername);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
}
}



//inner classes
class AvailableMembersListModel extends AbstractTableModel
{
private String[] title={"Members"," "};
private java.util.List<String> members;
private java.util.List<JButton> inviteButtons;
private boolean awaitingInvitationReply=false;
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
public Class getColumnClass(int column)
{
if(column==0) return String.class;
return JButton.class;
}
public Object getValueAt(int row,int column)
{
if(column==0) return members.get(row);
return this.inviteButtons.get(row);
}
public void setValueAt(Object value,int row,int column)
{
try
{
String text=(String)value;
JButton button=inviteButtons.get(row);
button.setText(text);
fireTableDataChanged();
if(text.equalsIgnoreCase("Invited"))
{
awaitingInvitationReply=true;
for(int i=0;i<inviteButtons.size();i++)
{
inviteButtons.get(i).setEnabled(false);
}
fireTableDataChanged();
ChessUI.this.sendInvitation(members.get(row));
}
if(text.equalsIgnoreCase("Invite"))
{
awaitingInvitationReply=false;
for(int i=0;i<inviteButtons.size();i++)
{
inviteButtons.get(i).setEnabled(true);
}
fireTableDataChanged();
}
}catch(Exception e)
{

}
}
public void setMembers(java.util.List<String> members)
{
if(awaitingInvitationReply) return ;
this.members=members;
this.inviteButtons.clear();
for(int i=0;i<members.size();i++)
{
this.inviteButtons.add(new JButton("Invite"));
}
fireTableDataChanged();
}
public boolean isCellEditable(int row,int column)
{
if(column==0) return false;
return true;
}
}
class AvailableMembersListCellRenderer implements TableCellRenderer
{
public Component getTableCellRendererComponent(JTable table,Object value,boolean a,boolean b,int row,int column)
{
return (JButton)value;
}
}
class AvailableMembersListCellEditor extends DefaultCellEditor
{
private JButton button;
private boolean isClicked;
private int row,column;
private ActionListener actionListener;
public AvailableMembersListCellEditor()
{
super(new JCheckBox());
actionListener=new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
fireEditingStopped();
}
};
}
public Component getTableCellEditorComponent(JTable table,Object value,boolean a,int row,int column)
{
this.row=row;
this.column=column;
this.button=(JButton)availableMembersListModel.getValueAt(row,column);
this.button.removeActionListener(actionListener);
this.button.addActionListener(actionListener);
this.button.setForeground(Color.black);
this.button.setBackground(UIManager.getColor("Button.background"));
this.button.setOpaque(true);
isClicked=true;
return button;
}
public Object getCellEditorValue()
{
return "Invited";
}
public boolean stopCellEditing()
{
isClicked=false;
return super.stopCellEditing();
}
public void fireEditingStopped()
{
//do whatever required before editing stopped
super.fireEditingStopped();
}
}
}
