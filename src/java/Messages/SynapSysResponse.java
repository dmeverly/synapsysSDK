package java.Messages;

import java.util.Map;

public class SynapSysResponse {
    public boolean ok;
    public String provider;
    public String model;
    public String requestId;
    public String output;
    public Map<String, Object> raw;
    public Map<String, Object> error;

    public SynapSysResponse() {}
}