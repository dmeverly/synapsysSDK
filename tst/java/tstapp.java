package tst.java;
import src.java.*;

public class tstapp {
    public static void main(String[] args) {
        Generator gen = new Generator("gemini", "gemini-2.5-pro", "Explain how AI works in a few words", System.getenv("GEMINI_API_KEY"));
        String resp = gen.generate();
        System.out.println(resp);
    }
}