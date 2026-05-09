import json
from urllib import error, request

from src.python.Providers.ProviderStrategy import ProviderStrategy

class GeminiProvider(ProviderStrategy):

    def __init__(self, provider, model, api_key, queryString):
        super().__init__(provider, model, api_key, queryString)

    def generate(self):
        payload = json.dumps({
            "contents": [
                {
                    "parts": [
                        {"text": self.queryString}
                    ]
                }
            ]
        }).encode("utf-8")

        req = request.Request(
            self.buildEndpointString(),
            data=payload,
            method="POST",
            headers={
                "Content-Type": "application/json",
                "x-goog-api-key": self.api_key or "",
            },
        )

        try:
            with request.urlopen(req, timeout=30) as response:
                return response.read().decode("utf-8")
        except error.HTTPError as exc:
            return exc.read().decode("utf-8")

    def buildEndpointString(self):
        return f"https://generativelanguage.googleapis.com/v1beta/models/{self.model}:generateContent"