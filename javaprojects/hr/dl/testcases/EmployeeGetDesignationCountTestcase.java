
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
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
