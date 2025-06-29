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
if(title==null)
{
blException.addPropertyException("title","Title required");
}
else if(title.trim().length()==0)
{
blException.addPropertyException("title","Title required");
}
if(blException.hasExceptions()) throw blException;
try
{
Request request=new Request();
//PROBLEM: Here, we want to pass String but here enums are passing
//SOLUTION: Created a Manager class which have functionalities as getManagerType and getActionType --> which provide specific values against given arguments. 
//Structure is not final yet.
request.setManager(Manager.getManagerType(Manager.MANAGER.DESIGNATION));
request.setAction(Manager.getActionType(Manager.DESIGNATION.ADD));
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
request.setManager(Manager.getManagerType(Manager.MANAGER.DESIGNATION));
request.setAction(Manager.getActionType(Manager.DESIGNATION.UPDATE));
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
if(code<=0) blException.addPropertyException("code","Code should not be negative or zero.");
if(blException.hasExceptions()) throw blException;
try
{
Request request=new Request();
request.setManager(Manager.getManagerType(Manager.MANAGER.DESIGNATION));
request.setAction(Manager.getActionType(Manager.DESIGNATION.REMOVE));
request.setArguments(code);
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
public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0)
{
blException.addPropertyException("code","Code should not be negative or zero.");
throw blException;
}
try
{
Request request=new Request();
request.setManager(Manager.getManagerType(Manager.MANAGER.DESIGNATION));
request.setAction(Manager.getActionType(Manager.DESIGNATION.GET_BY_CODE));
request.setArguments(code);
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
DesignationInterface designation=(DesignationInterface)response.getResult();
return designation;
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public DesignationInterface getDesignationByTitle(String title) throws BLException
{
BLException blException=new BLException();
if(title==null) blException.addPropertyException("title","Title required.");
else if((title=title.trim()).length()==0) blException.addPropertyException("title","Title required.");
if(blException.hasExceptions()) throw blException;
try
{
Request request=new Request();
request.setManager(Manager.getManagerType(Manager.MANAGER.DESIGNATION));
request.setAction(Manager.getActionType(Manager.DESIGNATION.GET_BY_TITLE));
request.setArguments(title);
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
DesignationInterface designation=(DesignationInterface)response.getResult();
return designation;
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public int getDesignationCount() throws BLException	 //pending throws
{
try
{
Request request=new Request();
request.setManager(Manager.getManagerType(Manager.MANAGER.DESIGNATION));
request.setAction(Manager.getActionType(Manager.DESIGNATION.GET_COUNT));
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
return 0;
}
int count=(Integer)response.getResult();
return count;
}catch(NetworkException networkException)
{
BLException blException=new BLException();
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public boolean designationCodeExists(int code) throws BLException
{
try
{
Request request=new Request();
request.setManager(Manager.getManagerType(Manager.MANAGER.DESIGNATION));
request.setAction(Manager.getActionType(Manager.DESIGNATION.CODE_EXISTS));
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
return false;
}
boolean exists=(Boolean)response.getResult();
return exists;
}catch(NetworkException networkException)
{
BLException blException=new BLException();
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public boolean designationTitleExists(String title) throws BLException
{
try
{
Request request=new Request();
request.setManager(Manager.getManagerType(Manager.MANAGER.DESIGNATION));
request.setAction(Manager.getActionType(Manager.DESIGNATION.TITLE_EXISTS));
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
return false;
}
boolean exists=(Boolean)response.getResult();
return exists;
}catch(NetworkException networkException)
{
BLException blException=new BLException();
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public Set<DesignationInterface> getDesignations() throws BLException
{
try
{
Request request=new Request();
request.setManager(Manager.getManagerType(Manager.MANAGER.DESIGNATION));
request.setAction(Manager.getActionType(Manager.DESIGNATION.GET_ALL));
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
return null;
}
Set<DesignationInterface> designations=(Set<DesignationInterface>)response.getResult();
return designations;
}catch(NetworkException networkException)
{
BLException blException=new BLException();
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
}
