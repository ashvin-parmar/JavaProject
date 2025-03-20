package com.ashvin.hr.nexus.pl.ui;
import com.ashvin.hr.nexus.pl.model.*;
import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class DesignationUI extends JFrame implements DocumentListener
{
private JLabel titleLabel;
private JLabel searchErrorLabel;
private JLabel searchLabel;
private JTextField searchTextField;
private JButton clearSearchButton;


private JTable designationTable;
private JScrollPane jsp;
private DesignationModel designationModel;
private DesignationPanel designationPanel;
private Container container;
public DesignationUI()
{
initComponents();
setAppearance();
addListeners();
}
private void initComponents()
{
titleLabel=new JLabel("Designations");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
searchErrorLabel=new JLabel("");
clearSearchButton=new JButton("Clear");

designationModel=new DesignationModel();
designationTable=new JTable(designationModel);
jsp=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

container=getContentPane();

}
private void setAppearance()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
Font columnHeaderFont=new Font("Times New Roman",Font.BOLD,16);

titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
designationTable.setFont(dataFont);
designationTable.setRowHeight(30);

designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
designationTable.setRowSelectionAllowed(true);

JTableHeader header=designationTable.getTableHeader();
header.setResizingAllowed(false);
header.setReorderingAllowed(false);
TableColumnModel columnModel=header.getColumnModel();
columnModel.getColumn(0).setPreferredWidth(40);
columnModel.getColumn(1).setPreferredWidth(400);

header.setFont(columnHeaderFont);

designationPanel=new DesignationPanel();

container.setLayout(null);
int lm=0;		//Left Margin
int tm=0;		//Top Margin

titleLabel.setBounds(lm+10,tm+10,150,30);

searchErrorLabel.setBounds(lm+380,tm+10+20,80,20);

searchLabel.setBounds(lm+10,tm+10+30+10,80,30);
searchTextField.setBounds(lm+10+80+10,tm+10+30+10,350,30);
clearSearchButton.setBounds(lm+10+80+10+350+10,tm+10+30+10,30,30);

jsp.setBounds(lm+10,tm+10+30+10+30+10,480,250);
designationPanel.setBounds(lm+10,tm+10+30+10+30+10+250+10,480,250);

container.add(titleLabel);
container.add(searchErrorLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchButton);
container.add(jsp);
container.add(designationPanel);

int w=500;
int h=650;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);

clearSearchButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
}
private void searchDesignation()
{
searchErrorLabel.setText("");
String title=searchTextField.getText().trim();
if(title.length()==0) return;
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not found");
return;
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle visibleRectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(visibleRectangle);
}
public void changedUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void removeUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void insertUpdate(DocumentEvent ev)
{
searchDesignation();
}
class DesignationPanel extends JPanel
{
public DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(160,160,160)));
}
}
}
