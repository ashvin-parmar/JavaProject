package com.ashvin.hr.nexus.bl.pojo.manager;

import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;

import com.ashvin.enums.*;

import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;

import java.util.*;
import java.math.*;

public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeeMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeeMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeeMap;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeeMap;
private Set<EmployeeInterface> employeeSet;
private EmployeeManager employeeManager;
private EmployeeManager() throws BLException
{
populateDataStrcutures();
}
private void populateDataStrcutures() throws BLException
{
employeeIdWiseEmployeeMap=new HashMap<String,EmployeeInterface>();
panNumberWiseEmployeeMap=new HashMap<String,EmployeeInterface>();
aadharCardNumberWiseEmployeeMap=new HashMap<String,EmployeeInterface>();
designationCodeWiseEmployeeMap=new HashMap<Integer,Set<EmployeeInterface>>();
employeeSet=new TreeSet<EmployeeInterface>();
try
{
EmployeeDAOInterface dlEmployeeDAO;
dlEmployeeDAO=new EmployeeDAO();
Set<EmployeeDTOInterface> dlEmployees=dlEmployeeDAO.getAll();
Set<EmployeeInterface> list;
for(EmployeeDTOInterface dlEmployee:dlEmployees)
{
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
employee.setDesignationCode(dlEmployee.getDesignationCode());
employee.setDateOfBirth(dlEmployee.getDateOfBirth());
employee.setGender(dlEmployee.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dlEmployee.isIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());

employeeIdWiseEmployeeMap.put(employee.getEmployeeId(),employee);
panNumberWiseEmployeeMap.put(employee.getPANNumber(),employee);
aadharCardNumberWiseEmployeeMap.put(employee.getAadharCardNumber(),employee);
if(designationCodeWiseEmployeeMap.containsKey(employee.getDesignationCode()))
{
list=designationCodeWiseEmployeeMap.get(employee.getDesignationCode());
}
else
{
list=new TreeSet<>();
}
list.add(employee);
designationCodeWiseEmployeeMap.put(employee.getDesignationCode(),list);
employeeSet.add(employee);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(this.employeeManager!=null) this.employeeManager=new EmployeeManager();
return this.employeeManager;
}
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public void removeEmployee(String employeeId) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public Set<EmployeeInterface> getEmployees() throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public Set<EmployeeInterface> getEmployeesByDesignationCode() throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public boolean employeeDesignationCodeAlloted() throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public boolean employeeEmployeeIdExists(String employeeId) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public boolean employeePANNumberExists(String panNumber) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public boolean employeeAadharCardNumberExists(String aadharCardNumber) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public int getEmployeesCount() throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public int getEmployeesDesignationCodeCount() throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;

}
}
