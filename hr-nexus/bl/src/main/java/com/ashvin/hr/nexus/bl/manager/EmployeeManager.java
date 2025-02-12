package com.ashvin.hr.nexus.bl.manager;

import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
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
private static EmployeeManager employeeManager;
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
employee.setDesignation(((DesignationManager)(DesignationManager.getDesignationManager())).getDSDesignationByCode(dlEmployee.getDesignationCode()));
employee.setDateOfBirth((Date)dlEmployee.getDateOfBirth().clone());
employee.setGender(dlEmployee.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dlEmployee.isIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());

employeeIdWiseEmployeeMap.put(employee.getEmployeeId().toUpperCase(),employee);
panNumberWiseEmployeeMap.put(employee.getPANNumber().toUpperCase(),employee);
aadharCardNumberWiseEmployeeMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
if(designationCodeWiseEmployeeMap.containsKey(dlEmployee.getDesignationCode()))
{
list=designationCodeWiseEmployeeMap.get(dlEmployee.getDesignationCode());
}
else
{
list=new TreeSet<>();
}
list.add(employee);
designationCodeWiseEmployeeMap.put(dlEmployee.getDesignationCode(),list);
employeeSet.add(employee);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
}
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)
{
blException.setGenericException("Employee required.");
throw blException;
}
String employeeId=employee.getEmployeeId();
if(employeeId!=null)
{
if((employeeId=employeeId.trim()).length()!=0) blException.addPropertyException("employeeId","Employee id must empty");
}
String name=employee.getName();
if(name==null) blException.addPropertyException("name","Name required");
else if((name=name.trim()).length()==0) blException.addPropertyException("name","Name required");
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
if(designation==null)
{
blException.addPropertyException("designation","Designation required");
}
else
{
designationCode=designation.getCode();
if(designationCode<=0) blException.addPropertyException("designationCode","Designation code should not be negative or zero.");
else if((DesignationManager.getDesignationManager()).designationCodeExists(designationCode)==false) blException.addPropertyException("designationCode","Invalid designation code: "+designationCode);
}
Date dateOfBirth=employee.getDateOfBirth();
if(dateOfBirth==null) blException.addPropertyException("dateOfBirth","Date of birth required");
char gender=employee.getGender();
if(gender==' ') blException.addPropertyException("gender","Gender required");
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null) blException.addPropertyException("basicSalary","Basic salary required.");
else if(basicSalary.signum()<0) blException.addPropertyException("basicSalary","Basic salary can not be negative,");
String panNumber=employee.getPANNumber();
if(panNumber==null) blException.addPropertyException("panNumber","PAN number required.");
else if((panNumber=panNumber.trim()).length()==0) blException.addPropertyException("panNumber","PAN number required.");
else if(panNumberWiseEmployeeMap.containsKey(panNumber.toUpperCase())==true) blException.addPropertyException("panNumber","PAN number exists.");
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null) blException.addPropertyException("aadharCardNumber","Aadhar number required.");
else if((aadharCardNumber=aadharCardNumber.trim()).length()==0) blException.addPropertyException("aadharCardNumber","Aadhar card number required.");
else if(aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber.toUpperCase())==true) blException.addPropertyException("aadharCardNumber","Aadhar card number exists.");
if(blException.hasExceptions()) throw blException;
try
{
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designationCode);
dlEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dlEmployee.setGender(gender=='M'?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);

//add in Data Layer
(new EmployeeDAO()).add(dlEmployee);
employeeId=dlEmployee.getEmployeeId();
employee.setEmployeeId(employeeId);	//Add in employee
//if added in Data Layer

DesignationInterface fDesignation=((DesignationManager)(DesignationManager.getDesignationManager())).getDSDesignationByCode(designationCode);
EmployeeInterface blEmployee=new Employee();
blEmployee.setEmployeeId(employeeId);
blEmployee.setName(name);
blEmployee.setDesignation(fDesignation);
blEmployee.setDateOfBirth((Date)dateOfBirth.clone());
blEmployee.setGender(gender=='M'?GENDER.MALE:GENDER.FEMALE);
blEmployee.setIsIndian(isIndian);
blEmployee.setBasicSalary(basicSalary);
blEmployee.setPANNumber(panNumber);
blEmployee.setAadharCardNumber(aadharCardNumber);

