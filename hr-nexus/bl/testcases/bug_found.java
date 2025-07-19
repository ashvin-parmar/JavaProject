//Add method remove designation from data layer, but did not remove data from its data structure properly.


import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;

import java.util.*;
public class bug_found
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationInterface designation;
designation=new Designation();
//designation.setCode(-13);
designation.setTitle(title);
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designationManager.addDesignation(designation);
System.out.println("Designtaion added with code: "+designation.getCode());

int code=designation.getCode();
designation=new Designation();
designation.setCode(code);
designation.setTitle("New Title");
designationManager.updateDesignation(designation);
System.out.println("Designtaion Updated with title: "+designation.getTitle());

designation=new Designation();
designation.setTitle(title);
designationManager.addDesignation(designation);
System.out.println("Designtaion added with code: "+designation.getCode());


}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.printf("[%s]:  %s\n",property,blException.getPropertyException(property));
}
}
}
}
