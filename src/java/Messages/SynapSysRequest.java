package java.Messages;

public class SynapSysRequest {
    private final String provider;
    private final String model;
    private final Object query;
    private final String apiKey;

    public SynapSysRequest(String provider, String model, Object query, String apiKey) {
        this.provider = provider;
        this.model = model;
        this.query = query;
        this.apiKey = apiKey;
    }

    public String serialize() {
        String queryJson = (query instanceof String)
                ? "\"" + escapeJson((String) query) + "\""
                : query.toString();
        return "{\"provider\":\"" + escapeJson(provider) +
                "\",\"model\":\"" + escapeJson(model) +
                "\",\"query\":" + queryJson +
                ",\"apiKey\":\"" + escapeJson(apiKey) + "\"}";
    }

    private static String escapeJson(String s) {
        if (s == null)
            return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
