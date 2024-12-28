package com.thinking.machines.utils;

public class TMArrayList<T> implements TMList<T>
{
private int size;
private Object collection[];
class TMArrayListIterator<T> implements TMIterator<T>
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
public T next()
{
if(this.index==TMArrayList.this.size) throw new InvalidIteratorException("Iterator has no more element");
return (T)TMArrayList.this.collection[this.index++];
}
}
public TMArrayListIterator<T> iterator()
{
return new TMArrayListIterator<T>();
}
public void forEach(TMListItemAcceptor<T> a)
{
if(a==null) return;
for(int i=0;i<this.size;i++)
{
a.accept((T)this.collection[i]);
}
}
public TMArrayList()
{
this.collection=new Object[10];
this.size=0;
}
private void resizeArray()
{
Object tmp[]=new Object[this.size+10];
for(int i=0;i<this.size;i++) tmp[i]=this.collection[i];
this.collection=tmp;
}

public void add(T data)
{
if(this.size==collection.length)
{
resizeArray();
}
this.collection[this.size]=data;
this.size++;
}
public void add(int index,T data)
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
public void insert(int index,T data)
{
add(index,data);
}
public T remove(int index)
{
if(index<0 || index>=this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
T data;
data=(T)this.collection[index];
int ep=this.size-2;
for(int e=index;e<=ep;e++)
{
this.collection[e]=this.collection[e+1];
}
this.size--;
return data;
}
public void update(int index,T data)
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
public T get(int index)
{
if(index<0 || index>this.size) throw new IndexOutOfBoundsException("Invalid index: "+index);
return (T)this.collection[index];
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
other.add(iterator.next());
}
}
public void copyFrom(TMList<T> other)
{
this.clear();
TMIterator<T> iterator=other.iterator();
while(iterator.hasNext())
{
this.add(iterator.next());
}
}
public void appendTo(TMList<T> other)
{
TMIterator<T> iterator=this.iterator();
while(iterator.hasNext())
{
other.add(iterator.next());
}
}
public void appendFrom(TMList<T> other)
{
TMIterator<T> iterator=other.iterator();
while(iterator.hasNext())
{
this.add(iterator.next());
}
}
}
