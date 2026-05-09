package java.Messages;

public class SynapSysRequest {
    public String provider;
    public String model;
    public Object query;
    public String version = "1";
    public String requestId;
    public Object context;
    public Object options;
    public Object providerOptions;

    public SynapSysRequest(String provider, String model, Object query) {
        this.provider = provider;
        this.model = model;
        this.query = query;
    }
}
