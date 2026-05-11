from dataclasses import dataclass, asdict
from typing import Union
import json

@dataclass
class SynapSysRequest:
    provider: str
    model: str
    query: Union[str, dict]
    apiKey: str
    
    def serialize(self) -> str:
        return json.dumps(asdict(self)).encode('utf-8')