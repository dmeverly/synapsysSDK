package tst.java;

import src.java.*;

public class tstapp {
    public static void main(String[] args) {
        String geminKey = System.getenv("GEMINI_API_KEY");
        String synapsysKey = System.getenv("SYNAPSYS_KEY");

        if (geminKey == null)
            geminKey = "your-gemini-key";
        if (synapsysKey == null)
            synapsysKey = "your-synapsys-key";

        // Create Generator with the new SDK API
        // Provider API key and SynapSys key are sent to the SynapSys service
        Generator gen = new Generator(
                "gemini",
                "gemini-2.0-flash",
                "Explain how AI works in a few words",
                geminKey,
                synapsysKey,
                "http://localhost:8080/api/generate");

        String resp = gen.generate();
        System.out.println("Response: " + resp);
    }
}