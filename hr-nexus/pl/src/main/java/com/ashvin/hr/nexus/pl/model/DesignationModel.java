package com.ashvin.hr.nexus.pl.model;

import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;
import com.ashvin.hr.nexus.bl.exceptions.*;

import java.util.*;
import java.io.*;
import javax.swing.table.*;

import com.itextpdf.kernel.colors.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import com.itextpdf.io.image.ImageType;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> designations;		//Wrong DS
private String columnTitles[];
private DesignationManagerInterface designationManager;
public DesignationModel()
{
populateDataStructure();
}
//Model Specific Methods
public int getColumnCount()
{
return columnTitles.length;
}
public int getRowCount()
{
return designations.size();
}
public String getColumnName(int columnIndex)
{
return columnTitles[columnIndex];
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class;
return String.class;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
return designations.get(rowIndex).getTitle(); 
}

//private methods for internal uses
private void populateDataStructure()
{
columnTitles=new String[2];
columnTitles[0]="S. No.";
columnTitles[1]="Designation Title";

Set<DesignationInterface> blDesignations;
try
{
designationManager=DesignationManager.getDesignationManager();
blDesignations=designationManager.getDesignations();
}catch(BLException blException)
{
blDesignations=new TreeSet<DesignationInterface>();
System.out.println("User Specific Message");
//Something to do
}
designations=new LinkedList<>();
for(DesignationInterface designation:blDesignations)
{
designations.add(designation);
}
Collections.sort(designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}

//Project Specific Methods
public void add(DesignationInterface designation) throws BLException
{
designationManager.addDesignation(designation);
designations.add(designation);
//Sorting
Collections.sort(designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public int indexOfDesignation(DesignationInterface designation) throws BLException
{
Iterator<DesignationInterface> iterator=designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(d.equals(designation))
{
return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid designation: "+designation.getTitle());
throw blException;
}
public int indexOfTitle(String title,boolean isPartial) throws BLException
{
Iterator<DesignationInterface> iterator=designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(isPartial)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase()))
{
return index;
}
}
else
{
if(d.getTitle().equalsIgnoreCase(title))
{
return index;
}
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid designation: "+title);
throw blException;
}
public void update(DesignationInterface designation) throws BLException
{
designationManager.updateDesignation(designation);
designations.remove(indexOfDesignation(designation));
designations.add(designation);
//Sorting
Collections.sort(designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public void delete(int code) throws BLException
{
designationManager.removeDesignation(code);
DesignationInterface d;
Iterator<DesignationInterface> iterator=designations.iterator();
boolean flag=false;
while(iterator.hasNext())
{
d=iterator.next();
if(d.getCode()==code)
{
designations.remove(indexOfDesignation(d));
flag=true;
break;
}
}
if(!flag)
{
BLException blException=new BLException();
blException.setGenericException("Invalid code: "+code);
throw blException;
}
fireTableDataChanged();
}
public void exportToPDF(File file) throws BLException
{
try
{
if(file.exists()) file.delete();
PdfWriter pdfWriter=new PdfWriter(file.getAbsolutePath());
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document document=new Document(pdfDocument);

PdfFont titleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

//Header
Paragraph top=new Paragraph();
//Image logo=new Image(ImageDataFactory.create("/resources/icons/logo.png"));
//top.add(logo);
top.add("HR-Nexus").setFont(titleFont).setFontSize(30).setTextAlignment(TextAlignment.CENTER);
Paragraph title=new Paragraph("Designation");
title.setFont(titleFont).setFontSize(20).setTextAlignment(TextAlignment.CENTER);
Text pageNumberText;

float[] columnWidth={1,3};
Table table=new Table(UnitValue.createPercentArray(columnWidth)).useAllAvailableWidth();
Cell cell0;
Cell cell1;

Cell headerCell0=new Cell().add(new Paragraph("S.No.").setFont(titleFont).setFontSize(18).setBackgroundColor(ColorConstants.BLUE));
Cell headerCell1=new Cell().add(new Paragraph("Designation").setFont(titleFont).setFontSize(18).setBackgroundColor(ColorConstants.BLUE)); 

Paragraph creator=new Paragraph("Creator: Ashvin Parmar");
creator.setFont(titleFont).setFontSize(18).setFontColor(ColorConstants.BLACK);

int sno=0;
int pageSize=22;
boolean newPage=true;
int pageNumber=0;
for(int i=0;i<designations.size();i++)
{
if(newPage)
{
document.add(top);
pageNumberText=new Text(new Integer(pageNumber).toString());
pageNumberText.setTextAlignment(TextAlignment.RIGHT).setFont(dataFont).setFontSize(18);
document.add(title).add(new Paragraph(pageNumberText).setTextAlignment(TextAlignment.RIGHT));
table=new Table(UnitValue.createPercentArray(columnWidth)).useAllAvailableWidth();
table.addHeaderCell(headerCell0);
table.addHeaderCell(headerCell1);
//create Header
newPage=false;
}
//Add row to table
sno++;
cell0=new Cell().add(new Paragraph(new Integer(sno).toString()));
cell0.setFont(dataFont).setFontSize(16).setTextAlignment(TextAlignment.RIGHT);
cell1=new Cell().add(new Paragraph(designations.get(i).getTitle()));
cell1.setFont(dataFont).setFontSize(16).setTextAlignment(TextAlignment.JUSTIFIED);
table.addCell(cell0);
table.addCell(cell1);

if(sno%pageSize==0 || sno==designations.size())
{
//add table to page
//add creator name
document.add(table);
document.add(creator);
if(sno<designations.size())
{
//add new page
System.out.println("New page to add");

}
newPage=true;
}
}
document.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
BLException blException=new BLException();
blException.setGenericException("Unable to create "+file.getName());
throw blException;
}
}
public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index>=designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid index "+index);
throw blException;
}
return designations.get(index);
}
}
