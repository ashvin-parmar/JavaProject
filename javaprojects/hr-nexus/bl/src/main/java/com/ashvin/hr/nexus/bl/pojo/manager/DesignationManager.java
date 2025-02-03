package com.ashvin.hr.nexus.bl.pojo.manager;

import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.dl.exceptions.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.interfaces.dao.*;
import com.ashvin.hr.nexus.dl.dto.*;
import com.ashvin.hr.nexus.dl.dao.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationMap;
private Map<String,DesignationInterface> titleWiseDesignationMap;
private Set<DesignationInterface> designationSet;
private static DesignationManagerInterface designationManager=null;
private DesignationManager() throws BLException
{
populateDataStructures();
}
private void populateDataStructures() throws BLException
{
this.codeWiseDesignationMap=new HashMap<>();
this.titleWiseDesignationMap=new HashMap<>();
this.designationSet=new TreeSet<>();
try
{
DesignationInterface designation;
Set<DesignationDTOInterface> dlDesignations=(new DesignationDAO()).getAll();
for(DesignationDTOInterface dlDesignation:dlDesignations)
{
designation=new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
this.codeWiseDesignationMap.put(new Integer(designation.getCode()),designation);
this.titleWiseDesignationMap.put(designation.getTitle().toUpperCase(),designation);
this.designationSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public DesignationManagerInterface getDesignationManager() throws BLException
{
if(this.designationManager==null) this.designationManager=new DesignationManager();
return this.designationManager;
}
public void add(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public void update(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public void remove(int code) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public Set<DesignationInterface> getByCode(int code) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public Set<DesignationInterface> getByTitle(String title) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public int getDesignationCount() throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public boolean codeExists(int code) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public boolean titleExists(String title) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public Set<DesignationInterface> getDesignations() throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
}
