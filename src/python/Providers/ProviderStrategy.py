class ProviderStrategy:
    def __init__(self, provider, model, api_key, queryString):
        self.provider = provider
        self.model = model
        self.api_key = api_key
        self.queryString = queryString

    def generate(self):
        raise NotImplementedError("Subclasses must implement this method")