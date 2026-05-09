export interface SynapSysResponse {
  ok: boolean;
  provider: string;
  model: string;
  requestId?: string;
  output?: string;
  raw?: Record<string, unknown>;
  error?: {
    code?: string;
    message?: string;
    retryable?: boolean;
    details?: unknown;
  };
}