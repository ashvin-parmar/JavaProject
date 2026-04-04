# API Reference

Complete technical reference for all public classes, methods, and annotations in NFramework.

---

## Package: `com.ashvin.nframework.server`

### `NFrameworkServer`

The main entry point for the server side. Manages service registration and accepts incoming TCP connections.

**Package:** `com.ashvin.nframework.server`

#### Constructor

```java
public NFrameworkServer()
```

Creates a new server instance with empty service and class registries.

---

#### Methods

##### `registerClass(Class c)`

```java
public void registerClass(Class c)
```

Scans the given class for `@Path` annotations and registers all annotated methods as TCP service endpoints.

| Parameter | Type | Description |
|---|---|---|
| `c` | `Class` | The service class to scan. Must have a class-level `@Path` annotation; otherwise this call is a no-op. |

**Behaviour:**
- Reads `@Path` on the class → sets the base path.
- Iterates over all public methods; for each method with `@Path`, computes `fullPath = classPath + methodPath` and stores a `TCPService` entry.
- Multiple classes can be registered; routes are stored in a `HashMap<String, TCPService>`.

---

##### `start(int port)`

```java
public void start(int port)
```

Binds a `ServerSocket` to the given port and enters an infinite accept loop. **This method blocks indefinitely.**

| Parameter | Type | Description |
|---|---|---|
| `port` | `int` | TCP port to listen on (e.g., `8080`). |

For each accepted connection, a new `RequestProcessor` thread is created and started automatically.

---

##### `getTCPService(String path)`

```java
public TCPService getTCPService(String path)
```

Looks up a registered service by its full path string. Used internally by `RequestProcessor`.

| Parameter | Type | Description |
|---|---|---|
| `path` | `String` | Full route path, e.g. `"/banking/getBranch"`. |

**Returns:** The matching `TCPService`, or `null` if no service is registered at that path.

---

### `RequestProcessor` *(package-private)*

Handles one client connection on a dedicated thread. Extends `Thread`.

Not directly accessible outside the `server` package. It is created internally by `NFrameworkServer.start()`.

**Lifecycle:**
1. Reads a 1024-byte header from the socket (contains the request byte-length).
2. Sends an ACK byte back to the client.
3. Reads the full request JSON payload.
4. Deserializes to `Request`, looks up the `TCPService`, instantiates the service object.
5. Deserializes arguments to their declared parameter types using Gson.
6. Invokes the target method via reflection.
7. Serializes the `Response` to JSON, sends a 1024-byte response header (length), waits for ACK, sends the payload, waits for final ACK.
8. Closes the socket.

---

### `TCPService` *(package-private)*

A simple data holder representing one registered service endpoint.

| Field | Type | Description |
|---|---|---|
| `c` | `Class` | The service class. |
| `method` | `Method` | The service method. |
| `path` | `String` | The full route path. |

---

## Package: `com.ashvin.nframework.server.annotations`

