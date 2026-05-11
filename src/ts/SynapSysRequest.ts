export interface SynapSysRequest {
    provider: string;
    model: string;
    query: string | Record<string, unknown>;
    apiKey: string;
}

export function createSynapSysRequest(
    provider: string,
    model: string,
    query: string | Record<string, unknown>,
    apiKey: string
): SynapSysRequest {
    return {
        provider,
        model,
        query,
        apiKey
    };
}