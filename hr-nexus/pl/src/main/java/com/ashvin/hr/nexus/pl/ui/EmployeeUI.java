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
import java.io.*;

public class EmployeeUI extends JFrame implements DocumentListener, ListSelectionListener
{
private JLabel titleLabel;
private JLabel searchErrorLabel;
private JLabel searchLabel;
private JTextField searchTextField;
private JButton clearSearchButton;
private JTable employeeTable;
private JScrollPane jsp;
private EmployeeModel employeeModel;
private EmployeePanel employeePanel;
private ImageIcon clearIcon;
private ImageIcon logoIcon;
private Container container;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
public EmployeeUI()
{
initComponents();
setAppearance();
addListeners();
setViewMode();
employeePanel.setViewMode();
}
private void initComponents()
{
titleLabel=new JLabel("Employee");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
searchErrorLabel=new JLabel("");
logoIcon=new ImageIcon(getClass().getResource("/icons/hr_nexus_logo.png"));
setIconImage(logoIcon.getImage());
clearIcon=new ImageIcon(getClass().getResource("/icons/clear.png"));
clearSearchButton=new JButton(clearIcon);

employeeModel=new EmployeeModel();
employeeTable=new JTable(employeeModel);
jsp=new JScrollPane(employeeTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
employeeTable.setFont(dataFont);
employeeTable.setRowHeight(30);

employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
employeeTable.setRowSelectionAllowed(true);

JTableHeader header=employeeTable.getTableHeader();
header.setResizingAllowed(false);
header.setReorderingAllowed(false);
TableColumnModel columnModel=header.getColumnModel();
columnModel.getColumn(0).setPreferredWidth(40);
columnModel.getColumn(1).setPreferredWidth(400);

header.setFont(columnHeaderFont);

employeePanel=new EmployeePanel();

container.setLayout(null);
int lm=0;		//Left Margin
int tm=0;		//Top Margin

titleLabel.setBounds(lm+10,tm+10,150,30);

searchErrorLabel.setBounds(lm+380,tm+10+20,80,20);

searchLabel.setBounds(lm+10,tm+10+30+10,80,30);
searchTextField.setBounds(lm+10+80+10,tm+10+30+10,350,30);
clearSearchButton.setBounds(lm+10+80+10+350+10,tm+10+30+10,30,30);

jsp.setBounds(lm+10,tm+10+30+10+30+10,480,250);
employeePanel.setBounds(lm+10,tm+10+30+10+30+10+250+10,480,160);

container.add(titleLabel);
container.add(searchErrorLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchButton);
container.add(jsp);
container.add(employeePanel);

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
employeeTable.getSelectionModel().addListSelectionListener(this);
}
private void searchEmployee()
{
searchErrorLabel.setText("");
String title=searchTextField.getText().trim();
if(title.length()==0) return;
int rowIndex=0;
try
{
rowIndex=employeeModel.indexOfTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not found");
return;
}
employeeTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle visibleRectangle=employeeTable.getCellRect(rowIndex,0,true);
employeeTable.scrollRectToVisible(visibleRectangle);
}
public void changedUpdate(DocumentEvent ev)
{
searchEmployee();
}
public void removeUpdate(DocumentEvent ev)
{
searchEmployee();
}
public void insertUpdate(DocumentEvent ev)
{
searchEmployee();
}
public void valueChanged(ListSelectionEvent ev)
{
int selectedRowIndex=employeeTable.getSelectedRow();
try
{
EmployeeInterface employee=employeeModel.getEmployeeAt(selectedRowIndex);
employeePanel.setEmployee(employee);
}catch(BLException blException)
{
employeePanel.clearEmployee();
}
}
private void setViewMode()
{
this.mode=MODE.VIEW;
if(employeeModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchButton.setEnabled(false);
employeeTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchButton.setEnabled(true);
employeeTable.setEnabled(true);
}
}
private void setAddMode()
{
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
clearSearchButton.setEnabled(false);
employeeTable.setEnabled(false);
}
private void setEditMode()
{
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchButton.setEnabled(false);
employeeTable.setEnabled(false);
}
private void setDeleteMode()
{
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchButton.setEnabled(false);
employeeTable.setEnabled(false);
}
private void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchButton.setEnabled(false);
employeeTable.setEnabled(false);
}
//inner_class
class EmployeePanel extends JPanel
{
private JLabel titleEmployeeLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton deleteButton;
private JButton cancelButton;
private JButton exportToPDFButton;
private JPanel buttonsPanel;
private EmployeeInterface employee;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon deleteIcon;
private ImageIcon cancelIcon;
private ImageIcon pdfIcon;
private ImageIcon saveIcon;

public EmployeePanel()
{
setBorder(BorderFactory.createLineBorder(new Color(160,160,160)));
initComponents();
setAppearance();
addListeners();
}
private void initComponents()
{
titleEmployeeLabel=new JLabel("Employee");
titleLabel=new JLabel("");
titleTextField=new JTextField();

addIcon=new ImageIcon(getClass().getResource("/icons/addEmployee.png"));
editIcon=new ImageIcon(getClass().getResource("/icons/editEmployee.png"));
cancelIcon=new ImageIcon(getClass().getResource("/icons/cancelEmployee.png"));
deleteIcon=new ImageIcon(getClass().getResource("/icons/deleteEmployee.png"));
pdfIcon=new ImageIcon(getClass().getResource("/icons/exportToPdf.png"));
saveIcon=new ImageIcon(getClass().getResource("/icons/saveEmployee.png"));

clearTitleTextFieldButton=new JButton(EmployeeUI.this.clearIcon);
buttonsPanel=new JPanel();
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
deleteButton=new JButton(deleteIcon);
cancelButton=new JButton(cancelIcon);
exportToPDFButton=new JButton(pdfIcon);
employee=null;
}
private void setAppearance()
{
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
titleEmployeeLabel.setFont(captionFont);
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm=0;
int tm=0;
titleEmployeeLabel.setBounds(lm+10,tm+20,110,30);
titleLabel.setBounds(lm+110+10+10,tm+20,350,30);
titleTextField.setBounds(lm+10+110+10,tm+20,300,30);
clearTitleTextFieldButton.setBounds(lm+10+110+10+300+10,tm+20,30,30);
buttonsPanel.setBounds(10,tm+20+30+30-5,460,75);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(170,170,170)));
addButton.setBounds(70,12,50,50);
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

add(titleEmployeeLabel);
add(titleLabel);
titleTextField.setVisible(false);
add(titleTextField);
add(clearTitleTextFieldButton);
add(buttonsPanel);

}
private void addListeners()
{
addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(EmployeeUI.this.mode==MODE.VIEW)
{
setAddMode();
}
else
{
if(addEmployee()) setViewMode();
}
}
});
editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(EmployeeUI.this.mode==MODE.VIEW)
{
setEditMode();
}
else
{
if(updateEmployee())
{
setViewMode();
}
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
setDeleteMode();
}
});
exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
//Necessary Methdods
exportToPDFEmployees();
setViewMode();
}
});
clearTitleTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
titleTextField.setText("");
titleTextField.requestFocus();
}
});
}
public boolean addEmployee()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Employee required");
titleTextField.requestFocus();
return false;
}
EmployeeInterface d=new Employee();
//d.set(title);
try
{
employeeModel.add(d);
int rowIndex=employeeModel.indexOfTitle(title,false);
employeeTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle visibleRectangle=employeeTable.getCellRect(rowIndex,0,true);
employeeTable.scrollRectToVisible(visibleRectangle);
}catch(BLException blException)
{
String exceptionMessage="";
if(blException.hasExceptions())
{
if(blException.hasGenericException()) exceptionMessage+=blException.getGenericException()+"\n";
java.util.List<String> properties=blException.getProperties();
for(String property:properties)
{
exceptionMessage+=(blException.getPropertyException(property)+"\n");
}
}
else
{
exceptionMessage="Cannot add employee title\n";
}
JOptionPane.showMessageDialog(this,exceptionMessage);
titleTextField.requestFocus();
return false;
}
return true;
}
public boolean updateEmployee()
{
String newTitle=titleTextField.getText().trim();
if(newTitle.length()==0) 
{
JOptionPane.showMessageDialog(this,"Employee required");
titleTextField.requestFocus();
return false;
}
EmployeeInterface d=new Employee();
//d.setTitle(newTitle);
//d.setCode(employee.getCode());
try
{
employeeModel.update(d);
int rowIndex=employeeModel.indexOfTitle(newTitle,false);
employeeTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle visibleRectangle=employeeTable.getCellRect(rowIndex,0,true);
employeeTable.scrollRectToVisible(visibleRectangle);
}catch(BLException blException)
{
String exceptionMessage="";
if(blException.hasExceptions())
{
exceptionMessage+=blException.getGenericException()+"\n";
java.util.List<String> properties=blException.getProperties();
for(String property:properties)
{
exceptionMessage+=(blException.getPropertyException(property)+"\n");
//System.out.printf("[%s]:  %s\n",property,blException.getPropertyException(property));
}
}
else
{
exceptionMessage="Cannot add employee title\n";
}
JOptionPane.showMessageDialog(this,exceptionMessage);
titleTextField.requestFocus();
return false;
}
return true;
}
public void deleteEmployee()
{
try
{
String title="";
//String title=employee.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this,"Delete "+title+" ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;
//employeeModel.delete(employee.getCode());
JOptionPane.showMessageDialog(this,"Employee: "+title+" deleted");
clearEmployee();
throw new BLException();
}catch(BLException blException)
{
if(blException.hasGenericException()) JOptionPane.showMessageDialog(this,blException.getGenericException());
else if(blException.hasPropertyException("title")) JOptionPane.showMessageDialog(this,"title: "+blException.getPropertyException("title"));
//else JOptionPane.showMessageDialog(this,"Unable to delete employee: "+employee.getTitle());
}
}
public void exportToPDFEmployees()
{
JFileChooser fileChooser=new JFileChooser();
fileChooser.setCurrentDirectory(new File("."));
int selectedOption=fileChooser.showSaveDialog(EmployeeUI.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
try
{
File file=fileChooser.getSelectedFile();
if(file.getName().trim().length()==0) 
{
JOptionPane.showMessageDialog(EmployeeUI.this,"Incorrect path : "+file.getAbsolutePath());
return;
}
String pdfFilePath=file.getAbsolutePath();
if(pdfFilePath.endsWith(".")) pdfFilePath+="pdf";
else if(pdfFilePath.endsWith(".pdf")==false) pdfFilePath+=".pdf";
File pdfFile=new File(pdfFilePath);
File parentFile=new File(pdfFile.getParent());
if(parentFile.exists()==false || parentFile.isDirectory()==false) 
{
JOptionPane.showMessageDialog(EmployeeUI.this,"Incorrect path : "+pdfFile.getAbsolutePath());
return;
}
EmployeeUI.this.employeeModel.exportToPDF_tm(pdfFile);
//EmployeeUI.this.employeeModel.exportToPDF(pdfFile);
//EmployeeUI.this.employeeModel.exportToPDF_pdfbox(pdfFile);
JOptionPane.showMessageDialog(EmployeeUI.this,"Export to PDF: "+pdfFile.getName());
System.out.println(pdfFile.getName()+" PDF created.");
}catch(BLException blException)
{
JOptionPane.showMessageDialog(EmployeeUI.this,"Incorrect path: "+blException.getMessage());
}catch(Exception exception)
{
JOptionPane.showMessageDialog(EmployeeUI.this,exception.getMessage());
}
}
}
public void setEmployee(EmployeeInterface employee)
{
this.employee=employee;
//titleLabel.setText(employee.getTitle());
}
public void clearEmployee()
{
titleLabel.setText("");
this.employee=null;
}
private void setViewMode()
{
EmployeeUI.this.setViewMode();
this.titleTextField.setVisible(false);
this.titleLabel.setVisible(true);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
this.clearTitleTextFieldButton.setVisible(false);
this.addButton.setIcon(addIcon);
this.editButton.setIcon(editIcon);
if(employeeModel.getRowCount()>0)
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
EmployeeUI.this.setAddMode();
this.titleTextField.setText("");
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.clearTitleTextFieldButton.setVisible(true);
this.addButton.setIcon(saveIcon);
this.cancelButton.setEnabled(true);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
private void setEditMode()
{
if(employeeTable.getSelectedRow()<0 || employeeTable.getSelectedRow()>=employeeTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select employee to edit");
return;
}
EmployeeUI.this.setEditMode();
//if(employee!=null) this.titleTextField.setText(employee.getTitle());
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.titleTextField.requestFocus();
this.clearTitleTextFieldButton.setVisible(true);
this.editButton.setIcon(saveIcon);
this.cancelButton.setEnabled(true);
this.addButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
private void setDeleteMode()
{
if(employeeTable.getSelectedRow()<0 || employeeTable.getSelectedRow()>=employeeTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select employee to delete");
return;
}
EmployeeUI.this.setDeleteMode();
this.deleteButton.setIcon(deleteIcon);
this.cancelButton.setEnabled(true);
this.addButton.setEnabled(false);
this.editButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
deleteEmployee();
EmployeeUI.this.searchEmployee();
setViewMode();

}
private void setExportToPDFMode()
{
EmployeeUI.this.setExportToPDFMode();
this.exportToPDFButton.setIcon(pdfIcon);
this.cancelButton.setEnabled(true);
this.addButton.setEnabled(false);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
}
}
}
