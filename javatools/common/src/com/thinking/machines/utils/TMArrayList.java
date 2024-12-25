package com.thinking.machines.utils;

public class TMArrayList implements TMList
{
private int size;
private int collection[];
class TMArrayListIterator implements TMIterator
{
int index;
public TMArrayListIterator()
{
this.index=0;
}
public boolean hasNext()
{
return this.index!=TMArrayList.this.size;	//outside class this property used
}
public int next()
{
if(this.index==TMArrayList.this.size) throw new InvalidIteratorException("Iterator has no more element");
return TMArrayList.this.collection[this.index++];
}
}
public TMArrayListIterator iterator()
{
return new TMArrayListIterator();
}
public TMArrayList()
{
this.collection=new int[10];
this.size=0;
}
private void resizeArray()
{
int tmp[]=new int[this.size+10];
for(int i=0;i<this.size;i++) tmp[i]=this.collection[i];
this.collection=tmp;
}

public void add(int data)
{
if(this.size==collection.length)
{
resizeArray();
}
this.collection[this.size]=data;
this.size++;
}
public void add(int index,int data)
{
if(index<0 || index>this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
if(this.size==collection.length)
{
resizeArray();
}
for(int e=this.size;e>index;e--) this.collection[e]=this.collection[e-1];
this.collection[index]=data;
this.size++;
}
public void insert(int index,int data)
{
add(index,data);
}
public int remove(int index)
{
if(index<0 || index>=this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
int data;
data=this.collection[index];
int ep=this.size-2;
for(int e=index;e<=ep;e++)
{
this.collection[e]=this.collection[e+1];
}
this.size--;
return data;
}
public void update(int index,int data)
{
if(index<0 || index>=this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
this.collection[index]=data;
}
public void removeAll()
{
this.size=0;
}
public void clear()
{
this.size=0;
}
public int get(int index)
{
if(index<0 || index>this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
return this.collection[index];
}
public int size()
{
return this.size;
}

public void copyTo(TMList other)
{
other.clear();
for(int i=0;i<this.size;i++)
{
other.add(this.collection[i]);
}
}
public void copyFrom(TMList other)
{
this.clear();
for(int i=0;i<other.size();i++)
{
this.add(other.get(i));
}
}
public void appendTo(TMList other)
{
for(int i=0;i<this.size;i++)
{
other.add(this.collection[i]);
}
}
public void appendFrom(TMList other)
{
for(int i=0;i<other.size();i++)
{
this.add(other.get(i));
}
}
}
