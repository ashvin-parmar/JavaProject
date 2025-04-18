import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;
import java.util.*;
public class EmployeeGetDesignationCountTestcase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
int count=employeeDAO.getCountByDesignation(designationCode);
System.out.println("Number of records: "+count);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
