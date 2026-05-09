use serde::{Deserialize, Serialize};
use serde_json::Value;

#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct SynapSysRequest {
    pub provider: String,
    pub model: String,
    pub query: Value,

    #[serde(default = "default_version")]
    pub version: String,

    #[serde(skip_serializing_if = "Option::is_none")]
    pub request_id: Option<String>,

    #[serde(skip_serializing_if = "Option::is_none")]
    pub context: Option<Value>,

    #[serde(skip_serializing_if = "Option::is_none")]
    pub options: Option<Value>,

    #[serde(skip_serializing_if = "Option::is_none")]
    pub provider_options: Option<Value>,
}

fn default_version() -> String {
    "1".to_string()
}