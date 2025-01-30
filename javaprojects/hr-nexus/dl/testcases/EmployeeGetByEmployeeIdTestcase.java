import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class EmployeeGetByEmployeeIdTestcase
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeDTOInterface employee;
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employee=employeeDAO.getByEmployeeId(employeeId);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
System.out.println("Id: "+employee.getEmployeeId());
System.out.println("Name: "+employee.getName());
System.out.println("Designation Code: "+employee.getDesignationCode());
System.out.println("Date of birth: "+simpleDateFormat.format(employee.getDateOfBirth()));
System.out.println("Gender: "+employee.getGender());
System.out.println("Is Indian: "+employee.isIndian());
System.out.println("Basic Salary: "+employee.getBasicSalary());
System.out.println("PAN Number: "+employee.getPANNumber());
System.out.println("Aadhar Card Number: "+employee.getAadharCardNumber());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
