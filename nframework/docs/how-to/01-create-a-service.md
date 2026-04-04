# How-To: Create and Register a Service

> **Assumes:** You have built the framework JARs and understand the basics (see [Getting Started](../tutorials/01-getting-started.md)).

---

## 1. Annotate Your Class with `@Path`

Every service class must have a `@Path` annotation at the class level. This sets the **base path** for all methods in that class.

```java
import com.ashvin.nframework.server.annotations.Path;

@Path("/myservice")
public class MyService {
    // ...
}
```

---

## 2. Annotate Methods with `@Path`

Each method you want to expose as a remote endpoint must also carry `@Path`. The full route is: **class path + method path**.

```java
@Path("/myservice")
public class MyService {

    @Path("/doSomething")
    public String doSomething(String input) {
        return "Processed: " + input;
    }

    @Path("/add")
    public int add(int a, int b) {
        return a + b;
    }
}
```

Routes registered: `/myservice/doSomething`, `/myservice/add`.

---

## 3. Register the Class with the Server

In your `main` method (or startup code), create an `NFrameworkServer`, register your service class, and call `start()`.

```java
public static void main(String[] args) {
    NFrameworkServer server = new NFrameworkServer();
    server.registerClass(MyService.class);
    // Register more classes if needed:
    // server.registerClass(OtherService.class);
    server.start(8080);
}
```

`registerClass()` scans the class at startup using Reflection — there is no runtime overhead for route scanning during request handling.

---

## 4. Rules for Service Methods

| Rule | Detail |
|---|---|
| **Must be public** | Private or package-private methods are ignored. |
| **Any return type** | `String`, `int`, custom POJO, `List<T>`, `void` — all supported. |
| **Any number of parameters** | Including zero. All parameters are deserialized from JSON. |
| **Can throw checked exceptions** | Exceptions are propagated to the client transparently. |
| **No static methods** | Methods must be instance methods. |

---

## 5. Multiple Services on One Server

You can register as many classes as needed:

```java
NFrameworkServer server = new NFrameworkServer();
server.registerClass(UserService.class);
server.registerClass(ProductService.class);
server.registerClass(OrderService.class);
server.start(8080);
```

Each class has its own base path, so routes will not conflict as long as the `@Path` values on each class are unique.
