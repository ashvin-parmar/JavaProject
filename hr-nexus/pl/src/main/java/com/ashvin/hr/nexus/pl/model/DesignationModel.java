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
private List<DesignationInterface> designations;
private String columnTitles[];
private DesignationManagerInterface designationManager;
public DesignationModel()
{
populateDataStructure();
}
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
}
}