employeeIdWiseEmployeeMap.put(employeeId.toUpperCase(),blEmployee);
panNumberWiseEmployeeMap.put(panNumber.toUpperCase(),blEmployee);
aadharCardNumberWiseEmployeeMap.put(aadharCardNumber.toUpperCase(),blEmployee);
Set<EmployeeInterface> list;
list=designationCodeWiseEmployeeMap.get(designationCode);
if(list==null)
{
list=new TreeSet<>();
}
list.add(blEmployee);
designationCodeWiseEmployeeMap.put(designationCode,list);
employeeSet.add(blEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)
{
blException.setGenericException("Employee required.");
throw blException;
}
String employeeId=employee.getEmployeeId();
if(employeeId==null) blException.addPropertyException("employeeId","Employee Id required");
else if((employeeId=employeeId.trim()).length()==0) blException.addPropertyException("employeeId","Employee Id required");
else if(employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase())==false) blException.addPropertyException("employeeId","Invalid employee id: "+employeeId);
String name=employee.getName();
if(name==null) blException.addPropertyException("name","Name required");
else if((name=name.trim()).length()==0) blException.addPropertyException("name","Name required");
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
if(designation==null)
{
blException.addPropertyException("designation","Designation required");
}
else
{
designationCode=designation.getCode();
if(designationCode<=0) blException.addPropertyException("designationCode","Designation code should not be negative or zero.");
else if((DesignationManager.getDesignationManager()).designationCodeExists(designationCode)==false) blException.addPropertyException("designationCode","Invalid designation code: "+designationCode);
}
Date dateOfBirth=employee.getDateOfBirth();
if(dateOfBirth==null) blException.addPropertyException("dateOfBirth","Date of birth required");
char gender=employee.getGender();
if(gender==' ') blException.addPropertyException("gender","Gender required");
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null) blException.addPropertyException("basicSalary","Basic salary required.");
else if(basicSalary.signum()<0) blException.addPropertyException("basicSalary","Basic salary can not be negative,");
String panNumber=employee.getPANNumber();
EmployeeInterface fEmployee;
if(panNumber==null) blException.addPropertyException("panNumber","PAN number required.");
else if((panNumber=panNumber.trim()).length()==0) blException.addPropertyException("panNumber","PAN number required.");
else if((fEmployee=panNumberWiseEmployeeMap.get(panNumber.toUpperCase()))!=null && fEmployee.getEmployeeId().equalsIgnoreCase(employeeId)==false) blException.addPropertyException("panNumber","PAN number exists against another employee.");
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null) blException.addPropertyException("aadharCardNumber","Aadhar number required.");
else if((aadharCardNumber=aadharCardNumber.trim()).length()==0) blException.addPropertyException("aadharCardNumber","Aadhar card number required.");
else if((fEmployee=aadharCardNumberWiseEmployeeMap.get(aadharCardNumber.toUpperCase()))!=null && fEmployee.getEmployeeId().equalsIgnoreCase(employeeId)==false) blException.addPropertyException("aadharCardNumber","Aadhar card number exists against another employee.");
if(blException.hasExceptions()) throw blException;
try
{
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
dlEmployee.setEmployeeId(employeeId);
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designationCode);
dlEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dlEmployee.setGender(gender=='M'?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);

//update in Data Layer
(new EmployeeDAO()).update(dlEmployee);
//if updated in Data Layer
Set<EmployeeInterface> list;
//Remove from D.S.
fEmployee=employeeIdWiseEmployeeMap.get(employeeId.toUpperCase());
employeeIdWiseEmployeeMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeeMap.remove(fEmployee.getPANNumber().toUpperCase());
aadharCardNumberWiseEmployeeMap.remove(fEmployee.getAadharCardNumber().toUpperCase());
list=designationCodeWiseEmployeeMap.get(fEmployee.getDesignation().getCode());
list.remove(fEmployee);
employeeSet.remove(fEmployee);

//Add new in D.S.
DesignationInterface fDesignation=((DesignationManager)(DesignationManager.getDesignationManager())).getDSDesignationByCode(designationCode);
EmployeeInterface blEmployee=new Employee();
blEmployee.setEmployeeId(employeeId);
blEmployee.setName(name);
blEmployee.setDesignation(fDesignation);
blEmployee.setDateOfBirth((Date)dateOfBirth.clone());
blEmployee.setGender(gender=='M'?GENDER.MALE:GENDER.FEMALE);
blEmployee.setIsIndian(isIndian);
blEmployee.setBasicSalary(basicSalary);
blEmployee.setPANNumber(panNumber);
blEmployee.setAadharCardNumber(aadharCardNumber);

