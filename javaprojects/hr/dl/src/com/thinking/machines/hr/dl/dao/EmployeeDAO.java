package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.math.*;
import java.io.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME="employee.dat";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
//Validations
String employeeIdString;
String name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Name length equals to zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code: "+designationCode);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean isDesignationCodeValid=designationDAO.codeExist(designationCode);
if(isDesignationCodeValid==false) throw new DAOException("Invalid designation code");
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of birth is null");
char gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.isIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("Basic salary os null");
if(basicSalary.signum()==-1) throw new DAOException("Basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("PAN Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of PAN Number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of Aadhar card number is zero");
try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
int lastGeneratedCode=10000000;
String lastGeneratedCodeString="";
int numberOfRecords=0;
String numberOfRecordsString="";
if(randomAccessFile.length()==0)
{
lastGeneratedCodeString=String.format("%-10s","10000000");
randomAccessFile.writeBytes(lastGeneratedCodeString+"\n");
numberOfRecordsString=String.format("%-10s","0");
randomAccessFile.writeBytes(numberOfRecordsString+"\n");
}
else
{
lastGeneratedCodeString=randomAccessFile.readLine();
lastGeneratedCode=Integer.parseInt(lastGeneratedCodeString.trim());
numberOfRecordsString=randomAccessFile.readLine();
numberOfRecords=Integer.parseInt(numberOfRecordsString.trim());
}
boolean panNumberExists, aadharCardNumberExists;
panNumberExists=false;
aadharCardNumberExists=false;
int i;
String fPANNumber="";
String fAadharCardNumber="";
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(i=0;i<7;i++) randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(panNumberExists==false && fPANNumber.equalsIgnoreCase(panNumber)) panNumberExists=true;
if(aadharCardNumberExists==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)) aadharCardNumberExists=true;
if(aadharCardNumberExists && panNumberExists) break;
}
if(panNumberExists && aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN Number ("+panNumber+") and Aadhar Card Number ("+aadharCardNumber+")are exists");
}
if(panNumberExists) 
{
randomAccessFile.close();
throw new DAOException("PAN Number ("+panNumber+")");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Aadhar card number ("+aadharCardNumber+") Exists");
}
lastGeneratedCode++;
numberOfRecords++;
employeeIdString="A"+String.format("%-10d",lastGeneratedCode);
System.out.println("Last Employee Id: "+employeeIdString);
randomAccessFile.writeBytes(employeeIdString+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(String.valueOf(designationCode)+"\n");
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(String.valueOf(isIndian)+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
randomAccessFile.seek(0);
lastGeneratedCodeString=String.format("%-10d",lastGeneratedCode);
numberOfRecordsString=String.format("%-10d",numberOfRecords);
randomAccessFile.writeBytes(lastGeneratedCodeString+"\n");
randomAccessFile.writeBytes(numberOfRecordsString+"\n");
randomAccessFile.close();
employeeDTO.setEmployeeId(employeeIdString);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public void delete(String employeeId) throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public boolean isDesignationAlloted(int designationCode) throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public boolean panNumberExists(String panNumber) throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public int getCount() throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
public int getCountByDesignation(int designationCode) throws DAOException
{
throw new DAOException("Not Yet Implemented");
}
}
