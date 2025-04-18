import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;

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
