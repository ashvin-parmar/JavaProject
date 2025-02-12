import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;
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
