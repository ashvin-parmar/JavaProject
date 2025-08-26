package com.ashvin.nframework.common;
import com.google.gson.*;
import java.lang.reflect.*;
import java.io.*;
class ThrowableAdapter implements JsonSerializer<Throwable>,JsonDeserializer<Throwable>
{
public JsonElement serialize(Throwable source,Type sourceType,JsonSerializationContext contex)
{
JsonObject obj=new JsonObject();
obj.addProperty("type",source.getClass().getName());
obj.addProperty("message",source.getMessage());
return obj;
}
public Throwable deserialize(JsonElement jsonElem,Type jsonType,JsonDeserializationContext context) throws JsonParseException
{
JsonObject obj=jsonElem.getAsJsonObject();
String type=obj.get("type").getAsString();
String message=obj.get("message").getAsString();
try
{
Class<?> c=Class.forName(type);
return (Throwable) c.getConstructor(String.class).newInstance(message);
}catch(Exception e)
{
//System.out.println("message: "+message);
return new Exception(message);
}
}
}
