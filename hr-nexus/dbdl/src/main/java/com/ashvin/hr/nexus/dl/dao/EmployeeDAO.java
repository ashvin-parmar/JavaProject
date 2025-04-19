package com.ashvin.hr.nexus.dl.dao;
import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;

import java.sql.*;

public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME="employee.dat";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId="";
String name=employeeDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Name length is zero");

int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code: "+designationCode);
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code: "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date of birth is null");
}
char gender=employeeDTO.getGender();
if(gender==' ') 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Invalid GENDER type or No GENDER type specify");
}
boolean isIndian=employeeDTO.isIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic salary is null");
}
if(basicSalary.signum()<0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic salary can not be negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number length is zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number length is zero");
}
try
{
//Pan Number existance checking
boolean panNumberExists=false;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

//Aadhar card existance checking
boolean aadharCardNumberExists=false;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

//Exception handling for pan number and aadhar card number
if(aadharCardNumberExists && panNumberExists) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number ("+panNumber+") and Aadhar Card Number ("+aadharCardNumber+") Exists.");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number ("+panNumber+") Exists.");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number ("+aadharCardNumber+") Exists.");
}

preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,basic_salary,gender,is_indian,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedCode=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
int start=1000000;
employeeDTO.setEmployeeId("A"+(generatedCode+start));
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("Employee id is null");
if(employeeId.length()<=1) throw new DAOException("Length of Employee Id. must greater than one");
int start=1000000;
int employeeIdInt=0;
try
{
employeeIdInt=Integer.parseInt(employeeId.substring(1));
}catch(NumberFormatException numberFormatException)
{
throw new DAOException("Invalid employee id. : "+employeeId);
}
String name=employeeDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Name length is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code: "+designationCode);
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code: "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date is null");
}
char gender=employeeDTO.getGender();
if(gender==' ') 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Invalid Gender Type: ("+gender+")");
}
boolean isIndian=employeeDTO.isIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic salary is null");
}
if(basicSalary.signum()<0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic salary can not be negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number length is zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number length is zero");
}

try
{
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,employeeIdInt-start);
//System.out.println("Employee id: "+employeeId+"\n EmployeeIdInt: "+employeeIdInt+"\n Employee id -start: "+(employeeIdInt-start));
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid employee id. : "+employeeId);
}
resultSet.close();
preparedStatement.close();

boolean panNumberExists=false;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=? and employee_id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,employeeIdInt-start);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

boolean aadharCardNumberExists=false;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=? and employee_id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,employeeIdInt-start);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

if(panNumberExists && aadharCardNumberExists) 
{
connection.close();
throw new DAOException("Employee with PAN number ("+panNumber+") and Aadhar card number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
connection.close();
throw new DAOException("Employee with PAN number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
connection.close();
throw new DAOException("Employee with Aadhar card number ("+aadharCardNumber+") exists");
}
preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,basic_salary=?,gender=?,is_indian=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,employeeIdInt-start);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Employee id is null");
if(employeeId.length()<=1) throw new DAOException("Length of Employee Id. must greater than one");
int start=1000000;
int employeeIdInt=0;
try
{
employeeIdInt=Integer.parseInt(employeeId.substring(1));
}catch(NumberFormatException numberFormatException)
{
throw new DAOException("Invalid employee id. : "+employeeId);
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,employeeIdInt-start);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid employee id. : "+employeeId);
}
resultSet.close();
preparedStatement.close();

preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,employeeIdInt-start);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
if(designationCode<=0) throw new DAOException("Invalid designation code: "+designationCode);
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
int start=1000000;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code: "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
Set<EmployeeDTOInterface> employees;
EmployeeDTOInterface employeeDTO;
employees=new TreeSet<>();
try
{
preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(start+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(resultSet.getDate("date_of_birth"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setGender(resultSet.getString("gender").charAt(0)=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
employees.add(employeeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
return employees;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
int start=1000000;
Set<EmployeeDTOInterface> employees;
EmployeeDTOInterface employeeDTO;
employees=new TreeSet<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement;
ResultSet resultSet;
statement=connection.createStatement();
resultSet=statement.executeQuery("select * from employee");
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(start+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(resultSet.getDate("date_of_birth"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setGender(resultSet.getString("gender").charAt(0)=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
employees.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
return employees;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Employee ID is null");
employeeId=employeeId.trim();
if(employeeId.length()<=1) throw new DAOException("Employee ID length must greater than one");
int start=1000000;
int employeeIdInt=0;
try
{
employeeIdInt=Integer.parseInt(employeeId.substring(1));
}catch(NumberFormatException numberFormatException)
{
throw new DAOException("Invalid employee id. : "+employeeId);
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,employeeIdInt-start);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid employee id. : "+employeeId);
}
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(start+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(resultSet.getDate("date_of_birth"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setGender(resultSet.getString("gender").charAt(0)=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("PAN Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("PAN Number length is zero");
int start=1000000;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid PAN Number: "+panNumber);
}
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(start+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(resultSet.getDate("date_of_birth"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setGender(resultSet.getString("gender").charAt(0)=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Aadhar Card Number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Aadhar Card Number length is zero");
int start=1000000;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Aadhar Card Number: "+aadharCardNumber);
}
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(start+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(resultSet.getDate("date_of_birth"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setGender(resultSet.getString("gender").charAt(0)=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean isDesignationAlloted(int designationCode) throws DAOException
{
//if(designationCode<=0) throw new DAOException("Invalid designation code: "+designationCode);
if(designationCode<=0) return false;
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
return false;
//throw new DAOException("Invalid designation code: "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
Set<EmployeeDTOInterface> employees;
EmployeeDTOInterface employeeDTO;
employees=new TreeSet<>();
try
{
preparedStatement=connection.prepareStatement("select gender from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
boolean isExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return isExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) return false;
employeeId=employeeId.trim();
if(employeeId.length()<=1) return false;
int start=1000000;
int employeeIdInt=0;
try
{
employeeIdInt=Integer.parseInt(employeeId.substring(1));
}catch(NumberFormatException numberFormatException)
{
return false;
//throw new DAOException("Invalid employee id. : "+employeeId);
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,employeeIdInt-start);
resultSet=preparedStatement.executeQuery();
boolean isExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return isExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) return false;
panNumber=panNumber.trim();
if(panNumber.length()==0) return false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
boolean isExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return isExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
boolean isExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return isExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public int getCount() throws DAOException
{
int count;
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select count(employee_id) as count from employee");
resultSet.next();
count=resultSet.getInt(1);
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public int getCountByDesignation(int designationCode) throws DAOException
{
if(designationCode<=0) return 0;
try
{
int count=0;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
return 0;
}
resultSet.close();
preparedStatement.close();

preparedStatement=connection.prepareStatement("select count(employee_id) as count from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
resultSet.next();
count=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
}
