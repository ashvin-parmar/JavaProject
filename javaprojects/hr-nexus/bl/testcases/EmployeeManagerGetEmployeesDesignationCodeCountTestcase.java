import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;

import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManagerGetEmployeesDesignationCodeCountTestcase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
int count=EmployeeManager.getEmployeeManager().getEmployeesDesignationCodeCount(designationCode);
System.out.println("Employees with Designatio Code ["+designationCode+"] Count: "+count);
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
