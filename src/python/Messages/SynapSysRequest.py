from dataclasses import dataclass, field
from typing import Any, Optional

@dataclass
class SynapSysRequest:
    provider: str
    model: str
    query: Any
    version: str = "1"
    requestId: Optional[str] = None
    context: Optional[dict] = field(default_factory=dict)
    options: Optional[dict] = field(default_factory=dict)
    providerOptions: Optional[dict] = field(default_factory=dict)