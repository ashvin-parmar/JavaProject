# Reference: Wire Protocol

> Technical specification of the binary protocol NFramework uses over TCP.

---

## Overview

NFramework uses a **length-prefixed binary protocol** over raw TCP sockets. Every exchange follows a strict two-phase handshake: a fixed 1024-byte header communicates the payload size, and an acknowledgement byte gates each data transfer.

There is no HTTP, WebSocket, or any other higher-level protocol involved. The protocol is intentionally minimal — just enough framing to transfer arbitrary-length JSON payloads reliably.

---

## Request Flow (Client → Server)

```
Client                                     Server
  |                                           |
  |--- 1024-byte header (request length) ---> |
  |<-- 1 byte ACK (0x01) -------------------- |
  |--- request JSON payload (N bytes) ------> |
  |                                           |
```

### Header Format

The header is always exactly **1024 bytes**.

Each byte stores one decimal digit of the payload length, right-aligned.
Unused leading bytes are `0x00`.

**Example:** payload length = `350` bytes

```
byte[0]    = 0x00
byte[1]    = 0x00
...
byte[1021] = 0x00
byte[1022] = 0x03   (digit '3')
byte[1023] = 0x05   (digit '5')
           ... wait, 350 has 3 digits
byte[1021] = 0x03
byte[1022] = 0x05
byte[1023] = 0x00
```

> Precise encoding: starting from `i = 1023`, `header[i] = length % 10`, then `length /= 10`, decrement `i`. Repeat until `i < 0`.

### Decoding the Header

```
length = 0
j = 1
for i from 1023 down to 0:
    length += header[i] * j
    j *= 10
```

---

## Response Flow (Server → Client)

```
Server                                     Client
  |                                           |
  |--- 1024-byte header (response length) --> |
  |<-- 1 byte ACK (0x01) ------------------- |
  |--- response JSON payload (N bytes) -----> |
  |<-- 1 byte ACK (0x01) ------------------- |
  |   [socket closed]                         |
```

The response uses the **same header format** as the request.
Two ACKs are exchanged: one before the payload is sent and one after, signalling the client has fully received the data before the socket is closed.

---

## Data Transfer — Chunking

Both client and server transfer the payload in **1024-byte chunks**:

```java
int chunkSize = 1024;
long j = 0;
while (j < payloadLength) {
    if ((payloadLength - j) < chunkSize) chunkSize = (int)(payloadLength - j);
    os.write(payloadBytes, (int)j, chunkSize);
    os.flush();
    j += chunkSize;
}
```

There is no compression or fragmentation beyond this basic chunking.

---

## JSON Payload Format

### Request JSON

```json
{
  "servicePath": "/banking/getBranch",
  "arguments": ["Ujjain"]
}
```

### Response JSON (success)

```json
{
  "success": true,
  "result": "Freeganj",
  "exception": null
}
```

### Response JSON (failure)

```json
{
  "success": false,
  "result": "",
  "exception": {
    "type": "BankingException",
    "message": "No branch available"
  }
}
```

---

## Character Encoding

All JSON strings are encoded and decoded using **UTF-8** (`StandardCharsets.UTF_8`). This ensures correct handling of multi-byte characters.

---

## Connection Lifecycle

- One TCP connection is opened **per `execute()` call**.
- The connection is closed by the server after the response ACK is received.
- There is no persistent connection, keep-alive, or connection pooling.

---

## Concurrency

- `NFrameworkServer.start()` runs a single-threaded accept loop.
- For each accepted `Socket`, a new `RequestProcessor extends Thread` is created and `start()`ed.
- Requests from different clients are handled concurrently in separate threads.
- There is no thread pool — a new OS thread is created per connection.
