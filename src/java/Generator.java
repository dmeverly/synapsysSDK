package java;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.Messages.SynapSysRequest;

public class Generator {
    private final String provider;
    private final String model;
    private final String query;
    private final String apiKey;
    private final String synapsysKey;
    private final String serviceEndpoint;
    private final HttpClient httpClient;

    public Generator(String provider, String model, String query, String apiKey, String synapsysKey) {
        this(provider, model, query, apiKey, synapsysKey, "http://localhost:8080/api/generate");
    }

    public Generator(String provider, String model, String query, String apiKey, String synapsysKey,
            String serviceEndpoint) {
        this.provider = provider;
        this.model = model;
        this.query = query;
        this.apiKey = apiKey;
        this.synapsysKey = synapsysKey;
        this.serviceEndpoint = serviceEndpoint;
        this.httpClient = HttpClient.newHttpClient();
    }

    public String generate() {
        try {
            SynapSysRequest request = new SynapSysRequest(provider, model, query, apiKey);
            String jsonBody = request.serialize();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(serviceEndpoint))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + synapsysKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"" + escapeJson(e.getMessage()) + "\"}";
        }
    }

    private static String escapeJson(String s) {
        if (s == null)
            return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
