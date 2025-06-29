package com.ashvin.hr.nexus.bl.manager;

public class Managers
{
private enum ManagerType{DESIGNATION,EMPLOYEE};
public static ManagerType DESIGNATION=ManagerType.DESIGNATION;
public static ManagerType EMPLOYEE=ManagerType.EMPLOYEE;
public static String getManagerType(ManagerType managerType)
{
if(managerType==DESIGNATION) return "DesignationManager";
if(managerType==EMPLOYEE) return "EmployeeManager";
return "";	//cannot reach here
}

static class Designation		//Inner class must be public if enum are declared inside
{
private enum ActionType{ADD,UPDATE,REMOVE,GET_BY_CODE,GET_BY_TITLE,GET_COUNT,CODE_EXISTS,TITLE_EXISTS,GET_DESIGNATIONS};
public static ActionType ADD=ActionType.ADD;
public static ActionType UPDATE=ActionType.UPDATE;
public static ActionType REMOVE=ActionType.REMOVE;
public static ActionType GET_BY_CODE=ActionType.GET_BY_CODE;
public static ActionType GET_BY_TITLE=ActionType.GET_BY_TITLE;
public static ActionType GET_COUNT=ActionType.GET_COUNT;
public static ActionType CODE_EXISTS=ActionType.CODE_EXISTS;
public static ActionType TITLE_EXISTS=ActionType.TITLE_EXISTS;
public static ActionType GET_DESIGNATIONS=ActionType.GET_DESIGNATIONS;
}
public static String getActionType(Designation.ActionType actionType)
{
if(actionType==Designation.ADD) return "addDesignation";		//Over here, ADD must be called with its class name because its static property.
if(actionType==Designation.UPDATE) return "updateDesignation";	
if(actionType==Designation.REMOVE) return "removeDesignation";
if(actionType==Designation.GET_BY_CODE) return "getDesignationByCode";
if(actionType==Designation.GET_BY_TITLE) return "getDesignationByTitle";
if(actionType==Designation.GET_COUNT) return "getDesignationCount";
if(actionType==Designation.CODE_EXISTS) return "designationCodeExists";
if(actionType==Designation.TITLE_EXISTS) return "designationTitleExists";
if(actionType==Designation.GET_DESIGNATIONS) return "getDesignations";
return "";		//Not reach to this point.
}
}
