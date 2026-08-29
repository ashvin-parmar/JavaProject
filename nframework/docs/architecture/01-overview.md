# Architecture Overview

> **Goal:** Understand *why* NFramework is designed the way it is, how the pieces fit together, and what trade-offs were made.

---

## Design Philosophy

NFramework was built around a single idea:

> **A developer should call a remote service the same way they call a local Java method.**

This means:
- No manually crafting HTTP requests.
- No writing serialization/deserialization code per service.
- No managing socket connections by hand.
- Exceptions on the server are exceptions on the client.

The framework achieves this by using **Java Reflection** and **Annotations** to bridge the gap between a method call and a network request.

---

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                            CLIENT SIDE                              │
│                                                                     │
│   Application Code                                                  │
│   client.execute("/banking/getBranch", "Ujjain")                    │
│            │                                                        │
│   NFrameworkClient                                                  │
│   • Wraps args in Request object                                    │
│   • Serializes to JSON (JSONUtil)                                   │
│   • Sends over TCP with length header                               │
│   • Waits for response, deserializes Response                       │
│   • Returns result or rethrows exception                            │
└─────────────────────┬───────────────────────────────────────────────┘
                      │  TCP Socket (raw, port e.g. 8080)
┌─────────────────────▼───────────────────────────────────────────────┐
│                            SERVER SIDE                              │
│                                                                     │
│   NFrameworkServer                                                  │
│   • Holds route map: path → TCPService                              │
│   • accept() loop → spawns RequestProcessor thread per connection   │
│            │                                                        │
│   RequestProcessor (Thread)                                         │
│   • Reads Request JSON from socket                                  │
│   • Looks up TCPService by path                                     │
│   • Instantiates service object (singleton check or newInstance)    │
│   • Deserializes arguments to declared parameter types              │
│   • Invokes method via reflection                                   │
│   • Wraps result/exception in Response                              │
│   • Serializes Response to JSON, sends back                         │
│            │                                                        │
│   Service Class (User Code)                                         │
│   @Path("/banking") class Bank { @Path("/getBranch") ... }          │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Module Responsibilities

| Module | Responsibility |
|---|---|
| `common` | Shared data contracts (`Request`, `Response`), JSON utilities, exception serialization. |
| `server` | Service registration, connection acceptance, request dispatching, reflection-based invocation. |
| `client` | Packing and sending requests, receiving and unpacking responses. |

The `common` module is deliberately kept dependency-light — it is the shared language between server and client.

---

## The Reflection Pipeline

This is the core of the framework. When a request arrives:

1. **Route lookup** — `HashMap.get(servicePath)` → `TCPService` (holds `Class` + `Method`).
2. **Object instantiation** — tries `get<ClassName>()` factory first (for singletons), then falls back to `Class.newInstance()`.
3. **Argument deserialization** — reads `method.getGenericParameterTypes()`, then for each argument uses `gson.fromJson(gson.toJson(arg), type)` to convert from the raw JSON structure (which comes in as `LinkedTreeMap`) to the correct declared type.
4. **Invocation** — `method.invoke(serviceObject, args)`.
5. **Result handling** — the return value is set on `Response.result`; if an exception is thrown (`InvocationTargetException`), its cause is set on `Response.exception`.

---

## Why JSON over a Custom Binary Format?

A custom binary format would be faster but would require tight coupling between client and server on data structures. JSON gives:

- **Human-readable wire data** — easy to debug with Wireshark or logging.
- **Language-neutral potential** — a future Python or JavaScript client could speak the same protocol.
- **Gson's type adapter system** — allows clean handling of edge cases like enums and exceptions.

The trade-off is overhead: JSON is verbose compared to binary formats like Protobuf. This is acceptable for an RPC framework aimed at applications (like chats) where latency is more important than raw throughput.

---

## Why a 1024-Byte Header?

The header communicates the payload size before the payload is sent. This allows the receiver to allocate an exact byte array for the payload, avoiding dynamic buffering complexity.

1024 bytes can encode a number up to 10^1024 − 1, which is astronomically larger than any realistic JSON payload. In practice, only the last few bytes are ever non-zero.

This is simple and robust, at the cost of sending ~1 KB of zeros per request/response. For a two-way communication framework, this overhead is negligible.

---

## The Two-ACK Handshake

Each data transfer is gated by an acknowledgement byte:

- **Request:** Server ACKs the header → Client sends payload.
- **Response:** Client ACKs the header → Server sends payload → Client ACKs receipt.

This prevents the sender from flooding the network before the receiver is ready, and gives both sides a clear signal for when to proceed. It is a simple form of **flow control** without the complexity of sliding windows.

---

## Return Type Erasure

This is a known design trade-off. The `execute()` method returns `Object` because the client has no compile-time knowledge of the remote method's return type. The JSON deserializer returns:

- A `String` for JSON strings.
- A `Double` for JSON numbers (Gson's default).
- An `ArrayList<LinkedTreeMap>` for JSON arrays of objects.
- A `LinkedTreeMap` for a single JSON object.

**The responsibility of converting this to the correct type falls on the caller.** For simple types (`String`, `int`) it works seamlessly. For complex types, Gson must be used explicitly:

```java
Gson gson = new Gson();
State s = gson.fromJson(gson.toJson(raw), State.class);
```

Future versions could address this with generics, proxies, or a type-token API.

---

## Per-Request Threading Model

Each accepted TCP connection spawns a new `Thread`. This is the **thread-per-connection** model:

- **Pros:** Simple to implement; each request is fully isolated; no shared mutable state in the dispatch path.
- **Cons:** Does not scale to thousands of concurrent connections (OS thread limit). A thread pool (`ExecutorService`) would be a natural upgrade.

For the target use case (chat applications, small to medium concurrent users), this model is adequate.

---

## Service Singleton Detection

The convention `get<ClassName>()` for singleton factory methods was chosen because:

- It mirrors the Java naming convention for factory/getter methods.
- It requires zero additional annotation or configuration.
- It is discovered at request time using `Class.getMethod()`, which is fast (it checks the class's public method table).

The downside is it is a naming convention, not a contract — there is no compiler enforcement. A misspelled method name will silently fall back to `newInstance()`.

---

## Known Limitations & Future Work

| Limitation | Impact | Possible Fix |
|---|---|---|
| No thread pool | Poor scalability under heavy load | Replace raw thread creation with `ExecutorService` |
| Return type erasure | Caller must manually deserialize complex results | Generic `execute<T>()` with `TypeToken`, or code-generated client proxies |
| No persistent connections | One TCP connect/close per call — high overhead for chatty services | Connection pooling or keep-alive |
| Singleton detection by convention | Fragile — typos silently break it | Introduce a `@Singleton` annotation |
| IOException swallowed on client | Network errors return `null` instead of throwing | Wrap in `NetworkException` and rethrow |
| No TLS/encryption | All data is plaintext | Wrap sockets with `SSLSocket` |
| No authentication | Any client can call any path | Add token/session middleware layer |
