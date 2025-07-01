package com.ashvin.hr.nexus.server;

import com.ashvin.hr.nexus.bl.interfaces.manager.*;
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
}
return response;
}
}
