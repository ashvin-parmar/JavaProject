package com.ashvin.components.swing;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;

class DatePickerModel extends AbstractTableModel
{
private String[] title;
private Object[][] data;
DatePickerModel()
{
populateDataStructures();
}
public int getRowCount()
{
return data.length;
}
public int getColumnCount()
{
return title.length;
}
public String getColumnName(int columnIndex)
{
return title[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
return data[rowIndex][columnIndex];
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
c=Class.forName("java.lang.Integer");
}catch(Exception e)
{
System.out.println(e);
}
return c;
}
public void populateDataStructures()
{
title=new String[7];
title[0]="S";
title[1]="M";
title[2]="T";
title[3]="W";
title[4]="T";
title[5]="F";
title[6]="S";

DatePicker datePicker=new DatePicker();
int days[][]=datePicker.getDays(5,2025);
data=new Object[days.length][days[0].length];
for(int i=0;i<days.length;i++)
{
for(int j=0;j<days[i].length;j++)
{
data[i][j]=days[i][j];
}
}
}
}
public class DatePickerUI extends JFrame
{
JTable table;
JScrollPane jsp;
JTextField dateTextField;
JButton calendarButton;
Container container;
public DatePickerUI()
{
init();
eventHandling();

Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=200;
int height=200;
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);

setLocation(x,y);
setSize(width,height);
setVisible(true);
setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

}
private void init()
{
table=new JTable(new DatePickerModel());
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
dateTextField=new JTextField("");
calendarButton=new JButton();
calendarButton.setIcon(new ImageIcon("calendar.png"));		//Not done
container=getContentPane();
container.setLayout(new BorderLayout());
JPanel panel=new JPanel(new FlowLayout());
panel.add(dateTextField);
panel.add(calendarButton);
container.add(panel,BorderLayout.NORTH);
container.add(jsp,BorderLayout.CENTER);
}
private void eventHandling()
{

}
public static void main(String gg[])
{
DatePickerUI ui=new DatePickerUI();
}
}
