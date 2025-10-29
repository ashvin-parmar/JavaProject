## Problem 1: 


Object result=method.invoke(serviceObject,request.getArguments());


Over here -> 

run_server.sh 
Exception in thread "Thread-1" java.lang.IllegalArgumentException: argument type mismatch
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:107)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at com.ashvin.nframework.server.RequestProcessor.run(RequestProcessor.java:116)


this error occured reason 

i have used a class which has enum values, Now those enum values are not invocked by the invoke method because at the Network layer those enum are transferred to String values | 

i have used google.gson
how i can manage those deserialization and serialization of those enums.



## Problem 2: 

class com.google.gson.internal.LinkedTreeMap cannot be cast to class State (com.google.gson.internal.LinkedTreeMap and State are in unnamed module of loader 'app')


when data arrived from execute, those data are come as string and there are casting issue generated because Its cannot applicable on Object -> State
