package com.thinking.machines.utils;
class TMLinkedListNode<T> 
{
T data;
TMLinkedListNode<T> next;
TMLinkedListNode()
{
this.data=null;
this.next=null;
}
TMLinkedListNode(T data)
{
this.data=data;
this.next=null;
}
}
public class TMLinkedList<T> implements TMList<T>
{
private int size;
private TMLinkedListNode<T> start;
private TMLinkedListNode<T> end;
public class TMLinkedListIterator<T> implements TMIterator<T>	//TMLinkedListIterator inner class
{
private TMLinkedListNode<T> node;
public TMLinkedListIterator(TMLinkedListNode<T> node)
{
this.node=node;	
}
public boolean hasNext()
{
return node!=null;
}
public T next()
{
if(node==null) throw new InvalidIteratorException("Iterator has no more element");
T data;
data=(T)node.data;
node=node.next;
return data;
}
}
public TMLinkedListIterator<T> iterator()
{
return new TMLinkedListIterator<T>(this.start);
}
public void forEach(TMListItemAcceptor<T> a)
{
if(a==null) return;
TMLinkedListNode<T> node;
node=this.start;
while(node!=null)
{
a.accept((T)node.data);
node=node.next;
}
}
public TMLinkedList()
{
this.size=0;
this.start=null;
this.end=null;
}
public void add(T data)
{
TMLinkedListNode<T> node=new TMLinkedListNode<T>(data);
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
public void add(int index,T data)
{
if(index<0 || index>this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
TMLinkedListNode<T> node=new TMLinkedListNode<T>(data);
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
TMLinkedListNode<T> t,f=null;
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
public void insert(int index,T data)
{
this.add(index,data);
}
public T remove(int index)
{
if(index<0 || index>=this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
TMLinkedListNode<T> t,f;
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
return (T)t.data;
}
public void update(int index,T data)
{
if(index<0 || index>=this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
TMLinkedListNode<T> t;
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

public T get(int index)
{
if(index<0 || index>=this.size) throw new IndexOutOfBoundsException("Invalid indedx: "+index);
TMLinkedListNode<T> t;
t=this.start;
for(int i=0;i<index;i++)
{
t=t.next;
}
return (T)t.data;
}
public int size()
{
return this.size;
}
public void copyTo(TMList<T> other)
{
other.clear();
TMIterator<T> iterator=this.iterator();
while(iterator.hasNext())
{
other.add((T)iterator.next());
}
}
public void copyFrom(TMList<T> other)
{
this.clear();
TMIterator<T> iterator=other.iterator();
while(iterator.hasNext())
{
this.add((T)iterator.next());
}
}
public void appendTo(TMList<T> other)
{
TMIterator<T> iterator=this.iterator();
while(iterator.hasNext())
{
other.add((T)iterator.next());
}
}
public void appendFrom(TMList<T> other)
{
TMIterator<T> iterator=other.iterator();
while(iterator.hasNext())
{
this.add((T)iterator.next());
}
}
}
