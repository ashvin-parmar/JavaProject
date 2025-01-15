
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
public class DesignationGetAllTestcase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
Set<DesignationDTOInterface> designations;
designations=designationDAO.getAll();
for(DesignationDTOInterface designation:designations)
{
System.out.println("Designation Code: "+designation.getCode()+" Designation Title: "+designation.getTitle());
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