### `@Path`

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Path {
    public String value() default "";
}
```

Marks a class or method as an NFramework service endpoint.

| Usage | Target | Description |
|---|---|---|
| On a class | `ElementType.TYPE` | Sets the base path for all methods in the class. |
| On a method | `ElementType.METHOD` | Sets the method-level path segment. Full path = class path + method path. |

**Example:**
```java
@Path("/banking")           // base path
public class BankService {
    @Path("/getBranch")     // → full route: /banking/getBranch
    public String getBranch(String area) { ... }
}
```

---

## Package: `com.ashvin.nframework.client`

### `NFrameworkClient`

The main entry point for the client side. Opens a TCP connection per call and handles the full request/response protocol.

**Package:** `com.ashvin.nframework.client`

#### Constructor

```java
public NFrameworkClient(String host, int port)
```

| Parameter | Type | Description |
|---|---|---|
| `host` | `String` | Hostname or IP address of the server. |
| `port` | `int` | TCP port the server is listening on. |

---

#### Methods

##### `execute(String servicePath, Object ...arguments)`

```java
public Object execute(String servicePath, Object ...arguments) throws Throwable
```

Connects to the server, sends the request, and returns the result.

| Parameter | Type | Description |
|---|---|---|
| `servicePath` | `String` | Full route path of the target service method. |
| `arguments` | `Object...` | Varargs — the arguments to pass to the remote method. |

**Returns:** `Object` — the deserialized result. For primitive types this may be a boxed type (`Double`, `Boolean`, etc.); for complex objects it will be a Gson `LinkedTreeMap` or `ArrayList`. Use Gson to convert to your expected type.

**Throws:** `Throwable` — rethrows any exception that was thrown on the server side. Also throws if there is a network-level `IOException` (though currently this is caught and `null` is returned — see known limitations).

**Protocol (per call):**
1. Creates a new `Socket` connection.
2. Serializes `Request` to JSON.
3. Sends a 1024-byte header (byte representation of the JSON length, right-aligned).
4. Waits for ACK from server.
5. Sends the request JSON payload in 1024-byte chunks.
6. Reads the response header (1024 bytes, same format).
7. Sends ACK to server.
8. Reads the response JSON payload.
9. Sends final ACK.
10. Closes the socket.
11. Deserializes the `Response` and returns the result or rethrows the exception.

---

## Package: `com.ashvin.nframework.common`

### `Request`

Implements `java.io.Serializable`. Represents a client-to-server call.

| Field | Type | Description |
|---|---|---|
| `servicePath` | `String` | The full route path to invoke. |
| `arguments` | `Object[]` | The arguments to pass to the method. |

**Methods:**

| Method | Signature | Description |
|---|---|---|
| `setServicePath` | `void setServicePath(String)` | Sets the target route. |
| `getServicePath` | `String getServicePath()` | Gets the target route. |
| `setArguments` | `void setArguments(Object...)` | Sets the argument array. |
| `getArguments` | `Object[] getArguments()` | Gets the argument array. |

---

### `Response`

Implements `java.io.Serializable`. Represents a server-to-client reply.

| Field | Type | Description |
|---|---|---|
| `success` | `boolean` | `true` if the method returned normally, `false` if it threw. |
| `result` | `Object` | The return value (when `success = true`). |
| `exception` | `Throwable` | The thrown exception (when `success = false`). |

**Methods:**

| Method | Signature | Description |
|---|---|---|
| `setSuccess` | `void setSuccess(boolean)` | Sets the success flag. |
| `getSuccess` | `boolean getSuccess()` | Returns the success flag. |
| `setResult` | `void setResult(Object)` | Sets the result value. |
| `getResult` | `Object getResult()` | Returns the result value. |
| `setException` | `void setException(Throwable)` | Sets the exception. |
| `getException` | `Throwable getException()` | Returns the exception. |
| `hasException` | `boolean hasException()` | Returns `true` when `success == false`. |

---

### `JSONUtil`

Static utility for JSON serialization/deserialization. Not instantiable (private constructor).

##### `toJSON(Serializable serializableClass)`

```java
public static String toJSON(java.io.Serializable serializableClass)
```

Serializes any `Serializable` object to a JSON string. Registered adapters:
- `ThrowableAdapter` (handles `Throwable` hierarchy)
- `EnumTypeAdapterFactory` (serializes enums by name)

Returns `"{}"` on error.

---

##### `fromJSON(String jsonString, Class<T> c)`

```java
public static <T> T fromJSON(String jsonString, Class<T> c)
```

Deserializes a JSON string to an instance of type `T`. Uses the same adapters as `toJSON`.

Returns `null` on error.

---

### `EnumTypeAdapterFactory`

Implements `TypeAdapterFactory`. Automatically registered by `JSONUtil`.

Serializes enums as their `.name()` string (e.g., `"INDIA"` instead of ordinal `0`).
Deserializes by matching the string against `Enum.name()` for all constants of the type.

---

### `ThrowableAdapter` *(package-private)*

Implements `JsonSerializer<Throwable>` and `JsonDeserializer<Throwable>`.

**Serialization format:**
```json
{
  "type": "com.example.BankingException",
  "message": "No branch available"
}
```

**Deserialization:** Uses `Class.forName(type)` and `.getConstructor(String.class).newInstance(message)` to reconstruct the original exception type. Falls back to `new Exception(message)` if reconstruction fails.

---

## Package: `com.ashvin.nframework.common.exceptions`

### `NetworkException`

```java
public class NetworkException extends Exception
```

A checked exception for network-level errors. Provided for use in custom services or future framework extensions.

| Constructor | Description |
|---|---|
| `NetworkException()` | No-arg constructor. |
| `NetworkException(String message)` | Constructs with a message. |
