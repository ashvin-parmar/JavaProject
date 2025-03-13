import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;


class HRNexusUI extends JFrame
{
private JLabel appIcon;
private JLabel appTitle;
private JLabel designationLabel;
private JLabel searchLabel;
private JLabel searchStatusLabel;
private JTextField search;
private JButton clearSearchButton;
private DesignationTableModel designationTableModel;
private JTable designationTable;
private JScrollPane jsp;
private JLabel outputStatusLabel;

private JButton addButton;
private JButton updateButton;
private JButton deleteButton;
private JButton cancelButton;
private JButton exportToPDFButton;
private Container container;

HRNexusUI()
{
appIcon=new JLabel(new ImageIcon("human_resources.png"));
//appIcon.setSize(30,30);
appTitle=new JLabel("HR Designation Nexus");
JPanel panel1=new JPanel(new GridLayout(1,2));
panel1.add(appIcon);
panel1.add(appTitle);

designationLabel=new JLabel("Designation");
searchStatusLabel=new JLabel("");
JPanel panel2=new JPanel(new BorderLayout());
panel2.add(designationLabel,BorderLayout.EAST);
panel2.add(new JLabel("       "),BorderLayout.CENTER);
panel2.add(searchStatusLabel,BorderLayout.WEST);


search=new JTextField(30);
searchLabel=new JLabel("Search: ");
clearSearchButton=new JButton(new ImageIcon("cancel.png"));
JPanel panel3=new JPanel(new FlowLayout());
panel3.add(searchLabel);
panel3.add(search);
panel3.add(clearSearchButton);


designationTableModel=new DesignationTableModel();
designationTable=new JTable(designationTableModel);
designationTable.setRowHeight(30);
designationTable.setFont(new Font("Times new roman",Font.PLAIN,24));
designationTable.setColumnSelectionAllowed(false);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
designationTable.setSize(700,200);


JTableHeader header=designationTable.getTableHeader();
header.setFont(new Font("Times new Roman",Font.BOLD,25));
header.setReorderingAllowed(false);
header.setResizingAllowed(false);

jsp=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);



addButton=new JButton(new ImageIcon("add.png"));
updateButton=new JButton(new ImageIcon("update.png"));
deleteButton=new JButton(new ImageIcon("delete.png"));
cancelButton=new JButton(new ImageIcon("cancel.png"));
exportToPDFButton=new JButton(new ImageIcon("exportToPDF.png"));

addActions();

JPanel finalPanel=new JPanel(new GridLayout(4,1));
finalPanel.add(panel1);
finalPanel.add(panel2);
finalPanel.add(panel3);
finalPanel.add(jsp);

container=getContentPane();
container.setLayout(new FlowLayout());
container.add(appIcon);
container.add(appTitle);
container.add(panel1);
container.add(designationLabel);
container.add(searchStatusLabel);
container.add(searchLabel);
container.add(search);
container.add(clearSearchButton);
container.add(panel2);
container.add(panel3);
container.add(jsp);
//container.add(finalPanel);
container.add(addButton);
container.add(updateButton);
container.add(deleteButton);
container.add(cancelButton);
container.add(exportToPDFButton);

setLocation(10,10);
setSize(700,800);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void addActions()
{

}
}
class testUI
{
public static void main(String gg[])
{
HRNexusUI ui=new HRNexusUI();
}
}
