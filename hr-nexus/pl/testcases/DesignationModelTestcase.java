import com.ashvin.hr.nexus.pl.model.*;
import com.ashvin.hr.nexus.bl.exceptions.*;

import java.awt.*;
import javax.swing.*;

class DesignationModelTestcase extends JFrame
{
private DesignationModel designationModel;
private JTable table;
private JScrollPane jsp;
private Container container;
public DesignationModelTestcase()
{
designationModel=new DesignationModel();
table=new JTable(designationModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

container=getContentPane();

container.setLayout(new BorderLayout());
container.add(jsp);

setLocation(100,100);
setSize(400,400);
setVisible(true);
}
public static void main(String gg[])
{
DesignationModelTestcase t=new DesignationModelTestcase();
}
}
