export interface SynapSysRequest {
  provider: string;
  model: string;
  query: string | Record<string, unknown>;
  version?: string;
  requestId?: string;
  context?: Record<string, unknown>;
  options?: {
    temperature?: number;
    maxTokens?: number;
    stream?: boolean;
    [key: string]: unknown;
  };
  providerOptions?: Record<string, unknown>;
}