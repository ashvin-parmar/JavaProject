import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;

import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManagerGetEmployeesByDesignationCodeTestcase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
Set<EmployeeInterface> employees=EmployeeManager.getEmployeeManager().getEmployeesByDesignationCode(designationCode);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
for(EmployeeInterface employee:employees)
{
System.out.println("Employee id: "+employee.getEmployeeId());
System.out.println("Name: "+employee.getName());
System.out.println("Designation Code: "+employee.getDesignationCode());
System.out.println("Date of birth: "+simpleDateFormat.format(employee.getDateOfBirth()));
System.out.println("Gender: "+(employee.getGender()=='M'?"Male":"Female"));
System.out.println("Is Indian: "+(employee.getIsIndian()?"Yes":"No"));
System.out.println("Basic Salary: "+employee.getBasicSalary().toPlainString());
System.out.println("PAN Number: "+employee.getPANNumber());
System.out.println("Aadhar Card Number: "+employee.getAadharCardNumber());
System.out.println("*******************************************");
}
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
