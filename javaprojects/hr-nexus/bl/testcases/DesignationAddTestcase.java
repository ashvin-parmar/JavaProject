import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.manager.*;
import com.ashvin.hr.nexus.bl.pojo.manager.*;
import java.util.*;
public class DesignationAddTestcase
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
