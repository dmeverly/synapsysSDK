package src.java;

import src.java.Providers.GeminiProvider;

public class Generator {
    private final String provider;
    private final String model;
    private final String query;
    private final String key;
    
    public Generator(String provider, String model, String query, String key){
        this.provider = provider;
        this.model = model;
        this.query = query;
        this.key = key;
    }
    public String generate(){
        GeminiProvider gp = new GeminiProvider(provider, model, key, query);
        return gp.generate();
    }
}
