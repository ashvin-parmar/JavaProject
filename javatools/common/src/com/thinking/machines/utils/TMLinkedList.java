package com.thinking.machines.utils;

class TMLinkedListNode 
{
int data;
TMLinkedListNode next;
TMLinkedListNode()
{
this.data=0;
this.next=null;
}
TMLinkedListNode(int data)
{
this.data=data;
this.next=null;
}
}
public class TMLinkedList implements TMList
{
private int size;
private TMLinkedListNode start;
private TMLinkedListNode end;
public TMLinkedList()
{
this.size=0;
this.start=null;
this.end=null;
}
public void add(int data)
{
TMLinkedListNode node=new TMLinkedListNode(data);
if(this.start==null)
{
this.start=node;
this.end=node;
}
else
{
this.end.next=node;
this.end=node;
}
this.size++;
}
public void add(int index,int data)
{
if(index<0 || index>this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
TMLinkedListNode node=new TMLinkedListNode(data);
if(this.start==null)
{
this.start=this.end=node;
}else
if(index==0)
{
node.next=this.start;
this.start=node;
}else
if(index==this.size)
{
this.end.next=node;
this.end=node;
}
else
{
TMLinkedListNode t,f=null;
t=this.start;
for(int i=0;i<index;i++)
{
f=t;
t=t.next;
}
f.next=node;
node.next=t;
}
this.size++;
}
public void insert(int index,int data)
{
this.add(index,data);
}
public int remove(int index)
{
if(index<0 || index>=this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
TMLinkedListNode t,f;
f=null;
t=this.start;
for(int i=0;i<index;i++)
{
f=t;
t=t.next;
}
if(this.start==this.end)
{
this.start=this.end=null;
}else
if(t==this.start)
{
this.start=this.start.next;
}else
if(t==this.end)
{
f.next=null;
this.end=f;
}else
{
f.next=t.next;
}
this.size--;
return t.data;
}
public void update(int index,int data)
{
if(index<0 || index>=this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
TMLinkedListNode t;
t=this.start;
for(int i=0;i<index;i++)
{
t=t.next;
}
t.data=data;
}

public void removeAll()
{
this.clear();
}
public void clear()
{
this.size=0;
this.start=null;
this.end=null;
}

public int get(int index)
{
if(index<0 || index>=this.size) throw new IndexOutOfBoundsException("Invalid indedx: "+index);
TMLinkedListNode t;
t=this.start;
for(int i=0;i<index;i++)
{
t=t.next;
}
return t.data;
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
other.add(this.get(i));
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
other.add(this.get(i));
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
