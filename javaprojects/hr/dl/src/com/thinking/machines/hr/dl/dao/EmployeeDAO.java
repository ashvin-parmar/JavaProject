package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.math.*;
import java.io.*;

public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME="employee.dat";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
throw new DAOException("Not Yet Implemented");
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
