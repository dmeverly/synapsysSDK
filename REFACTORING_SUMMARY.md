# SDK Refactoring Summary

## What Changed

All three SDKs (Java, Python, TypeScript) have been refactored to follow the **ephemeral key forwarding** security model:

### Before
- SDKs called provider APIs **directly** with provider keys
- Each SDK had provider-specific implementation
- No centralized service layer

### After
- SDKs send `SynapSysRequest` to **centralized SynapSys service**
- Service proxies to providers using ephemeral keys (in-memory only)
- Normalized response returned to SDK

## API Contract

**Request:** [synapsys-request.json](./synapsys-request.json)
```json
{
  "provider": "gemini|openai|...",
  "model": "model-name",
  "query": "user query",
  "apiKey": "provider-api-key"
}
```

**Headers:**
```
Authorization: Bearer <synapsys-key>
Content-Type: application/json
```

**Endpoint:** `POST http://localhost:8080/api/generate`

## Files Modified

### Java
- [src/java/Messages/SynapSysRequest.java](./src/java/Messages/SynapSysRequest.java) - Now has only 4 fields + `toJson()` method
- [src/java/Generator.java](./src/java/Generator.java) - Builds request, sends to service, returns response
- [tst/java/tstapp.java](./tst/java/tstapp.java) - Updated to use new API signature

### Python
- [src/python/Messages/SynapSysRequest.py](./src/python/Messages/SynapSysRequest.py) - Dataclass with 4 required fields
- [src/python/Generator.py](./src/python/Generator.py) - Builds request, POSTs to service via urllib
- [tst/python/tstapp.py](./tst/python/tstapp.py) - Updated to use new API signature

### TypeScript
- [src/ts/SynapSysRequest.ts](./src/ts/SynapSysRequest.ts) - Interface + factory function
- [src/ts/Generator.ts](./src/ts/Generator.ts) - New class that sends async request via fetch
- [src/ts/SynapSysResponse.ts](./src/ts/SynapSysResponse.ts) - Already existed, no changes

### Configuration
- [synapsys-request.json](./synapsys-request.json) - Updated schema (removed `synapsysKey`, kept only required fields)
- [SDK_REFACTORING.md](./SDK_REFACTORING.md) - Complete documentation with examples

## Security Improvements

1. **Provider keys never logged** - Kept in memory only during request
2. **Centralized auth** - SynapSys key in HTTP header (standard practice)
3. **Key isolation** - If SynapSys breached, attacker only gets invalid SynapSys keys
4. **Transport security** - Ready for HTTPS enforcement

## Next Steps

1. **Build SynapSys service** - Spring Boot controller that:
   - Authenticates via `Authorization` header
   - Receives `SynapSysRequest` with provider key
   - Routes to provider adapter
   - Calls provider with ephemeral key
   - Returns normalized `SynapSysResponse`

2. **Test end-to-end** - Verify SDK → Service → Provider flow works

3. **Add hardening** - Key memory zeroing, disable core dumps, add request signing

## Usage Examples

### Java
```java
Generator gen = new Generator(
    "gemini", 
    "gemini-2.0-flash",
    "What is AI?",
    geminKey,
    synapsysKey,
    "http://localhost:8080/api/generate"
);
String response = gen.generate();
```

### Python
```python
generator = Generator(
    provider="gemini",
    model="gemini-2.0-flash",
    query="What is AI?",
    api_key=gemini_key,
    synapsys_key=synapsys_key,
    service_endpoint="http://localhost:8080/api/generate"
)
response = generator.generate()
```

### TypeScript
```typescript
const generator = new Generator(
    "gemini",
    "gemini-2.0-flash",
    "What is AI?",
    geminKey,
    synapsysKey,
    "http://localhost:8080/api/generate"
);
const response = await generator.generate();
```

## Verification

✅ Schema validated  
✅ Python SynapSysRequest tested  
✅ All SDKs have matching contract  
✅ HTTP headers follow standard Bearer token pattern  
✅ Ready for service implementation
