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
title=designationDTO.getTitle();
if(title==null) throw new DAOException("Designation is null");
title=title.trim();
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
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
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
File file=new File(DESIGNATION_FILE);
if(file.exists()==false) throw new DAOException("Invalid code: "+code);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode=0;
String fTitle="";
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine().trim();
if(code==fCode)
{
if(fTitle.equalsIgnoreCase(title)) 
{
randomAccessFile.close();
//throw new DAOException("Same title : "+title+" already exist against given code.");
return;
}
found=true;
}
if(fTitle.equalsIgnoreCase(title))
{
randomAccessFile.close();
throw new DAOException("Designation: "+title+" Exist");
}
}
if(!found) 
{
randomAccessFile.close();
throw new DAOException("Invalide code: "+code);
}
//System.out.println("Previous Title: "+fTitle);
//System.out.println("New Title: "+title);
randomAccessFile.seek(0);
File tmpFile=new File("tmp.tmp");
tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode==code)
{
tmpRandomAccessFile.writeBytes(String.valueOf(code));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(title);
tmpRandomAccessFile.writeBytes("\n");
}
else
{
tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(fTitle);
tmpRandomAccessFile.writeBytes("\n");
}
}
tmpRandomAccessFile.seek(0);
randomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void delete(int code) throws DAOException
{
throw new DAOException("Not yer implemented");
}
public Set<DesignationDTOInterface> getAll() throws DAOException
{
try
{
Set<DesignationDTOInterface> treeSet;
treeSet=new TreeSet<>();
DesignationDTOInterface designationDTO;
File file=new File(DESIGNATION_FILE);
if(file.exists()==false) return treeSet;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return treeSet;
}
int code=0;
String title="";
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
code=Integer.parseInt(randomAccessFile.readLine());
title=randomAccessFile.readLine();
designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
designationDTO.setCode(code);
treeSet.add(designationDTO);
}
return treeSet;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
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
try
{
File file=new File(DESIGNATION_FILE);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int count=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return count;
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}
