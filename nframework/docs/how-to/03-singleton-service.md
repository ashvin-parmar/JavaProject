# How-To: Use a Singleton Service

> **Goal:** Make a service class use a single shared instance across all requests, instead of creating a new instance per request.

---

## Why Singletons?

By default, NFramework instantiates a **new object** of the service class for every incoming request using `Class.newInstance()`.

If your service needs to maintain state (e.g., an in-memory store, a database connection pool, or a cached resource), you should use a singleton pattern so all requests share the same instance.

---

## How It Works

Before falling back to `newInstance()`, `RequestProcessor` checks if the service class exposes a static factory method with the naming convention:

```
public static ClassName getClassName()
```

If this method exists, the framework calls it to obtain the service instance. Your factory method controls the lifecycle.

---

## Implementation

```java
@Path("/banking")
public class Bank {

    // Private singleton instance
    private static Bank bank = null;

    // Private constructor — prevents external instantiation
    private Bank() {}

    // Factory method — naming convention: "get" + ClassName
    public static Bank getBank() {
        if (bank == null) bank = new Bank();
        return bank;
    }

    @Path("/getBranch")
    public String getBranch(String area) throws BankingException {
        // All requests share this single Bank instance
        if (area.equals("Ujjain")) return "Freeganj";
        throw new BankingException("No branch found for: " + area);
    }

    public static void main(String[] args) {
        NFrameworkServer server = new NFrameworkServer();
        server.registerClass(Bank.class);
        server.start(8080);
    }
}
```

---

## Discovery Logic in the Framework

From `RequestProcessor.java`:

```java
Object serviceObject = null;
try {
    Method m = c.getMethod("get" + c.getSimpleName());
    serviceObject = m.invoke(c);   // calls Bank.getBank()
} catch (Throwable t) {
    // No factory method found — fall through
}
if (serviceObject == null) {
    serviceObject = c.newInstance(); // fallback: new instance per request
}
```

---

## Rules

| Rule | Detail |
|---|---|
| Method name | Must be exactly `"get" + ClassName` (e.g., `getBank` for `Bank`). |
| Must be static | The method is invoked on the class, not an instance. |
| Must be public | The framework uses `getMethod()`, which only finds public methods. |
| Return type | Must return an instance of the class itself. |

---

## Thread Safety Warning

> Each request is handled in its own `Thread` (via `RequestProcessor extends Thread`).
> If your singleton service is stateful and multiple clients call it simultaneously, you must handle thread safety yourself (e.g., with `synchronized` blocks or `java.util.concurrent` primitives).

---

## When NOT to Use Singletons

- When each request must work with isolated state (user session data, per-request transactions).
- When the service has no shared state — the default per-request instantiation is simpler and always safe.
