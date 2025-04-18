import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class EmployeePANNumberExistsTestcase
{
public static void main(String gg[])
{
String panNumber=gg[0];
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
System.out.println("Employee PAN Number Exists: "+employeeDAO.panNumberExists(panNumber));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
