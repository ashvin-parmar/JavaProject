package com.ashvin.chess.server;

public class Member implements java.io.Serializable, Comparable<Member>
{
private String username;
private String password;
public void setUsername(String username)
{
this.username=username;
}
public String getUsername()
{
return this.username;
}
public void setPassword(String password)
{
this.password=password;
}
public String getPassword()
{
return this.password;
}
public boolean equals(Object obj)
{
if(!(obj instanceof Member)) return false;
Member member=(Member)obj;
if(member==null) return false;
if(this.username==null && member.username==null) return true;
if(this.username==null || member.username==null) return false;
return this.username.equals(member.username);
}
public int compareTo(Member other)
{
if(other==null) return 1;
if(this.username==null && other.username==null) return 0;
if(this.username==null) return -1;
if(other.username==null) return 1;
return this.username.compareTo(other.username);
}
public int hashCode()
{
return this.username.hashCode();
}
}
