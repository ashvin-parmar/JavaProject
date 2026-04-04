# NFramework Documentation

NFramework is a lightweight Java web-service framework that provides **two-way, TCP-based RPC** (Remote Procedure Call) communication between a server and client. It eliminates boilerplate networking code by using Java **Annotations** and **Reflection** to route and invoke service methods automatically — making it feel like calling a local Java method, even when the logic runs on a remote server.

---

## Documentation Structure

This documentation follows the [Diátaxis](https://diataxis.fr/) framework, organized into four sections:

| Section | Purpose | Good for |
|---|---|---|
| [📖 Tutorials](./tutorials/01-getting-started.md) | Learning-oriented, step-by-step | First-time users |
| [🛠 How-To Guides](./how-to/01-create-a-service.md) | Goal-oriented recipes | Intermediate users |
| [📚 Reference](./reference/01-api-reference.md) | Precise technical facts | Looking things up |
| [🏛 Architecture](./architecture/01-overview.md) | Design & internals explained | Deep understanding |

---

## Modules at a Glance

```
nframework/
├── common/     → Shared: Request, Response, JSONUtil, adapters
├── server/     → NFrameworkServer, RequestProcessor, @Path annotation
├── client/     → NFrameworkClient
├── testing/    → Example: Bank service & BankUI client
└── docs/       → This documentation
```

---

## Quick Start

**1. Build all modules** (in order):
```bash
cd common  && ./gradlew jar
cd server  && ./gradlew jar
cd client  && ./gradlew jar
```

**2. Write a service on the server:**
```java
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
    }
}
```

**3. Call it from the client:**
```java
NFrameworkClient client = new NFrameworkClient("localhost", 8080);
String result = (String) client.execute("/hello/greet", "World");
System.out.println(result); // Hello, World!
```

---

## Key Features

- **Annotation-based routing** — use `@Path` on classes and methods, no config files needed.
- **Reflection-driven dispatch** — the framework discovers and invokes services at runtime.
- **JSON wire format** — all data is serialized to/from JSON using Gson; transport is type-agnostic.
- **Transparent exception propagation** — exceptions thrown on the server are reconstructed and thrown on the client.
- **Singleton service support** — services can expose a static factory method for singleton instantiation.
- **Enum support** — custom `EnumTypeAdapterFactory` ensures enums survive the JSON round-trip correctly.
