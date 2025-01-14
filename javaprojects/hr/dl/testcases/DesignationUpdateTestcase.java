
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
public class DesignationUpdateTestcase
{
public static void main(String gg[])
{
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(Integer.parseInt(gg[0]));
designationDTO.setTitle(gg[1]);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.update(designationDTO);
System.out.println("Designation Updated to: "+designationDTO.getTitle());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
