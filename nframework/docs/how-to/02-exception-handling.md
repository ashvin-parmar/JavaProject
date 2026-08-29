# How-To: Handle Exceptions from a Service

> **Goal:** Throw typed exceptions on the server and catch them correctly on the client.

---

## Overview

NFramework propagates exceptions from the server to the client transparently through the JSON wire format.
The `ThrowableAdapter` serializes any `Throwable` as `{ "type": "...", "message": "..." }` and reconstructs it on the other side using `Class.forName()` and reflection.

---

## Step 1 — Define a Custom Exception

The exception class must be **public** so it can be constructed by the framework's reflection code.

```java
// BankingException.java
public class BankingException extends Exception {
    public BankingException(String message) {
        super(message);
    }
}
```

> **Important:** The exception must have a `public Constructor(String message)`.
> If the class cannot be found or instantiated on the client side, the framework falls back to a plain `Exception` with the same message.

---

## Step 2 — Throw It from a Service Method

Declare the exception in the method's `throws` clause:

```java
@Path("/banking")
public class BankService {

    @Path("/getBranch")
    public String getBranch(String area) throws BankingException {
        if (area.equals("Ujjain")) return "Freeganj";
        throw new BankingException("No branch available for: " + area);
    }
}
```

---

## Step 3 — Catch It on the Client

`NFrameworkClient.execute()` is declared `throws Throwable`. Catch your specific exception type first:

```java
NFrameworkClient client = new NFrameworkClient("localhost", 8080);
try {
    String branch = (String) client.execute("/banking/getBranch", "UnknownCity");
    System.out.println("Branch: " + branch);
} catch (BankingException be) {
    System.out.println("Business error: " + be.getMessage());
} catch (Throwable t) {
    System.out.println("Unexpected error: " + t.getMessage());
}
```

---

## How Exception Propagation Works Internally

```
Server                                    Client
  |                                          |
  | Method throws BankingException           |
  |                                          |
  | Response.success = false                 |
  | Response.exception = BankingException    |
  |                                          |
  | ThrowableAdapter serializes:             |
  |   { "type": "BankingException",          |
  |     "message": "No branch available" }   |
  |                                          |
  |-------- JSON Response sent -----------> |
  |                                          |
  |                     ThrowableAdapter deserializes:
  |                     Class.forName("BankingException")
  |                     .getConstructor(String.class)
  |                     .newInstance("No branch available")
  |                                          |
  |                     client throws BankingException
```

---

## Fallback Behaviour

If the exception class is **not on the client's classpath**, the framework catches the `ClassNotFoundException` and returns a plain `java.lang.Exception` with the original message. You can still catch it as `Throwable` and read `getMessage()`.

---

## Catching Framework-Level Errors

If you send a request to a path that does not exist, the server returns a `RuntimeException` with the message `"Invalid path: /your/path"`. Handle it in your catch-all `Throwable` block.
