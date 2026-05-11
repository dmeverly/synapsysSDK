import { SynapSysRequest, createSynapSysRequest } from "./SynapSysRequest";
import { SynapSysResponse } from "./SynapSysResponse";

export class Generator {
    private readonly provider: string;
    private readonly model: string;
    private readonly query: string | Record<string, unknown>;
    private readonly apiKey: string;
    private readonly synapsysKey: string;
    private readonly serviceEndpoint: string;

    constructor(
        provider: string,
        model: string,
        query: string | Record<string, unknown>,
        apiKey: string,
        synapsysKey: string,
        serviceEndpoint: string = "http://localhost:8080/api/generate"
    ) {
        this.provider = provider;
        this.model = model;
        this.query = query;
        this.apiKey = apiKey;
        this.synapsysKey = synapsysKey;
        this.serviceEndpoint = serviceEndpoint;
    }

    async generate(): Promise<SynapSysResponse> {
        try {
            const request = createSynapSysRequest(
                this.provider,
                this.model,
                this.query,
                this.apiKey
            );

            const response = await fetch(this.serviceEndpoint, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${this.synapsysKey}`
                },
                body: JSON.stringify(request)
            });

            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }

            return await response.json() as SynapSysResponse;
        } catch (error) {
            return {
                ok: false,
                provider: this.provider,
                model: this.model,
                error: error instanceof Error ? error.message : String(error)
            } as SynapSysResponse;
        }
    }
}
