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
private JTable invitationByMembersList;
private InvitationByMembersListModel invitationByMembersListModel;
private JScrollPane invitationByMembersListScrollPane;
private String username;
private String invitationToUsername="";
private NFrameworkClient client;
private javax.swing.Timer timerForSelf;
private javax.swing.Timer timerForOther;
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
availableMembersListModel=new AvailableMembersListModel();
availableMembersList=new JTable(availableMembersListModel);
availableMembersListScrollPane=new JScrollPane(availableMembersList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
availableMembersList.getColumn("Invite").setCellRenderer(new AvailableMembersListCellRenderer());
availableMembersList.getColumn("Invite").setCellEditor(new AvailableMembersListCellEditor());

invitationByMembersListModel=new InvitationByMembersListModel();
invitationByMembersList=new JTable(invitationByMembersListModel);
invitationByMembersListScrollPane=new JScrollPane(invitationByMembersList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

invitationByMembersList.getColumn("Accept").setCellRenderer(new InvitationByMembersListCellRenderer());
invitationByMembersList.getColumn("Reject").setCellRenderer(new InvitationByMembersListCellRenderer());
//invitationByMembersList.getColumn("Accept").setCellEditor(new InvitationByMembersListCellEditor());
//invitationByMembersList.getColumn("Reject").setCellEditor(new InvitationByMembersListCellEditor());

JPanel p1=new JPanel(new BorderLayout());
p1.add(new JLabel("Members"),BorderLayout.NORTH);
p1.add(availableMembersListScrollPane,BorderLayout.CENTER);

JPanel p2=new JPanel(new BorderLayout());
p2.add(new JLabel("Invitation"),BorderLayout.NORTH);
p2.add(invitationByMembersListScrollPane,BorderLayout.CENTER);


container=getContentPane();
container.setLayout(new GridLayout(1,2));
container.add(p2);
container.add(p1);
}
private void setAppearance()
{

}
private void addEventListeners()
{
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

timerForSelf=new javax.swing.Timer(4000,new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
//setting list of available members to JList
timerForSelf.stop();
if(availableMembersListModel.awaitingInvitationReply==false)
{
try
{
java.util.List<String> members=(java.util.List<String>)client.execute("/ChessServer/getMembers",username);
availableMembersListModel.setMembers(members);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
}
}
else
{
try
{
MESSAGE_TYPE messageType=MESSAGE_TYPE.NOT_AVAILABLE;
Object obj=client.execute("/ChessServer/getInvitationStatus",username,invitationToUsername);
if(obj instanceof MESSAGE_TYPE) messageType=(MESSAGE_TYPE)obj;
else messageType=MESSAGE_TYPE.valueOf(obj.toString());
if(messageType==MESSAGE_TYPE.CHALLENGE)
{
}
else
{
if(messageType==MESSAGE_TYPE.CHALLENGE_ACCEPTED)
{
System.out.println("Challenge accepted\n");
timerForSelf.stop();
}
else if(messageType==MESSAGE_TYPE.CHALLENGE_REJECTED)
{
System.out.println("Challenge accepted\n");
invitationToUsername="";
}
else if(messageType==MESSAGE_TYPE.NOT_AVAILABLE)
{
System.out.println("Challenge rejected\n");
invitationToUsername="";
}
availableMembersListModel.awaitingInvitationReply=false;
}
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
System.out.println(t.toString());
}
}
timerForSelf.start();
}
});
timerForOther=new javax.swing.Timer(4000,new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
timerForOther.stop();
try
{
java.util.List<String> fromUsernames=(java.util.List<String>)client.execute("/ChessServer/getMessagesToUsernames",username);
invitationByMembersListModel.setMembers(fromUsernames);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
//System.out.println(t.toString());
}
timerForOther.start();
}
});

//after performing all operations, start the timer
timerForSelf.start();
timerForOther.start();
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
Message message=new Message();
message.setToUsername(toUsername);
message.setFromUsername(username);
message.setMessageType(MESSAGE_TYPE.CHALLENGE);
client.execute("/ChessServer/inviteMember",message);
this.invitationToUsername=toUsername;
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
}
}


//inner classes
class AvailableMembersListModel extends AbstractTableModel
{
private String[] title={"Members","Invite"};
private java.util.List<String> members;
private java.util.List<JButton> inviteButtons;
public boolean awaitingInvitationReply=false;
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
if(text.equalsIgnoreCase("Invited"))
{
awaitingInvitationReply=true;
JButton button=inviteButtons.get(row);
button.setText(text);
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
/*
for(int i=0;i<inviteButtons.size();i++)		//what if they are cleared or assigned as null -> because setMembers called from thread
{
inviteButtons.get(i).setEnabled(true);
}
fireTableDataChanged();
*/
}
}catch(Exception exception)
{
JOptionPane.showMessageDialog(ChessUI.this,exception.getMessage());
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

class InvitationByMembersListModel extends AbstractTableModel
{
private String titles[]={"Members","Accept","Reject"};
private java.util.List<String> members;
private java.util.List<java.util.List<JButton>> invitationButtons;
public InvitationByMembersListModel()
{
members=new LinkedList<>();
invitationButtons=new LinkedList<>();
invitationButtons.add(new LinkedList<>());
invitationButtons.add(new LinkedList<>());
}
public int getColumnCount()
{
return titles.length;
}
public int getRowCount()
{
return invitationButtons.get(0).size();
}
public Class getColumnClass(int column)
{
if(column==0) return String.class;
return JButton.class;
}
public String getColumnName(int column)
{
return titles[column];
}
public boolean isCellEditatble(int row,int column)
{
if(column==0) return false;
return true;
}
public Object getValueAt(int row,int column)
{
if(column==0)
{
return members.get(row);
}
else
{
return invitationButtons.get(column-1).get(row);
}
}
public void setMembers(java.util.List<String> fromUsernames)
{
this.members=fromUsernames;
java.util.List<JButton> buttons1=this.invitationButtons.get(0);
java.util.List<JButton> buttons2=this.invitationButtons.get(1);
buttons1.clear();
buttons2.clear();
for(int i=0;i<fromUsernames.size();i++)
{
buttons1.add(new JButton("Accept"));
buttons2.add(new JButton("Reject"));
}
fireTableDataChanged();
}
}
private class InvitationByMembersListCellRenderer implements TableCellRenderer
{
public Component getTableCellRendererComponent(JTable table,Object value,boolean a,boolean b,int row,int column)
{
return (JButton)value;
}
}
}
