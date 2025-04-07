package com.ashvin.hr.nexus.pl.model;

import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;
import com.ashvin.hr.nexus.bl.exceptions.*;

import java.util.*;
import java.io.*;
import javax.swing.table.*;

//For itextpdf
import com.itextpdf.kernel.colors.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import com.itextpdf.layout.borders.*;
import com.itextpdf.io.image.*;

//For pdfbox
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.*;

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
Image logo=new Image(ImageDataFactory.create(getClass().getResource("/icons/hr_nexus_logo1.png")));
top.add(logo);
top.add(new Text("                         "));
top.add("HR-Nexus").setFont(titleFont).setFontSize(30).setTextAlignment(TextAlignment.JUSTIFIED);
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
int pageSize=20;
boolean newPage=true;
int pageNumber=0;
for(int i=0;i<designations.size();i++)
{
if(newPage)
{
document.add(top);
pageNumberText=new Text("Page no: "+String.valueOf(++pageNumber));
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
cell0=new Cell().add(new Paragraph(String.valueOf(sno)));
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
public void exportToPDF_pdfbox(File file) throws BLException
{
try
{
if(file.exists()) file.delete();
PDDocument document=new PDDocument();
PDPage page=new PDPage();;

PDType1Font titleFont=new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD);
PDType1Font dataFont=new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
PDPageContentStream contentStream=new PDPageContentStream(document,page);

//Header
//PDImageXObject logo=PDImageXObject.createFromFile(getClass().getResource("/icons/logo.png").toString());
String companyName="HR-Nexus";

String title="Designations";
String header1="S.No.";
String header2="Designation";
String designer="Designer: Ashvin Parmar";
//table creation variables

float tableTopY=680;
float margin=50;
float tableWidth=page.getMediaBox().getWidth()-2*margin;
float rowHeight=25;
float col1Width=tableWidth/5;
float col2Width=tableWidth-col1Width;
float dataXMargin=5;
float dataYMargin=20;
int rowNumber=0;

//pdf creation variables
int sno=0;
int pageSize=22;
boolean newPage=true;
int pageNumber=0;
for(int i=0;i<designations.size();i++)
{
if(newPage)
{
page=new PDPage();
document.addPage(page);
contentStream.close();
contentStream=new PDPageContentStream(document,page);
//contentStream.drawImage(logo,30,tableTopY+100+80);
contentStream.beginText();
contentStream.setFont(titleFont,30);
contentStream.newLineAtOffset(30+180+20,tableTopY+60);
contentStream.showText(companyName);
contentStream.endText();

contentStream.beginText();
contentStream.setFont(titleFont,20);
contentStream.newLineAtOffset(margin+10,tableTopY+20);
contentStream.showText(title);
contentStream.endText();
contentStream.beginText();
contentStream.setFont(dataFont,16);
contentStream.newLineAtOffset(180+300+5,tableTopY+10);
contentStream.showText("Page no: "+String.valueOf(pageNumber+1));
contentStream.endText();

contentStream.moveTo(margin,tableTopY);
contentStream.lineTo(margin+tableWidth,tableTopY);
contentStream.moveTo(margin,tableTopY-rowHeight);
contentStream.lineTo(margin+tableWidth,tableTopY-rowHeight);

contentStream.moveTo(margin,tableTopY);
contentStream.lineTo(margin,tableTopY-rowHeight);
contentStream.moveTo(margin+col1Width,tableTopY);
contentStream.lineTo(margin+col1Width,tableTopY-rowHeight);
contentStream.moveTo(margin+tableWidth,tableTopY);
contentStream.lineTo(margin+tableWidth,tableTopY-rowHeight);

contentStream.stroke();

contentStream.beginText();
contentStream.setFont(titleFont,18);
contentStream.newLineAtOffset(margin+dataXMargin,tableTopY-dataYMargin);
contentStream.showText(header1);
contentStream.endText();
contentStream.beginText();
contentStream.setFont(titleFont,18);
contentStream.newLineAtOffset(margin+col1Width+dataXMargin,tableTopY-dataYMargin);
contentStream.showText(header2);
contentStream.endText();
rowNumber=1;
//create Header
newPage=false;
}
//Add row to table
sno++;

float x=rowNumber*rowHeight;
 
contentStream.moveTo(margin,tableTopY-x-rowHeight);
contentStream.lineTo(margin+tableWidth,tableTopY-x-rowHeight);

contentStream.moveTo(margin,tableTopY-x);
contentStream.lineTo(margin,tableTopY-x-rowHeight);
contentStream.moveTo(margin+col1Width,tableTopY-x-rowHeight);
contentStream.lineTo(margin+col1Width,tableTopY-x);
contentStream.moveTo(margin+tableWidth,tableTopY-x-rowHeight);
contentStream.lineTo(margin+tableWidth,tableTopY-x);

contentStream.stroke();

contentStream.beginText();
contentStream.setFont(dataFont,14);
contentStream.newLineAtOffset(margin+dataXMargin,tableTopY-dataYMargin-x);
contentStream.showText(String.valueOf(sno));
contentStream.endText();
contentStream.beginText();
contentStream.newLineAtOffset(margin+col1Width+dataXMargin,tableTopY-dataYMargin-x);

contentStream.showText(designations.get(i).getTitle());
contentStream.endText();
rowNumber++;
if(sno%pageSize==0 || sno==designations.size())
{
//add table to page
//add creator name

contentStream.beginText();
contentStream.setFont(titleFont,14);
contentStream.newLineAtOffset(margin,tableTopY-x-2*rowHeight);
contentStream.showText(designer);
contentStream.endText();

if(sno<designations.size())
{
//add new page
System.out.println("New page to add");

}
newPage=true;
}
}
contentStream.close();
document.save(file.getAbsolutePath());
document.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
BLException blException=new BLException();
blException.setGenericException("Unable to create "+file.getName());
throw blException;
}
}
public void exportToPDF_tm(File file) throws BLException
{
try
{
if(file.exists()) file.delete();
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document document=new Document(pdfDocument);

PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont columnTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

Image logo=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/hr_nexus_logo1.png")));
Paragraph logoPara=new Paragraph();
logoPara.add(logo);
Paragraph companyNamePara=new Paragraph();
companyNamePara.add("  HR-Nexus");
companyNamePara.setFont(companyNameFont).setFontSize(18);
Paragraph reportTitlePara=new Paragraph("List of designations");
reportTitlePara.setFont(reportTitleFont).setFontSize(15);
Paragraph columnTitle1=new Paragraph("S.No.");
columnTitle1.setFont(columnTitleFont).setFontSize(14);
Paragraph columnTitle2=new Paragraph("Designations");
columnTitle1.setFont(columnTitleFont).setFontSize(14);
Paragraph pageNumberParagraph;
Paragraph dataParagraph;

float topTableColumnWidths[]={1,5};
float dataTableColumnWidths[]={1,5};

Table topTable;
Table pageNumberTable;
Table dataTable=null;
Cell cell;

int sno=0;
int pageSize=26;
boolean newPage=true;
int numberOfPages=this.designations.size()/pageSize+(this.designations.size()%pageSize!=0?1:0);
int pageNumber=0;
for(int i=0;i<this.designations.size();i++)
{
if(newPage)
{
pageNumber++;
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidths)).useAllAvailableWidth();
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(logoPara);
topTable.addCell(cell);
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(companyNamePara);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);
document.add(topTable);
pageNumberParagraph=new Paragraph("Page: "+pageNumber+"/"+numberOfPages);
pageNumberParagraph.setFont(pageNumberFont).setFontSize(15);
pageNumberTable=new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
document.add(pageNumberTable);
dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidths)).useAllAvailableWidth();
cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(cell);
newPage=false;
}
sno++;
cell=new Cell();
dataParagraph=new Paragraph(String.valueOf(sno));
dataParagraph.setFont(dataFont).setFontSize(14);
cell.add(dataParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);
cell=new Cell();
dataParagraph=new Paragraph(designations.get(i).getTitle());
dataParagraph.setFont(dataFont).setFontSize(14);
cell.add(dataParagraph);
dataTable.addCell(cell);

if(sno%pageSize==0 || sno==designations.size())
{
document.add(dataTable);
document.add(new Paragraph("Software by: Ashvin Parmar"));
if(sno<designations.size()) 
{
//new page created
document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}
}
document.close();
}catch(Exception exception)
{
BLException blException=new BLException();
blException.setGenericException(""+file.getName());
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
