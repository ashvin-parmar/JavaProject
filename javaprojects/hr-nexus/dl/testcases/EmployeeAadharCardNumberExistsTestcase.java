import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class EmployeeAadharCardNumberExistsTestcase
{
public static void main(String gg[])
{
String aadharCardNumber=gg[0];
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
System.out.println("Employee Aadhar Card Number Exists: "+employeeDAO.aadharCardNumberExists(aadharCardNumber));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
