import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dao.*;
import java.util.*;

class DesignationTableModel extends AbstractTableModel
{
private Object data[][];
private String title[];
DesignationTableModel()
{
populateDataStructure();
}
public int getColumnCount()
{
return title.length;
}
public int getRowCount()
{
return data.length;
}
public String getColumnName(int columnIndex)
{
return title[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return new Integer(rowIndex+1);
if(columnIndex==1) return data[rowIndex][0];
if(columnIndex==2) return data[rowIndex][1];
return null;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0 || columnIndex==1)
{
c=Class.forName("java.lang.Integer");
}
if(columnIndex==2)
{
c=Class.forName("java.lang.String");
}
}catch(Exception exception)
{

}
return c;
}

public void populateDataStructure()
{
title=new String[3];
title[0]="S. No.";
title[1]="Code";
title[2]="Title";
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();

Set<DesignationDTOInterface> designations;
designations=designationDAO.getAll();

data=new Object[designations.size()][2];
int i=0;
for(DesignationDTOInterface designation:designations)
{
//System.out.println("Designation Code: "+designation.getCode()+" Designation Title: "+designation.getTitle());
data[i][0]=designation.getCode();
data[i][1]=designation.getTitle();
i++;
}
}catch(DAOException daoException)
{
//System.out.println(daoException.getMessage());
data=null;
}
}
}

class DesignationModule extends JFrame
{
private JTable table;
private JScrollPane jsp;
private DesignationTableModel designationTableModel;
private Container container;
DesignationModule()
{
super("Designation Table Module");
designationTableModel=new DesignationTableModel();
table=new JTable(designationTableModel);
table.setRowSelectionAllowed(true);
table.setColumnSelectionAllowed(false);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.setFont(new Font("Times new roman",Font.PLAIN,20));
table.setRowHeight(25);

JTableHeader header=table.getTableHeader();
header.setFont(new Font("Times new Roman",Font.BOLD,25));
header.setReorderingAllowed(false);
header.setResizingAllowed(false);

jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);

Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
int width=600;
int height=600;
int x=(dimension.width/2)-(width/2);
int y=(dimension.height/2)-(height/2);
setSize(width,height);
setLocation(x,y);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
}

class DesignationModuleTest
{
public static void main(String gg[])
{
DesignationModule module=new DesignationModule();
}
}
