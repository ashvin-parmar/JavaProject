import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;

import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManagerEmployeeAadharCardNumberExistsTestcase
{
public static void main(String gg[])
{
String aadharCardNumber=gg[0];
try
{
System.out.println("Employee with Aadhar Card Number ["+aadharCardNumber+"] Exists: "+EmployeeManager.getEmployeeManager().employeeAadharCardNumberExists(aadharCardNumber));
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
