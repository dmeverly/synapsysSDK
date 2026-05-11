import json
from urllib import request
from src.python.Messages.SynapSysRequest import SynapSysRequest

class Generator:
    def __init__(self, provider, model, query, api_key, synapsys_key, service_endpoint=None):
        self.provider = provider
        self.model = model
        self.query = query
        self.api_key = api_key
        self.synapsys_key = synapsys_key
        self.service_endpoint = service_endpoint or "http://localhost:8080/api/generate"
    
    def generate(self):
        try:
            synapsys_request = SynapSysRequest(
                provider=self.provider,
                model=self.model,
                query=self.query,
                apiKey=self.api_key
            )
            
            json_body = synapsys_request.serialize()
            
            http_request = request.Request(
                self.service_endpoint,
                data=json_body,
                method="POST",
                headers={
                    "Content-Type": "application/json",
                    "Authorization": f"Bearer {self.synapsys_key}"
                }
            )
            
            with request.urlopen(http_request, timeout=30) as response:
                return response.read().decode('utf-8')
        except Exception as e:
            return json.dumps({"error": str(e)})