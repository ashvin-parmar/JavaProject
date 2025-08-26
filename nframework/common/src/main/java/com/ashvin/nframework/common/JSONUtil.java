package com.ashvin.nframework.common;
import com.google.gson.*;
public class JSONUtil
{
private JSONUtil(){}
public static String toJSON(java.io.Serializable serializableClass)
{
try
{
Gson gson=new GsonBuilder().registerTypeHierarchyAdapter(Throwable.class,new ThrowableAdapter()).create();
return gson.toJson(serializableClass);
}catch(Exception exception)
{
return "{}";
}
}
public static <T> T fromJSON(String jsonString,Class<T> c)
{
try
{
Gson gson=new GsonBuilder().registerTypeHierarchyAdapter(Throwable.class,new ThrowableAdapter()).create();
return gson.fromJson(jsonString,c);
}catch(Exception exception)
{
return null;
}
}
}
