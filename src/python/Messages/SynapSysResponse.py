from dataclasses import dataclass, field
from typing import Any, Optional

@dataclass
class SynapSysResponse:
    ok: bool
    provider: str
    model: str
    requestId: Optional[str] = None
    output: Optional[str] = None
    raw: Optional[dict] = field(default_factory=dict)
    error: Optional[dict] = field(default_factory=dict)