import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;
import com.ashvin.enums.*;
import java.text.*;
import java.util.*;
import java.math.*;
public class EmployeeUpdateTestcase
{
public static void main(String gg[])
{
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
if(gg.length!=9) 
{
System.out.println("Usage: EmployeeUpdateTestcase <employee_id> <name> <designation_code> <date_of_birth(dd/MM/yyyy)> <gender(M/F)> <is_indian(Y/N))> <basic_salary> <pan_number> <aadhar_card_number>");
return;
}
String employeeId=gg[0];
String name=gg[1];
int designationCode=Integer.parseInt(gg[2]);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth;
try
{
dateOfBirth=simpleDateFormat.parse(gg[3]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
return ;
}
char gender=gg[4].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[5]);
BigDecimal basicSalary=new BigDecimal(gg[6]);
String panNumber=gg[7];
String aadharCardNumber=gg[8];
EmployeeDAOInterface employeeDAO;
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender=='M') employeeDTO.setGender(GENDER.MALE);
if(gender=='F') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDAO=new EmployeeDAO();
employeeDAO.update(employeeDTO);
System.out.println("Employee updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
