import os
import sys
from pathlib import Path

repo_root = Path(__file__).resolve().parents[2]
if str(repo_root) not in sys.path:
    sys.path.insert(0, str(repo_root))

from src.python.Generator import Generator

if __name__ == "__main__":
    # Create a Generator with the new SDK API
    # Provider API key and SynapSys key are sent to the SynapSys service
    generator = Generator(
        provider="gemini",
        model="gemini-2.0-flash",
        query="What is the capital of France?",
        api_key=os.getenv("GEMINI_API_KEY", "your-gemini-key"),
        synapsys_key=os.getenv("SYNAPSYS_KEY", "your-synapsys-key"),
        service_endpoint="http://localhost:8080/api/generate"
    )
    
    response = generator.generate()
    print("Response:", response)