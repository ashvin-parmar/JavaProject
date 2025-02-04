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
blException.setGenericException("designation is null"); 
throw blException;
}
int code=designation.getCode();
if(code!=0) blException.addPropertyException("code","Code should be zero");
String title=designation.getTitle();
if(title==null) 
{
blException.addPropertyException("title","Title required.");
title="";
}
else if((title=title.trim()).length()==0)  
{
blException.addPropertyException("title","Title required.");
}
else if(this.titleWiseDesignationMap.containsKey(title.toUpperCase())==true)
{
blException.addPropertyException("title","Designation: "+title+" exists.");
}
if(blException.hasExceptions()) throw blException;
try
{
DesignationDTOInterface dlDesignation=new DesignationDTO();
dlDesignation.setTitle(designation.getTitle());
(new DesignationDAO()).add(dlDesignation);
code=dlDesignation.getCode();
designation.setCode(code);
DesignationInterface blDesignation=new Designation();
blDesignation.setCode(code);
blDesignation.setTitle(designation.getTitle());
this.codeWiseDesignationMap.put(new Integer(blDesignation.getCode()),blDesignation);
this.titleWiseDesignationMap.put(blDesignation.getTitle().toUpperCase(),blDesignation);
this.designationSet.add(blDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
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
else if(this.codeWiseDesignationMap.containsKey(code)==false)
{
blException.addPropertyException("code","Invalid code: "+code);
}
if(blException.hasExceptions()) throw blException;
String title=designation.getTitle();
if(title==null) blException.addPropertyException("title","Title required.");
else if((title=title.trim()).length()==0) blException.addPropertyException("title","Title required.");
else if(this.titleWiseDesignationMap.containsKey(title.toUpperCase())==true)
{
DesignationInterface fDesignation=this.titleWiseDesignationMap.get(title.toUpperCase());
if(fDesignation.getCode()!=code) blException.addPropertyException("title","Designation: "+title+" exists against another code.");
}
if(blException.hasExceptions()) throw blException;
try
{
DesignationDTOInterface dlDesignation=new DesignationDTO();
dlDesignation.setCode(code);
dlDesignation.setTitle(title);
(new DesignationDAO()).update(dlDesignation);
DesignationInterface blDesignation=new Designation();
blDesignation.setCode(code);
blDesignation.setTitle(title);
DesignationInterface fDesignation;
fDesignation=this.codeWiseDesignationMap.get(code);
this.codeWiseDesignationMap.remove(code);
this.codeWiseDesignationMap.put(code,blDesignation);
this.titleWiseDesignationMap.remove(title.toUpperCase());
this.titleWiseDesignationMap.put(title.toUpperCase(),blDesignation);
this.designationSet.remove(fDesignation);
this.designationSet.add(blDesignation);
}
catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0) blException.addPropertyException("code","Code should not be negative or zero.");
else if(this.codeWiseDesignationMap.containsKey(code)==false)
{
blException.addPropertyException("code","Invalid code: "+code);
}
if(blException.hasExceptions()) throw blException;
try
{
(new DesignationDAO()).delete(code);
DesignationInterface fDesignation;
fDesignation=this.codeWiseDesignationMap.get(code);
this.codeWiseDesignationMap.remove(code);
this.titleWiseDesignationMap.remove(fDesignation.getTitle().toUpperCase());
this.designationSet.remove(fDesignation);
}
catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0) blException.addPropertyException("code","Code should not be negative or zero.");
else if(this.codeWiseDesignationMap.containsKey(code)==false)
{
blException.addPropertyException("code","Invalid code: "+code);
}
if(blException.hasExceptions()) throw blException;
DesignationInterface designation=new Designation();
DesignationInterface fDesignation=this.codeWiseDesignationMap.get(code);
designation.setCode(fDesignation.getCode());
designation.setTitle(fDesignation.getTitle());
return designation;
}
public DesignationInterface getDesignationByTitle(String title) throws BLException
{
BLException blException=new BLException();
if(title==null) blException.addPropertyException("title","Title required.");
else if((title=title.trim()).length()==0) blException.addPropertyException("title","Title required.");
else if(this.titleWiseDesignationMap.containsKey(title.toUpperCase())==false)
{
blException.addPropertyException("title","Invalid title: "+title);
}
if(blException.hasExceptions()) throw blException;
DesignationInterface designation=new Designation();
DesignationInterface fDesignation=this.titleWiseDesignationMap.get(title.toUpperCase());
designation.setCode(fDesignation.getCode());
designation.setTitle(fDesignation.getTitle());
return designation;
}
public int getDesignationCount() throws BLException
{
return this.designationSet.size();
}
public boolean designationCodeExists(int code) throws BLException
{
return this.codeWiseDesignationMap.containsKey(code);
}
public boolean designationTitleExists(String title) throws BLException
{
if(title==null) return false;
title=title.trim();
if(title.length()==0) return false;
return this.titleWiseDesignationMap.containsKey(title.toUpperCase());
}
public Set<DesignationInterface> getDesignations() throws BLException
{
Set<DesignationInterface> designations;
designations=new TreeSet<>();
DesignationInterface designationClone;
for(DesignationInterface designation:this.designationSet)
{
designationClone=new Designation();
designationClone.setCode(designation.getCode());
designationClone.setTitle(designation.getTitle());
designations.add(designationClone);
}
return designations;
}
}
