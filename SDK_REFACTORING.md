# SynapSys SDK Refactoring

This refactoring updates the Java, Python, and TypeScript SDKs to build and send `SynapSysRequest` objects to a centralized SynapSys service endpoint.

## Schema

The `SynapSysRequest` now matches the minimal contract defined in [synapsys-request.json](./synapsys-request.json):

```json
{
  "provider": "string",      // LLM provider name (e.g., "gemini")
  "model": "string",         // Model identifier
  "query": "string|object",  // User query or structured payload
  "apiKey": "string"         // Provider API key (sent in request body)
}
```

Authentication uses HTTP `Authorization` header with the SynapSys service key:
```
Authorization: Bearer <synapsys-key>
```

## Java SDK

### SynapSysRequest
- Location: [src/java/Messages/SynapSysRequest.java](./src/java/Messages/SynapSysRequest.java)
- Constructor: `new SynapSysRequest(provider, model, query, apiKey)`
- Serialization: `toJson()` method returns JSON string

### Generator
- Location: [src/java/Generator.java](./src/java/Generator.java)
- Constructor: `new Generator(provider, model, query, apiKey, synapsysKey, serviceEndpoint)`
- Method: `generate()` returns response body as String

### Usage
```java
String serviceEndpoint = "http://localhost:8080/api/generate";
Generator gen = new Generator(
    "gemini",
    "gemini-2.0-flash",
    "What is AI?",
    apiKey,
    synapsysKey,
    serviceEndpoint
);
String response = gen.generate();
```

## Python SDK

### SynapSysRequest
- Location: [src/python/Messages/SynapSysRequest.py](./src/python/Messages/SynapSysRequest.py)
- Dataclass with fields: `provider`, `model`, `query`, `apiKey`
- Serialization: `to_json()` returns JSON string

### Generator
- Location: [src/python/Generator.py](./src/python/Generator.py)
- Constructor: `Generator(provider, model, query, api_key, synapsys_key, service_endpoint=None)`
- Method: `generate()` returns response as string or JSON error object

### Usage
```python
generator = Generator(
    provider="gemini",
    model="gemini-2.0-flash",
    query="What is AI?",
    api_key=api_key,
    synapsys_key=synapsys_key,
    service_endpoint="http://localhost:8080/api/generate"
)
response = generator.generate()
```

## TypeScript SDK

### SynapSysRequest
- Location: [src/ts/SynapSysRequest.ts](./src/ts/SynapSysRequest.ts)
- Interface with fields: `provider`, `model`, `query`, `apiKey`
- Factory function: `createSynapSysRequest(provider, model, query, apiKey)`

### Generator
- Location: [src/ts/Generator.ts](./src/ts/Generator.ts)
- Constructor: `new Generator(provider, model, query, apiKey, synapsysKey, serviceEndpoint)`
- Method: `async generate()` returns `Promise<SynapSysResponse>`

### Usage
```typescript
const generator = new Generator(
  "gemini",
  "gemini-2.0-flash",
  "What is AI?",
  apiKey,
  synapsysKey,
  "http://localhost:8080/api/generate"
);
const response = await generator.generate();
```

## Flow

1. **SDK creates SynapSysRequest** with provider, model, query, and provider API key
2. **SDK sends POST** to SynapSys service with:
   - JSON body: `SynapSysRequest` object
   - `Authorization` header: Bearer token (SynapSys key)
3. **Service receives request**, authenticates via header
4. **Service extracts provider key** from request body (ephemeral, in-memory only)
5. **Service routes to provider adapter** (Gemini, OpenAI, etc.)
6. **Service calls provider** with ephemeral key, discards key after response
7. **Service normalizes response** to `SynapSysResponse` format
8. **SDK receives normalized response**

## Security Model

- **SynapSys Key**: Sent via `Authorization` header, used to authenticate SDK to service
- **Provider API Key**: Sent in request body, held in service memory only during single request, never persisted or logged
- **No key leakage**: If SynapSys is compromised, attacker gets invalid SynapSys keys but not provider keys
- **Transport**: All communication over HTTPS (enforced in production)

## Testing

### Python Test
```bash
GEMINI_API_KEY=your-key SYNAPSYS_KEY=your-synapsys-key python3 tst/python/tstapp.py
```

### Java Test
```bash
make run  # Builds and runs Java test
```

### TypeScript Test
```bash
npx ts-node tst/ts/tstapp.ts
```
