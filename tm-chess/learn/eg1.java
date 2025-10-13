import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

class MyModel extends AbstractTableModel
{
private Object[][] data;
private String[] title={"A","B"};
MyModel()
{
data=new Object[2][2];
data[0][0]="One";
data[0][1]=new JButton("Button one");
data[1][0]="Two";
data[1][1]=new JButton("Button two");
}
public String getColumnName(int column)
{
return title[column];
}
public Object getValueAt(int row,int column)
{
return data[row][column];
}
public boolean isCellEditable(int row,int column)
{
if(column==1) return true;
return false;
}
public int getColumnCount()
{
return title.length;
}
public int getRowCount()
{
return data.length;
}
public Class getColumnClass(int column)		//No Change occured
{
return data[0][column].getClass();
//if(column==1) return JButton.class;
//return String.class;
}
public void setValueAt(Object value,int row,int column)
{
System.out.println(row+","+column+","+value.toString());
}
}

class Whatever extends JFrame
{
private JTable table;
private MyModel model;
private Container container;
Whatever()
{
super("Whatever");
model=new MyModel();
table=new JTable(model);
table.getColumn("B").setCellRenderer(new ButtonRenderer());
table.getColumn("B").setCellEditor(new ButtonCellEditor());

container=getContentPane();
container.setLayout(new BorderLayout());
container.add(table);
setSize(500,400);
setLocation(10,10);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public static void main(String gg[])
{
Whatever whatever=new Whatever();
}
class ButtonRenderer implements TableCellRenderer
{
public Component getTableCellRendererComponent(JTable table,Object value,boolean a,boolean b,int row,int column)
{
System.out.println("getTableCellRendererComponent got called");
System.out.println(value.toString());
return (JButton)value;
}
}
class ButtonCellEditor extends DefaultCellEditor
{
private JButton button;
private boolean isClicked;
private int row,column;
ButtonCellEditor()
{
super(new JCheckBox());
button=new JButton();
button.setOpaque(true);
button.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
System.out.println("Great");
fireEditingStopped();
}
});
}
public Component getTableCellEditorComponent(JTable table,Object value,boolean a,int row,int column)
{
System.out.println("getTableCellEditorComponent got called");
//System.out.println(row+","+column+","+value.toString());
this.row=row;
this.column=column;
button.setForeground(Color.black);
button.setBackground(UIManager.getColor("Button.background"));
button.setText("Whatever");
isClicked=true;
return button;
}
public Object getCellEditorValue()
{
System.out.println("getCellEditorValue got called");
return "cool";
}
public boolean stopCellEditing()
{
isClicked=false;
return super.stopCellEditing();
}
public void fireEditingStopped()
{
//do custom things over here and then call original super class method and return as such.
super.fireEditingStopped();
}
}
}
