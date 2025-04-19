import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class EmployeeGetByDesignationCodeTestcase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
Set<EmployeeDTOInterface> employees;
DesignationDTOInterface designationDTO;
DesignationDAOInterface designationDAO=new DesignationDAO();
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
designationDTO=designationDAO.getByCode(designationCode);
employees=employeeDAO.getByDesignationCode(designationCode);
System.out.println("Designation Code: "+designationDTO.getCode()+" Desigantion Title: "+designationDTO.getTitle());
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
if(employees.size()==0) 
{
System.out.printf("There are no employee with respective designation code\n");
return ;
}
employees.forEach((employee)->{
System.out.println("Id: "+employee.getEmployeeId());
System.out.println("Name: "+employee.getName());
System.out.println("Designation Code: "+employee.getDesignationCode());
System.out.println("Date of birth: "+simpleDateFormat.format(employee.getDateOfBirth()));
System.out.println("Gender: "+employee.getGender());
System.out.println("Is Indian: "+employee.isIndian());
System.out.println("Basic Salary: "+employee.getBasicSalary());
System.out.println("PAN Number: "+employee.getPANNumber());
System.out.println("Aadhar Card Number: "+employee.getAadharCardNumber());
System.out.println("-------------------------------------------------");
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
