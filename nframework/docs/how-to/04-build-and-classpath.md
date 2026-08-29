# How-To: Build and Use the Framework JARs

> **Goal:** Understand how to compile the three framework modules and set up a classpath for your own project.

---

## Module Build Order

The modules have a dependency chain. Always build in this order:

```
common  →  server
        →  client
```

Both `server` and `client` depend on `nframework-common.jar`.

---

## Building with Gradle

Each module has its own `build.gradle`. Build each one from its directory:

```bash
# Step 1: Build common (no dependencies)
cd /path/to/nframework/common
./gradlew jar

# Step 2: Build server (depends on common + gson)
cd ../server
./gradlew jar

# Step 3: Build client (depends on common)
cd ../client
./gradlew jar
```

Output JARs:

| Module | Output JAR |
|---|---|
| common | `common/build/libs/nframework-common.jar` |
| server | `server/build/libs/nframework-server.jar` |
| client | `client/build/libs/nframework-client.jar` |

---

## External Dependency: Gson

The `common` and `server` modules require Gson. The default build config references:

```
/media/ashvin/code/my-libs/java/gson/gson-2.13.1.jar
```

Update this path in `common/build.gradle` and `server/build.gradle` to point to your local copy of `gson-2.13.1.jar` (or a compatible version).

---

## Classpath Setup for Your Application

When compiling and running your application, include all three JARs plus Gson:

```bash
CP="server/build/libs/nframework-server.jar:\
    client/build/libs/nframework-client.jar:\
    common/build/libs/nframework-common.jar:\
    /path/to/gson-2.13.1.jar:."

# Compile
javac -classpath $CP MyApp.java

# Run server
java -classpath $CP MyServer

# Run client
java -classpath $CP MyClient
```

---

## Reference: Testing Directory Scripts

The `testing/` directory contains example shell scripts you can adapt:

```bash
# Compile example files
testing/compile.sh

# Run Bank (server)
testing/run_server.sh

# Run BankUI (client), pass area name as argument
testing/run_application.sh Mumbai
```
