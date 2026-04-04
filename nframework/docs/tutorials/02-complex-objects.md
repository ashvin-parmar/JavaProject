# Tutorial 2 — Passing Complex Objects & Enums

> **Goal:** Learn how to pass and return objects (POJOs) and enums through NFramework,
> and understand how to deserialize the response on the client side.

---

## Background

NFramework serializes all method arguments and return values as **JSON** using Gson.
This means any object that Gson can serialize can be passed across the wire.
However, since the client's `execute()` returns `Object`, you need to manually deserialize
the raw response into your expected type on the client side.

---

## Step 1 — Shared Data Classes

Both the server and client need access to the same data class definitions.
Define them in a shared location (or duplicate them if the projects are separate).

```java
// COUNTRY.java
public enum COUNTRY {
    INDIA, USA, UK
}

// State.java
public class State implements java.io.Serializable {
    private COUNTRY country;
    private String state;

    public State(COUNTRY country, String state) {
        this.country = country;
        this.state = state;
    }

    public String getCountry() { return this.country.toString(); }
    public String getState()   { return this.state; }
    public void setCountry(COUNTRY country) { this.country = country; }
    public void setCountry(String country)  { this.country = COUNTRY.valueOf(country); }
    public void setState(String state)      { this.state = state; }
}
```

---

## Step 2 — Service Method That Returns a List

```java
import com.ashvin.nframework.server.annotations.Path;
import java.util.*;

@Path("/geo")
public class GeoService {

    @Path("/getStates")
    public List<State> getStates(COUNTRY country) {
        List<State> states = new LinkedList<>();
        if (country == COUNTRY.INDIA) {
            states.add(new State(COUNTRY.INDIA, "Maharashtra"));
            states.add(new State(COUNTRY.INDIA, "Karnataka"));
        } else if (country == COUNTRY.USA) {
            states.add(new State(COUNTRY.USA, "California"));
        }
        return states;
    }

    public static void main(String[] args) {
        NFrameworkServer server = new NFrameworkServer();
        server.registerClass(GeoService.class);
        server.start(8080);
    }
}
```

---

## Step 3 — Client: Calling and Deserializing

Because `execute()` returns `Object`, the raw JSON structure comes back as a Gson `LinkedTreeMap` (for objects) or `ArrayList` (for lists). You must use Gson explicitly to convert:

```java
import com.ashvin.nframework.client.NFrameworkClient;
import com.google.gson.*;
import java.util.*;

public class GeoClient {
    public static void main(String[] args) throws Throwable {
        NFrameworkClient client = new NFrameworkClient("localhost", 8080);

        // Call the service
        Object raw = client.execute("/geo/getStates", COUNTRY.INDIA);

        // Deserialize the response using Gson
        Gson gson = new Gson();
        JsonArray jsonArray = JsonParser.parseString(raw.toString()).getAsJsonArray();

        for (JsonElement element : jsonArray) {
            State state = gson.fromJson(element, State.class);
            System.out.println(state.getCountry() + " — " + state.getState());
        }
    }
}
```

Expected output:
```
INDIA — Maharashtra
INDIA — Karnataka
```

---

## How Enums Are Handled

NFramework includes a custom `EnumTypeAdapterFactory` that serializes enums **by name** (e.g., `"INDIA"`), not by ordinal. This ensures:

- The correct enum constant is matched on deserialization.
- Renaming enum constants will break the wire protocol (ordinal-based would not, but name-based is human-readable and easier to debug).

You do **not** need to configure this — `JSONUtil` registers it automatically.

---

## Key Limitation — Return Type Erasure

> The client's `execute()` method has no way to know the expected return type at the network level.
> The return value will always be a generic JSON structure (String, Number, List of `LinkedTreeMap`, etc.).
> **You must deserialize it on the client side using Gson.**

This is a known design consideration — see [Architecture: Return Type Handling](../architecture/01-overview.md#return-type-erasure).

---

## Next Steps

- [How-To: Handle Exceptions](../how-to/02-exception-handling.md)
- [How-To: Use a Singleton Service](../how-to/03-singleton-service.md)
- [Reference: JSONUtil](../reference/01-api-reference.md#jsonutil)
