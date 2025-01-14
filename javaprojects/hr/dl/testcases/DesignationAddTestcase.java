
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
public class DesignationAddTestcase
{
public static void main(String gg[])
{
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setTitle(gg[0]);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
System.out.println("Designation : "+designationDTO.getTitle()+" added with code: "+designationDTO.getCode());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
