import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;
import com.ashvin.enums.*;
import java.text.*;
import java.util.*;
import java.math.*;

public class EmployeeManagerUpdateEmployeeTestcase
{
public static void main(String gg[])
{
if(gg.length!=9) 
{
System.out.println("Usage: EmployeeManagerUpdateEmployeeTestcase <emoloyeeId> <name> <designationCode> <date(dd/MM/yyyy)> <gender(M/F)> <isIndian(true/false)> <basicSalary> <panNumber> <aadharCardNumber>");
return ;
}
EmployeeInterface employee=new Employee();
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
DesignationInterface designation=new Designation();
employee.setEmployeeId(gg[0]);
employee.setName(gg[1]);
designation.setCode(Integer.parseInt(gg[2]));
employee.setDesignation(designation);
try
{
employee.setDateOfBirth(simpleDateFormat.parse(gg[3]));
}catch(ParseException parseException)
{
System.out.println(parseException.getMessage());
return;
}
char gender=gg[4].charAt(0);
if(gender=='M' || gender=='m')
{
employee.setGender(GENDER.MALE);
}
if(gender=='F' || gender=='f')
{
employee.setGender(GENDER.FEMALE);
}
//We can also take (Y/N) from user for better experience
employee.setIsIndian(Boolean.parseBoolean(gg[5]));
employee.setBasicSalary(new BigDecimal(gg[6]));
employee.setPANNumber(gg[7]);
employee.setAadharCardNumber(gg[8]);
try
{
(EmployeeManager.getEmployeeManager()).updateEmployee(employee);
System.out.println("Employee updated");

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
