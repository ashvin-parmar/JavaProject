package com.ashvin.chess.client;

import com.ashvin.nframework.client.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;

public class ChessUI extends JFrame
{
private String username;
private JTable availableMembersList;
private JScrollPane availableMembersListScrollPane;
private AvailableMembersListModel availableMembersListModel;
private javax.swing.Timer timer;
private Container container;
private NFrameworkClient client;
public ChessUI(String username)
{
super(username);
this.username=username;
this.client=new NFrameworkClient("localhost",5050);
initComponents();
setAppearance();
initEventListeners();
int w=1000;
int h=800;
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
this.setSize(w,h);
this.setLocation(d.width/2-w/2,d.height/2-h/2);
}
private void initComponents()
{
JPanel p1=new JPanel(new BorderLayout());
//p1.add(new JLabel("Members"),BorderLayout.NORTH);

this.availableMembersListModel=new AvailableMembersListModel();
this.availableMembersList=new JTable(availableMembersListModel);

JTableHeader header=availableMembersList.getTableHeader();
header.setResizingAllowed(false);
header.setReorderingAllowed(false);
TableColumnModel columnModel=header.getColumnModel();
columnModel.getColumn(0).setPreferredWidth(100);
columnModel.getColumn(1).setPreferredWidth(20);

this.availableMembersList.getColumn(" ").setCellRenderer(new AvailableMembersListButtonCellRenderer());
this.availableMembersList.getColumn(" ").setCellEditor(new AvailableMembersListButtonCellEditor());
this.availableMembersListScrollPane=new JScrollPane(this.availableMembersList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
p1.add(availableMembersListScrollPane);


container=getContentPane();
container.setLayout(new BorderLayout());
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
timer.stop();
try
{
Object[] args={username};
java.util.List<String> members=(java.util.List<String>)client.execute("/ChessServer/getMembers",args);
availableMembersListModel.setMembers(members);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
//Change message later on
}
timer.start();
}
});
addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent we)
{
timer.stop();
try
{
Object[] args={username};
ChessUI.this.client.execute("/ChessServer/logout",args);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
//Change message later on
}
timer.start();
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
//methods for send request internally --Start Here
private void sendInvitation(String toUsername)
{
//System.out.println("Sending invitation to: "+toUsername);
try
{
client.execute("/ChessServer/inviteMember",username,toUsername);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(this,t.toString());
}
}

//methods for send request internally --Ends Here

//Inner classes -- Starts here
class AvailableMembersListModel extends AbstractTableModel
{
private java.util.List<String> members;
private String titles[]={"Members"," "};
private java.util.List<JButton> inviteButtons;
private boolean awaitingInvitationReply=false;
AvailableMembersListModel()
{
inviteButtons=new LinkedList<>();
members=new LinkedList<>();
}
public int getColumnCount()
{
return this.titles.length;
}
public int getRowCount()
{
return this.members.size();
}
public Class getColumnClass(int column)
{
if(column==0) return String.class;
return JButton.class;
}
public String getColumnName(int column)
{
return this.titles[column];
}
public Object getValueAt(int row,int column)
{
if(column==0) return this.members.get(row);
return inviteButtons.get(row);
}
public void setValueAt(Object value,int row,int column)
{
if(column==1)
{
String text=value.toString();
try
{
if(text.equalsIgnoreCase("Invited"))
{
awaitingInvitationReply=true;
for(JButton inviteButton:inviteButtons)
{
inviteButton.setEnabled(false);
fireTableDataChanged();
ChessUI.this.sendInvitation(this.members.get(row));
}
}
if(text.equalsIgnoreCase("Invite"))
{
awaitingInvitationReply=false;
for(JButton inviteButton:inviteButtons) inviteButton.setEnabled(true);
fireTableDataChanged();
}
JButton button=this.inviteButtons.get(row);
button.setText((String)value);
}catch(Exception e)
{

}
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
class AvailableMembersListButtonCellRenderer implements TableCellRenderer
{
public Component getTableCellRendererComponent(JTable table,Object value,boolean a,boolean b,int row,int column)
{
//System.out.println("Row: "+row+", Column: "+column);
return (JButton)(value);
}
}
class AvailableMembersListButtonCellEditor extends DefaultCellEditor
{
private JButton button;
private ActionListener actionListener;
private int row,column;
private boolean isClicked=false;
public AvailableMembersListButtonCellEditor()
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
button.setForeground(Color.black);
button.setBackground(UIManager.getColor("Button.background"));
button.setOpaque(true);
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
super.fireEditingStopped();
}
}
//Inner classes --Ends Here
}
