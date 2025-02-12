import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dao.*;
public class EmployeeDeleteTestcase
{
public static void main(String gg[])
{
try
{
String employeeId=gg[0];
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.delete(employeeId);
System.out.println("Employee with employee id "+employeeId+" Deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
