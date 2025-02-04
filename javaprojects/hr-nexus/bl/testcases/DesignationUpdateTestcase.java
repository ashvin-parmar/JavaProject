import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.manager.*;
import com.ashvin.hr.nexus.bl.pojo.manager.*;
import java.util.*;
public class DesignationUpdateTestcase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
String title=gg[1];
try
{
DesignationInterface designation;
designation=new Designation();
designation.setCode(code);
designation.setTitle(title);
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designationManager.updateDesignation(designation);
System.out.println("Designtaion Updated");
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
