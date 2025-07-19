package com.ashvin.hr.nexus.server;

import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;
import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.network.server.*;
import com.ashvin.network.common.*;
public class RequestHandler implements RequestHandlerInterface
{
private DesignationManagerInterface designationManager;
public RequestHandler()
{
try
{
this.designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{
// do nothing
}
}
public Response process(Request request)
{
Response response=new Response();
String manager=request.getManager();
String action=request.getAction();
Object arguments[]=request.getArguments();
if(manager.equals("DesignationManager"))
{
if(action.equals("getDesignations"))
{
Object designations=designationManager.getDesignations();
if(designations==null) 
{
return null;
}
response.setResult(designations);
response.setSuccess(true);
response.setException(null);
}
if(action.equals("addDesignation"))
{
try
{
DesignationInterface designation=((DesignationInterface)arguments[0]);
designationManager.addDesignation(designation);
response.setResult(designation);
response.setSuccess(true);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException);
}
}
if(action.equals("updateDesignation"))
{
try
{
designationManager.updateDesignation((DesignationInterface)arguments[0]);
response.setSuccess(true);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException);
}
}
if(action.equals("removeDesignation"))
{
try
{
designationManager.removeDesignation((int)arguments[0]);
response.setSuccess(true);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException);
}
}
if(action.equals("getDesignationByCode"))
{
try
{
DesignationInterface designation=designationManager.getDesignationByCode((Integer)arguments[0]);
response.setResult(designation);
response.setSuccess(true);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException);
}
}
if(action.equals("getDesignationByTitle"))
{
try
{
DesignationInterface designation=designationManager.getDesignationByTitle((String)arguments[0]);
response.setResult(designation);
response.setSuccess(true);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException);
}
}
if(action.equals("getDesignationCount"))
{
Integer count=designationManager.getDesignationCount();
response.setResult(count);
response.setSuccess(true);
response.setException(null);
}
if(action.equals("designationCodeExists"))
{
Boolean exists=designationManager.designationCodeExists((Integer)arguments[0]);
response.setResult(exists);
response.setSuccess(true);
response.setException(null);
}
if(action.equals("designationTitleExists"))
{
Boolean exists=designationManager.designationTitleExists((String)arguments[0]);
response.setResult(exists);
response.setSuccess(true);
response.setException(null);
}
}
return response;
}
}
