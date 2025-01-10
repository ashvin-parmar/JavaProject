package com.thinking.machines.hr.dl.dao;
import java.io.*;
import java.util.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
public class DesignationDAO implements DesignationDAOInterface
{
static final private String DESIGNATION_FILE="designation.dat";
public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation is null");
String title;
title=designationDTO.getTitle().trim();
if(title==null) throw new DAOException("Designation is null");
if(title.length()==0) throw new DAOException("Title length is zero");
try
{
File file=new File(DESIGNATION_FILE);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
int lastGeneratedCode=0;
String lastGeneratedCodeString="";
int totalRecords=0;
String totalRecordsString="";
if(randomAccessFile.length()==0)
{
lastGeneratedCodeString="0";
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
totalRecordsString="0";
while(totalRecordsString.length()<10) totalRecordsString+=" ";
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(totalRecordsString);
randomAccessFile.writeBytes("\n");
}
else
{
lastGeneratedCodeString=randomAccessFile.readLine().trim();
lastGeneratedCode=Integer.parseInt(lastGeneratedCodeString);
totalRecordsString=randomAccessFile.readLine().trim();
totalRecords=Integer.parseInt(totalRecordsString);
}
int fCode=0;
String fTitle="";
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title)) 
{
randomAccessFile.close();
throw new DAOException("Title : "+title+" Exist.");
}
}
int code=lastGeneratedCode+1;
randomAccessFile.writeBytes(String.valueOf(code));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(title);
randomAccessFile.writeBytes("\n");
lastGeneratedCode++;
totalRecords++;
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
totalRecordsString=String.valueOf(totalRecords);
while(totalRecordsString.length()<10) totalRecordsString+=" ";
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(totalRecordsString);
randomAccessFile.writeBytes("\n");
designationDTO.setCode(code);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(DesignationDTOInterface designationDTO) throws DAOException
{
throw new DAOException("Not yer implemented");
}
public void delete(int code) throws DAOException
{
throw new DAOException("Not yer implemented");
}
public TreeSet<DesignationDTOInterface> getAll() throws DAOException
{
throw new DAOException("Not yer implemented");
}
public DesignationDTOInterface getByCode(int code) throws DAOException
{
throw new DAOException("Not yer implemented");
}
public DesignationDTOInterface getByTitle(String title) throws DAOException
{
throw new DAOException("Not yer implemented");
}
public boolean codeExist(int coed) throws DAOException
{
throw new DAOException("Not yer implemented");
}
public boolean titleExist(String title) throws DAOException
{
throw new DAOException("Not yer implemented");
}
public int getCount() throws DAOException
{
throw new DAOException("Not yer implemented");
}
}
