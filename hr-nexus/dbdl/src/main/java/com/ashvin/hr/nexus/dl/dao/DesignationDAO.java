package com.ashvin.hr.nexus.dl.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
public class DesignationDAO implements DesignationDAOInterface
{
static final private String DESIGNATION_FILE="designation.dat";
public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation is null");
String title;
title=designationDTO.getTitle();
if(title==null) throw new DAOException("Designation is null");
title=title.trim();
if(title.length()==0) throw new DAOException("Title length is zero");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation "+title+" exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into designation (title) values(?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int code=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
designationDTO.setCode(code);
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public void update(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation is null");
int code;
code=designationDTO.getCode();
if(code<=0) throw new DAOException("Invalid code : "+code);
String title;
title=designationDTO.getTitle();
if(title==null) throw new DAOException("Designation is null");
title=title.trim();
if(title.length()==0) throw new DAOException("Designation length is zero");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code: "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select code from designation where title=?");
preparedStatement.setString(1,title);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
int fCode=resultSet.getInt("code");
if(fCode!=code)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation "+title+" exists");
}
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update designation set title=? where code=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public void delete(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code: "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from designation where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public Set<DesignationDTOInterface> getAll() throws DAOException
{
try
{
Set<DesignationDTOInterface> designations;
designations=new TreeSet<>();
DesignationDTOInterface designationDTO;
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from designation");
while(resultSet.next())
{
designationDTO=new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title").trim());
designations.add(designationDTO);
}
resultSet.close();
statement.close();
connection.close();
return designations;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code: "+code);
}
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title==null || title.trim().length()==0) throw new DAOException("Invalid title: "+title);
title=title.trim();
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid title: "+title);
}
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public boolean codeExist(int code) throws DAOException
{
if(code<=0) return false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
boolean ans=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return ans;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public boolean titleExist(String title) throws DAOException
{
if(title==null || title.trim().length()==0) return false;
title=title.trim();
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
boolean ans=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return ans;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public int getCount() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select count(code) as count from designation");
if(resultSet.next())
{
int count=resultSet.getInt("count");
resultSet.close();
statement.close();
connection.close();
return count;
}
resultSet.close();
statement.close();
connection.close();
return 0;
}
catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}
