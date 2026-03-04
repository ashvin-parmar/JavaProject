
class User implements Comparable<User>
{
private String username;
private String password;
public User()
{
}
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
public int hashCode()
{
return this.username.hashCode();
}
public boolean equals(Object other)
{
if(!(other instanceof User)) return false;
User otherUser=(User)other;
return this.username.equals(otherUser.getUsername());
}
public int compareTo(User other)
{
return this.username.compareTo(other.getUsername());
}
}
