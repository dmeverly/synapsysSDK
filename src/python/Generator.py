from src.python.Providers.GeminiProvider import GeminiProvider

class Generator:
    def __init__(self, provider, model, queryString, api_key):
        self.provider = provider
        self.model = model
        self.api_key = api_key
        self.queryString = queryString
    
    def generate(self):
        if self.provider == "gemini":
            return GeminiProvider(
                self.provider,
                self.model,
                self.api_key,
                self.queryString,
            ).generate()

        raise ValueError(f"Unsupported provider: {self.provider}")