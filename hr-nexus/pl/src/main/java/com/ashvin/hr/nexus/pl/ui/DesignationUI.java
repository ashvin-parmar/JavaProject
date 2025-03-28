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
import java.util.*;

public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
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
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
public DesignationUI()
{
initComponents();
setAppearance();
addListeners();
setViewMode();
designationPanel.setViewMode();
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
designationPanel.setBounds(lm+10,tm+10+30+10+30+10+250+10,480,160);

container.add(titleLabel);
container.add(searchErrorLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchButton);
container.add(jsp);
container.add(designationPanel);

int w=500;
int h=560;
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
designationTable.getSelectionModel().addListSelectionListener(this);
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
public void valueChanged(ListSelectionEvent ev)
{
int selectedRowIndex=designationTable.getSelectedRow();
try
{
DesignationInterface designation=designationModel.getDesignationAt(selectedRowIndex);
designationPanel.setDesignation(designation);
}catch(BLException blException)
{
designationPanel.clearDesignation();
}
}
private void setViewMode()
{
this.mode=MODE.VIEW;
if(designationModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchButton.setEnabled(false);
designationTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchButton.setEnabled(true);
designationTable.setEnabled(true);
}
}
private void setAddMode()
{
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
clearSearchButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setEditMode()
{
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setDeleteMode()
{
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchButton.setEnabled(false);
designationTable.setEnabled(false);
}
//inner_class
class DesignationPanel extends JPanel
{
private JLabel titleDesignationLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton deleteButton;
private JButton cancelButton;
private JButton exportToPDFButton;
private JButton saveButton;
private JPanel buttonsPanel;
private DesignationInterface designation;
public DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(160,160,160)));
initComponents();
setAppearance();
addListeners();
}
private void initComponents()
{
titleDesignationLabel=new JLabel("Designation");
titleLabel=new JLabel("");
titleTextField=new JTextField();
clearTitleTextFieldButton=new JButton("X");
buttonsPanel=new JPanel();
addButton=new JButton("A");
editButton=new JButton("E");
deleteButton=new JButton("D");
cancelButton=new JButton("C");
exportToPDFButton=new JButton("D");
saveButton=new JButton("S");
designation=null;
}
private void setAppearance()
{
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
titleDesignationLabel.setFont(captionFont);
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm=0;
int tm=0;
titleDesignationLabel.setBounds(lm+10,tm+20,110,30);
titleLabel.setBounds(lm+110+10+10,tm+20,350,30);
titleTextField.setBounds(lm+10+110+10,tm+20,300,30);
clearTitleTextFieldButton.setBounds(lm+10+110+10+300+10,tm+20,30,30);
buttonsPanel.setBounds(10,tm+20+30+30-5,460,75);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(170,170,170)));
addButton.setBounds(70,12,50,50);
saveButton.setBounds(70,12,50,50);
editButton.setBounds(70+50+20,12,50,50);
cancelButton.setBounds(70+50+20+50+20,12,50,50);
deleteButton.setBounds(70+50+20+50+20+50+20,12,50,50);
exportToPDFButton.setBounds(70+50+20+50+20+50+20+50+20,12,50,50);
buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
buttonsPanel.add(saveButton);

add(titleDesignationLabel);
add(titleLabel);
titleTextField.setVisible(false);
add(titleTextField);
add(clearTitleTextFieldButton);
saveButton.setVisible(false);
add(buttonsPanel);

}
private void addListeners()
{
addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(DesignationUI.this.mode==MODE.VIEW)
{
setAddMode();
}
else
{
addDesignation();
setViewMode();
}
}
});
editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(DesignationUI.this.mode==MODE.VIEW)
{
setEditMode();
}
else
{
updateDesignation();
setViewMode();
}
}
});
cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});
deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(DesignationUI.this.mode==MODE.VIEW)
{
setDeleteMode();
}
else
{
deleteDesignation();
setViewMode();
}
}
});
exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});
clearTitleTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
titleTextField.setText("");
}
});
}
public void addDesignation()
{
String title=titleTextField.getText().trim();
DesignationInterface d=new Designation();
d.setTitle(title);
try
{
designationModel.add(d);
int rowIndex=designationModel.indexOfTitle(title,false);
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle visibleRectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(visibleRectangle);
}catch(BLException blException)
{
String exceptionMessage="";
if(blException.hasExceptions())
{
exceptionMessage+=blException.getGenericException()+"\n";
java.util.List<String> properties=blException.getProperties();
for(String property:properties)
{
String exception=blException.getPropertyException(property);
exceptionMessage+=(property+": "+exception+"\n");
//System.out.printf("[%s]:  %s\n",property,blException.getPropertyException(property));
}
}
else
{
exceptionMessage="Cannot add designation title\n";
}
JOptionPane.showMessageDialog(this,exceptionMessage);
}
}
public void updateDesignation()
{
String newTitle=titleTextField.getText().trim();
DesignationInterface d=new Designation();
d.setTitle(newTitle);
d.setCode(designation.getCode());
try
{
designationModel.update(d);
int rowIndex=designationModel.indexOfTitle(newTitle,false);
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle visibleRectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(visibleRectangle);
}catch(BLException blException)
{
String exceptionMessage="";
if(blException.hasExceptions())
{
exceptionMessage+=blException.getGenericException()+"\n";

java.util.List<String> properties=blException.getProperties();
for(String property:properties)
{
String exception=blException.getPropertyException(property);
exceptionMessage+=(property+": "+exception+"\n");
//System.out.printf("[%s]:  %s\n",property,blException.getPropertyException(property));
}

}
else
{
exceptionMessage="Cannot add designation title\n";
}
JOptionPane.showMessageDialog(this,exceptionMessage);
}
}
public void deleteDesignation()
{

}
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
titleLabel.setText(designation.getTitle());
}
public void clearDesignation()
{
titleLabel.setText("");
this.designation=null;
}
private void setViewMode()
{
DesignationUI.this.setViewMode();
this.titleTextField.setVisible(false);
this.titleLabel.setVisible(true);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
this.clearTitleTextFieldButton.setVisible(false);
this.addButton.setText("A");
this.editButton.setText("E");
if(designationModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
}
private void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleTextField.setText("");
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.clearTitleTextFieldButton.setVisible(true);
this.addButton.setText("S");
this.cancelButton.setEnabled(true);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
private void setEditMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit");
return;
}
DesignationUI.this.setEditMode();
if(designation!=null) this.titleTextField.setText(designation.getTitle());
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.titleTextField.requestFocus();
this.clearTitleTextFieldButton.setVisible(true);
this.editButton.setText("U");
this.cancelButton.setEnabled(true);
this.addButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
private void setDeleteMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit");
return;
}
DesignationUI.this.setDeleteMode();
this.deleteButton.setText("D");
this.cancelButton.setEnabled(true);
this.addButton.setEnabled(false);
this.editButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
private void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
this.exportToPDFButton.setText("<\\>");
this.cancelButton.setEnabled(true);
this.addButton.setEnabled(false);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
}
}
}
