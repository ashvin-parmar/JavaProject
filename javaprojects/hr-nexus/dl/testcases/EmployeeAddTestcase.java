import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;
import com.ashvin.enums.*;
import java.text.*;
import java.util.*;
import java.math.*;
public class EmployeeAddTestcase
{
public static void main(String gg[])
{
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
if(gg.length!=8) 
{
System.out.println("Usage: [EmployeeDTOInterface name designation_code Date(dd/MM/yyyy) Gender(M/F) is_indian[Y/N)] basic_salary pan_number aadhar_card_number");
return;
}
String name=gg[0];
int designationCode=Integer.parseInt(gg[1]);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth;
try
{
dateOfBirth=simpleDateFormat.parse(gg[2]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
return ;
}
char gender=gg[3].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[4]);
BigDecimal basicSalary=new BigDecimal(gg[5]);
String panNumber=gg[6];
String aadharCardNumber=gg[7];
EmployeeDAOInterface employeeDAO;
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDAO=new EmployeeDAO();
employeeDAO.add(employeeDTO);
System.out.println("Employee added");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
