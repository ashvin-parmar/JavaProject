package com.ashvin.hr.nexus.dl.interfaces.dao;

import java.io.*;
import java.util.*;
import com.ashvin.hr.nexus.dl.interfaces.dto.*;
import com.ashvin.hr.nexus.dl.exceptions.*;
public interface DesignationDAOInterface
{
public void add(DesignationDTOInterface designationDTO) throws DAOException;
public void update(DesignationDTOInterface designationDTO) throws DAOException;
public void delete(int code) throws DAOException;
public Set<DesignationDTOInterface> getAll() throws DAOException;
public DesignationDTOInterface getByCode(int code) throws DAOException;
public DesignationDTOInterface getByTitle(String title) throws DAOException;
public boolean codeExist(int coed) throws DAOException;
public boolean titleExist(String title) throws DAOException;
public int getCount() throws DAOException;
}
