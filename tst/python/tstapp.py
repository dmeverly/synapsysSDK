import os
import sys
from pathlib import Path

repo_root = Path(__file__).resolve().parents[2]
if str(repo_root) not in sys.path:
    sys.path.insert(0, str(repo_root))

from src.python.Generator import Generator

if __name__ == "__main__":
    print(Generator(
        "gemini",
        "gemini-2.5-pro",
        "What is the capital of France?",
        os.getenv("GEMINI_API_KEY")
    ).generate())