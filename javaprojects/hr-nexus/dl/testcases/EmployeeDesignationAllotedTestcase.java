import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class EmployeeDesignationAllotedTestcase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
System.out.println("Employee Designation Alloted: "+employeeDAO.isDesignationAlloted(designationCode));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
