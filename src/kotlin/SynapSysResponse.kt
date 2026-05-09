import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class SynapSysResponse(
  val ok: Boolean,
  val provider: String,
  val model: String,
  val requestId: String? = null,
  val output: String? = null,
  val raw: JsonElement? = null,
  val error: JsonElement? = null
)