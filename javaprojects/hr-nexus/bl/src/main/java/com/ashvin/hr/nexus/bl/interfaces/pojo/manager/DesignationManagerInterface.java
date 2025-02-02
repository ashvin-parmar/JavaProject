package com.ashvin.hr.nexus.bl.interfaces.pojo.manager;

import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import java.util.*;
public interface DesignationManagerInterface
{
public void add(DesignationInterface designation) throws BLException;
public void update(DesignationInterface designation) throws BLException;
public void remove(int code) throws BLException;
public Set<DesignationInterface> getByCode(int code) throws BLException;
public Set<DesignationInterface> getByTitle(String title) throws BLException;
public int getDesignationCount() throws BLException;
public boolean codeExists(int code) throws BLException;
public boolean titleExists(String title) throws BLException;
public Set<DesignationInterface> getDesignations() throws BLException;
}
