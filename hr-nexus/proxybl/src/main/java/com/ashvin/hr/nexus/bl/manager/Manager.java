package com.ashvin.hr.nexus.bl.manager;

public class Manager
{
public enum MANAGER{DESIGNATION,EMPLOYEE};
public enum DESIGNATION{ADD,UPDATE,REMOVE,GET_BY_CODE,GET_BY_TITLE,GET_COUNT,CODE_EXISTS,TITLE_EXISTS,GET_ALL};
public static String getManagerType(MANAGER manager)
{
if(manager==MANAGER.DESIGNATION) return "DesignationManager";
else return "EmployeeManager";
}
public static String getActionType(DESIGNATION designation)
{
if(designation==DESIGNATION.ADD) return "addDesignation";
else if(designation==DESIGNATION.UPDATE) return "updateDesignation";
else if(designation==DESIGNATION.REMOVE) return "removeDesignation";
else if(designation==DESIGNATION.GET_BY_CODE) return "getDesignationByCode";
else if(designation==DESIGNATION.GET_BY_TITLE) return "getDesignationByTitle";
else if(designation==DESIGNATION.GET_COUNT) return "getDesignationCount";
else if(designation==DESIGNATION.CODE_EXISTS) return "designationCodeExists";
else if(designation==DESIGNATION.TITLE_EXISTS) return "designationTitleExists";
else return "getAll";
//Here, We can also add NONE enum for passing nothing
}
}
