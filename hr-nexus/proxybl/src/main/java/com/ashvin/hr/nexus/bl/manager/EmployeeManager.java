package com.ashvin.hr.nexus.bl.manager;

import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;

import com.ashvin.enums.*;

import java.util.*;
import java.math.*;

public class EmployeeManager implements EmployeeManagerInterface
{
static private EmployeeManager employeeManager;
private EmployeeManager() throws BLException
{
}
public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
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
public Set<EmployeeInterface> getEmployees()
{
return null;
}
public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
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
public boolean employeeDesignationCodeAlloted(int designationCode) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public boolean employeeEmployeeIdExists(String employeeId)
{
return false;
}
public boolean employeePANNumberExists(String panNumber)
{
return false;
}
public boolean employeeAadharCardNumberExists(String aadharCardNumber)
{
return false;
}
public int getEmployeesCount()
{
return 0;
}
public int getEmployeesDesignationCodeCount(int designationCode) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
}
