package com.ashvin.hr.nexus.bl.manager;

import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import java.util.*;
import com.ashvin.network.client.*;
import com.ashvin.network.common.*;
import com.ashvin.network.common.exceptions.*;

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
if(designation==null)
{
blException.setGenericException("Designation is null");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code!=0)
{
blException.addPropertyException("code","Code should be zero");
}
if(title=null)
{
blException.addPropertyException("title","Title required");
}
else if(title.trim().length==0)
{
blException.addPropertyException("title","Title required");
}
if(blException.hasException()) throw blException;
try
{
Request request=new Request();
//PROBLEM: Here, we want to pass String but here enums are passing
request.setManager(MANAGER.DESIGNATION);
request.setAction(DESIGNATION.ADD);
request.setArguments(designation);
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
designation.setCode(((Designation)response.getResult()).getCode());
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
} 
}
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required.");
throw blException;
}
int code=designation.getCode();
if(code<=0) blException.addPropertyException("code","Code should not be negative or zero.");
String title=designation.getTitle();
if(title==null) blException.addPropertyException("title","Title required.");
else if((title=title.trim()).length()==0) blException.addPropertyException("title","Title required.");
if(blException.hasExceptions()) throw blException;
try
{
Request request=new Request();
request.setManager("DesignationManager");
request.setAction("update");
request.setArguments(designation);
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
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
