import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode

data class SynapSysRequest(
    @JsonProperty("provider") val provider: String,
    @JsonProperty("model") val model: String,
    @JsonProperty("query") val query: JsonNode,
    @JsonProperty("version") val version: String = "1",
    @JsonProperty("requestId") val requestId: String? = null,
    @JsonProperty("context") val context: JsonNode? = null,
    @JsonProperty("options") val options: JsonNode? = null,
    @JsonProperty("providerOptions") val providerOptions: JsonNode? = null
)