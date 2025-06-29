package com.ashvin.hr.nexus.bl.manager;

public class Manager
{
public enum MANAGER{DESIGNATION,EMPLOYEE};
public enum DESIGNATION{ADD,UPDATE};
public static String getManagerType(MANAGER manager)
{
if(manager==MANAGER.DESIGNATION) return "DesignationManager";
else return "EmployeeManager";
}
public static String getActionType(DESIGNATION designation)
{
if(designation==DESIGNATION.ADD) return "add";
else if(designation==DESIGNATION.UPDATE) return "update";
else return "none";
//More for other functionalities
}
}
