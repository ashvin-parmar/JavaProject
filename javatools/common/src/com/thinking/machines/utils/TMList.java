package com.thinking.machines.utils;

public interface TMList
{
public void add(int data);
public void add(int index,int data);
public void insert(int index,int data);
public int remove(int index);
public void update(int index,int data);

public void removeAll();
public void clear();

public int get(int index);
public int size();

public void copyTo(TMList other);
public void copyFrom(TMList other);

public void appendTo(TMList other);
public void appendFrom(TMList other);
}
