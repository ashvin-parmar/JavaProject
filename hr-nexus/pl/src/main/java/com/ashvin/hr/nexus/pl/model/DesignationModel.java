package com.ashvin.hr.nexus.pl.model;

import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;
import com.ashvin.hr.nexus.bl.exceptions.*;

import java.util.*;
import javax.swing.table.*;

public class DesignationModel extends AbstractTableModel
{
private List<DesignationInterface> designations;		//Wrong DS
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
}
