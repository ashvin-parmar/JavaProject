import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.manager.*;
import com.ashvin.hr.nexus.bl.pojo.manager.*;
import java.util.*;
public class DesignationManagerGetDesignationByCodeTestcase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationInterface designation;
designation=DesignationManager.getDesignationManager().getDesignationByCode(code);
System.out.printf("Designation code: %d, Designation Title: %s\n",designation.getCode(),designation.getTitle());
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
