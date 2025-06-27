package com.ashvin.hr.nexus.bl.manager;

import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
static private DesignationManager designationManager;
private DesignationManager() throws BLException
{
designationManager=null;
}
public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager==null) designationManager=new DesignationManager();
return designationManager;
}
public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public DesignationInterface getDesignationByTitle(String title) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public int getDesignationCount()
{
return 0;
}
public boolean designationCodeExists(int code)
{
return false;
}
public boolean designationTitleExists(String title)
{
return false;
}
public Set<DesignationInterface> getDesignations()
{
return null;
}
}
