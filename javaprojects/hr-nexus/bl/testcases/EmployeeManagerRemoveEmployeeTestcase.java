import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;

import java.util.*;
public class EmployeeManagerRemoveEmployeeTestcase
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeManager.getEmployeeManager().removeEmployee(employeeId);
System.out.printf("Employee with employee id : %s Removed\n",employeeId);
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
