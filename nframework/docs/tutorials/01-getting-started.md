# Tutorial 1 — Getting Started with NFramework

> **Goal:** Build and run a working "Hello World" RPC service from scratch in under 10 minutes.
> No prior knowledge of the framework is assumed — just Java and Gradle.

---

## Prerequisites

| Requirement | Version |
|---|---|
| Java (JDK) | 11 or higher |
| Gradle | 7+ (wrapper included) |
| Gson | `gson-2.13.1.jar` (or compatible) |

---

## Step 1 — Build the Framework JARs

The framework has three modules that must be built in order because `server` and `client` both depend on `common`.

```bash
# From the nframework root directory

cd common
./gradlew jar
# Produces: common/build/libs/nframework-common.jar

cd ../server
./gradlew jar
# Produces: server/build/libs/nframework-server.jar

cd ../client
./gradlew jar
# Produces: client/build/libs/nframework-client.jar
```

After this step you will have three JARs ready to use in your own project.

---

## Step 2 — Create Your Project Directory

```bash
mkdir my-app
cd my-app
```

Place your source files here. Your classpath will include all three framework JARs plus `gson`.

---

## Step 3 — Write a Service Class

Create `HelloService.java`:

```java
import com.ashvin.nframework.server.NFrameworkServer;
import com.ashvin.nframework.server.annotations.Path;

@Path("/hello")
public class HelloService {

    @Path("/greet")
    public String greet(String name) {
        return "Hello, " + name + "!";
    }

    public static void main(String[] args) {
        NFrameworkServer server = new NFrameworkServer();
        server.registerClass(HelloService.class);
        server.start(8080);
        // Server is now listening on port 8080
    }
}
```

**What's happening here:**
- `@Path("/hello")` on the class sets the base path for this service.
- `@Path("/greet")` on the method registers the full route `/hello/greet`.
- `NFrameworkServer` discovers these routes at startup via Java Reflection.

---

## Step 4 — Write a Client

Create `HelloClient.java`:

```java
import com.ashvin.nframework.client.NFrameworkClient;

public class HelloClient {
    public static void main(String[] args) throws Throwable {
        NFrameworkClient client = new NFrameworkClient("localhost", 8080);

        // Call the remote method just like a local method call
        String result = (String) client.execute("/hello/greet", "World");
        System.out.println(result); // prints: Hello, World!
    }
}
```

---

## Step 5 — Compile

```bash
CLASSPATH="../nframework/server/build/libs/nframework-server.jar:\
../nframework/client/build/libs/nframework-client.jar:\
../nframework/common/build/libs/nframework-common.jar:\
/path/to/gson-2.13.1.jar:."

javac -classpath $CLASSPATH *.java
```

---

## Step 6 — Run the Server

```bash
java -classpath $CLASSPATH HelloService
```

The server starts and blocks, waiting for incoming connections on port 8080.

---

## Step 7 — Run the Client

Open a **second terminal** in the same directory:

```bash
java -classpath $CLASSPATH HelloClient
```

Expected output:
```
Hello, World!
```

---

## What Just Happened?

```
HelloClient                          HelloService (Server)
    |                                       |
    |-- execute("/hello/greet","World") --> |
    |   [TCP connection opened]             |
    |   [Request serialized to JSON]        |
    |   [Header (size) sent first]          |
    |   [ACK received]                      |
    |   [Payload sent]                      |
    |                              greet("World") invoked via Reflection
    |                              returns "Hello, World!"
    |   [Response JSON received] <----------|
    |   [ACK sent]                          |
    |   [Connection closed]                 |
    |                                       |
 "Hello, World!" returned to caller
```

The framework handled all the networking, serialization, routing, and reflection — you just wrote a plain Java class.

---

## Next Steps

- [Tutorial 2 — Passing Complex Objects](./02-complex-objects.md)
- [How-To: Handle Exceptions from a Service](../how-to/02-exception-handling.md)
- [How-To: Use a Singleton Service](../how-to/03-singleton-service.md)
