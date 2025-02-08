import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;

import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManagerGetEmployeeByEmployeeIdTestcase
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeInterface employee=EmployeeManager.getEmployeeManager().getEmployeeByEmployeeId(employeeId);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
DesignationInterface designation;
System.out.println("Employee id: "+employee.getEmployeeId());
System.out.println("Name: "+employee.getName());
designation=employee.getDesignation();
System.out.println("Designation Code: "+designation.getCode());
System.out.println("Designation title: "+designation.getTitle());
System.out.println("Date of birth: "+simpleDateFormat.format(employee.getDateOfBirth()));
System.out.println("Gender: "+(employee.getGender()=='M'?"Male":"Female"));
System.out.println("Is Indian: "+(employee.getIsIndian()?"Yes":"No"));
System.out.println("Basic Salary: "+employee.getBasicSalary().toPlainString());
System.out.println("PAN Number: "+employee.getPANNumber());
System.out.println("Aadhar Card Number: "+employee.getAadharCardNumber());
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
