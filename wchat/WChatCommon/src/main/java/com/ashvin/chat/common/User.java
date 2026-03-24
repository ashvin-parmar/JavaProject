package com.ashvin.chat.common;

import java.util.*;

record User(String username,String pswd) implements Comparable<User>
{
  public User {
    Objects.requireNonNull(name, "Name cannot be null");
    Objects.requireNonNull(pswd, "Password cannot be null");
  }
  private static final Comparator<User> COMPARATOR = Comparator
    .comparing(User::username,String.CASE_INSENSITIVE_ORDER)
    .thenComparing(User::pswd,String.CASE_INSENSITIVE_ORDER);

  public int compareTo(User other)
  {
    return COMPARATOR.compare(this,other);
  }
}
