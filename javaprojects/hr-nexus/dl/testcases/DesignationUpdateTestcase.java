import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;
public class DesignationUpdateTestcase
{
public static void main(String gg[])
{
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(Integer.parseInt(gg[0].trim()));
designationDTO.setTitle(gg[1]);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.update(designationDTO);
System.out.println("Designation Updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