employeeIdWiseEmployeeMap.put(employeeId.toUpperCase(),blEmployee);
panNumberWiseEmployeeMap.put(panNumber.toUpperCase(),blEmployee);
aadharCardNumberWiseEmployeeMap.put(aadharCardNumber.toUpperCase(),blEmployee);
list=designationCodeWiseEmployeeMap.get(designationCode);
if(list==null)
{
list=new TreeSet<>();
}
list.add(blEmployee);
designationCodeWiseEmployeeMap.put(designationCode,list);
employeeSet.add(blEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeEmployee(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null) blException.addPropertyException("employeeId","Employee Id required");
else if((employeeId=employeeId.trim()).length()==0) blException.addPropertyException("employeeId","Employee Id required");
else if(employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase())==false) blException.addPropertyException("employeeId","Invalid employee id: "+employeeId);
if(blException.hasExceptions()) throw blException;
try
{
//delete from Data Layer
(new EmployeeDAO()).delete(employeeId);
//if remove from Data Layer

Set<EmployeeInterface> list;
EmployeeInterface fEmployee;
//Remove from D.S.
fEmployee=employeeIdWiseEmployeeMap.get(employeeId.toUpperCase());
employeeIdWiseEmployeeMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeeMap.remove(fEmployee.getPANNumber().toUpperCase());
aadharCardNumberWiseEmployeeMap.remove(fEmployee.getAadharCardNumber().toUpperCase());
list=designationCodeWiseEmployeeMap.get(fEmployee.getDesignation().getCode());
list.remove(fEmployee);
employeeSet.remove(fEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public Set<EmployeeInterface> getEmployees()
{
Set<EmployeeInterface> employees;
employees=new TreeSet<>();
EmployeeInterface employee;
DesignationInterface designation;
DesignationInterface cloneDesignation;
for(EmployeeInterface emp:this.employeeSet)
{
employee=new Employee();
employee.setEmployeeId(emp.getEmployeeId());
employee.setName(emp.getName());
designation=emp.getDesignation();
cloneDesignation=new Designation();
cloneDesignation.setCode(designation.getCode());
cloneDesignation.setTitle(designation.getTitle());
employee.setDesignation(cloneDesignation);
employee.setDateOfBirth((Date)emp.getDateOfBirth().clone());
employee.setGender(emp.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(emp.getIsIndian());
employee.setBasicSalary(emp.getBasicSalary());
employee.setPANNumber(emp.getPANNumber());
employee.setAadharCardNumber(emp.getAadharCardNumber());
employees.add(employee);
}
return employees;
}
public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
{
BLException blException=new BLException();
if(designationCode<=0) blException.addPropertyException("designationCode","Invalid designation code: "+designationCode);
else if((DesignationManager.getDesignationManager()).designationCodeExists(designationCode)==false) blException.addPropertyException("designationCode","Invalid designation code: "+designationCode);
if(blException.hasExceptions()) throw blException;
Set<EmployeeInterface> employees;
employees=new TreeSet<>();
EmployeeInterface employee;
Set<EmployeeInterface> list=this.designationCodeWiseEmployeeMap.get(designationCode);
if(list==null) return employees;
DesignationInterface designation;
DesignationInterface cloneDesignation;
for(EmployeeInterface emp:list)
{
employee=new Employee();
employee.setEmployeeId(emp.getEmployeeId());
employee.setName(emp.getName());
designation=emp.getDesignation();
cloneDesignation=new Designation();
cloneDesignation.setCode(designation.getCode());
cloneDesignation.setTitle(designation.getTitle());
employee.setDesignation(cloneDesignation);
employee.setDateOfBirth((Date)emp.getDateOfBirth().clone());
employee.setGender(emp.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(emp.getIsIndian());
employee.setBasicSalary(emp.getBasicSalary());
employee.setPANNumber(emp.getPANNumber());
employee.setAadharCardNumber(emp.getAadharCardNumber());
employees.add(employee);
}
return employees;
}
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null) blException.addPropertyException("employeeId","Employee Id required");
else if((employeeId=employeeId.trim()).length()==0) blException.addPropertyException("employeeId","Employee Id required");
else if(employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase())==false) blException.addPropertyException("employeeId","Invalid employee id: "+employeeId);
if(blException.hasExceptions()) throw blException;
DesignationInterface designation;
DesignationInterface cloneDesignation;
EmployeeInterface employee=new Employee();
EmployeeInterface emp=this.employeeIdWiseEmployeeMap.get(employeeId.toUpperCase());
employee.setEmployeeId(emp.getEmployeeId());
employee.setName(emp.getName());
designation=emp.getDesignation();
cloneDesignation=new Designation();
cloneDesignation.setCode(designation.getCode());
cloneDesignation.setTitle(designation.getTitle());
employee.setDesignation(cloneDesignation);
employee.setDateOfBirth((Date)emp.getDateOfBirth().clone());
employee.setGender(emp.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(emp.getIsIndian());
employee.setBasicSalary(emp.getBasicSalary());
employee.setPANNumber(emp.getPANNumber());
employee.setAadharCardNumber(emp.getAadharCardNumber());
return employee;
}
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
BLException blException=new BLException();
if(panNumber==null) blException.addPropertyException("panNumber","PAN number required.");
else if((panNumber=panNumber.trim()).length()==0) blException.addPropertyException("panNumber","PAN number required.");
else if(panNumberWiseEmployeeMap.containsKey(panNumber.toUpperCase())==false) blException.addPropertyException("panNumber","PAN number does not exists.");
if(blException.hasExceptions()) throw blException;
DesignationInterface designation;
DesignationInterface cloneDesignation;
EmployeeInterface employee=new Employee();
EmployeeInterface emp=this.panNumberWiseEmployeeMap.get(panNumber.toUpperCase());
employee.setEmployeeId(emp.getEmployeeId());
employee.setName(emp.getName());
designation=emp.getDesignation();
cloneDesignation=new Designation();
cloneDesignation.setCode(designation.getCode());
cloneDesignation.setTitle(designation.getTitle());
employee.setDesignation(cloneDesignation);
employee.setDateOfBirth((Date)emp.getDateOfBirth().clone());
employee.setGender(emp.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(emp.getIsIndian());
employee.setBasicSalary(emp.getBasicSalary());
employee.setPANNumber(emp.getPANNumber());
employee.setAadharCardNumber(emp.getAadharCardNumber());
return employee;
}
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException=new BLException();
if(aadharCardNumber==null) blException.addPropertyException("aadharCardNumber","Aadhar number required.");
else if((aadharCardNumber=aadharCardNumber.trim()).length()==0) blException.addPropertyException("aadharCardNumber","Aadhar card number required.");
else if(aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber.toUpperCase())==false) blException.addPropertyException("aadharCardNumber","Aadhar card number does not exists.");
if(blException.hasExceptions()) throw blException;
DesignationInterface designation;
DesignationInterface cloneDesignation;
EmployeeInterface employee=new Employee();
EmployeeInterface emp=this.aadharCardNumberWiseEmployeeMap.get(aadharCardNumber.toUpperCase());
employee.setEmployeeId(emp.getEmployeeId());
employee.setName(emp.getName());
designation=emp.getDesignation();
cloneDesignation=new Designation();
cloneDesignation.setCode(designation.getCode());
cloneDesignation.setTitle(designation.getTitle());
employee.setDesignation(cloneDesignation);
employee.setDateOfBirth((Date)emp.getDateOfBirth().clone());
employee.setGender(emp.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(emp.getIsIndian());
employee.setBasicSalary(emp.getBasicSalary());
employee.setPANNumber(emp.getPANNumber());
employee.setAadharCardNumber(emp.getAadharCardNumber());
return employee;
}
public boolean employeeDesignationCodeAlloted(int designationCode) throws BLException
{
if(designationCode<=0 || DesignationManager.getDesignationManager().designationCodeExists(designationCode)==false)
{
BLException blException=new BLException();
blException.addPropertyException("designationCode","Invalid designation code: "+designationCode);
throw blException;
}
return this.designationCodeWiseEmployeeMap.containsKey(designationCode);
}
public boolean employeeEmployeeIdExists(String employeeId)
{
return this.employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase());
}
public boolean employeePANNumberExists(String panNumber)
{
return this.panNumberWiseEmployeeMap.containsKey(panNumber.toUpperCase());
}
public boolean employeeAadharCardNumberExists(String aadharCardNumber)
{
return this.aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber.toUpperCase());
}
public int getEmployeesCount()
{
return this.employeeSet.size();
}
public int getEmployeesDesignationCodeCount(int designationCode) throws BLException
{
Set<EmployeeInterface> list=this.designationCodeWiseEmployeeMap.get(designationCode);
if(list==null)
{
BLException blException=new BLException();
blException.addPropertyException("designationCode","Invalid designation code: "+designationCode);
throw blException;
}
return list.size();
}
}
