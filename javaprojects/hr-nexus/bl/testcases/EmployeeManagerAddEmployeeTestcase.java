import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.pojo.manager.*;

import com.ashvin.enums.*;
import java.text.*;
import java.util.*;
import java.math.*;

public class EmployeeManagerAddEmployeeTestcase
{
public static void main(String gg[])
{
if(gg.length!=8) 
{
System.out.println("Usage: EmployeeManagerAddEmployeeTestcase <name> <designationCode> <date(dd/MM/yyyy)> <gender(M/F)> <isIndian(true/false)> <basicSalary> <panNumber> <aadharCardNumber>");
return ;
}
EmployeeInterface employee=new Employee();
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
//employee.setEmployeeId();
employee.setName(gg[0]);
employee.setDesignationCode(Integer.parseInt(gg[1]));
try
{
employee.setDateOfBirth(simpleDateFormat.parse(gg[2]));
}catch(ParseException parseException)
{
System.out.println(parseException.getMessage());
return;
}
char gender=gg[3].charAt(0);
if(gender=='M' || gender=='m')
{
employee.setGender(GENDER.MALE);
}
if(gender=='F' || gender=='f')
{
employee.setGender(GENDER.FEMALE);
}
//We can also take (Y/N) from user for better experience
employee.setIsIndian(Boolean.parseBoolean(gg[4]));
employee.setBasicSalary(new BigDecimal(gg[5]));
employee.setPANNumber(gg[6]);
employee.setAadharCardNumber(gg[7]);
try
{
(EmployeeManager.getEmployeeManager()).addEmployee(employee);
System.out.println("Employee added with Employee Id: "+employee.getEmployeeId());

}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getPropertyException(property));
}
}
}
}
