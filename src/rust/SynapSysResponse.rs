use serde::{Deserialize, Serialize};
use serde_json::Value;

#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct SynapSysResponse {
    pub ok: bool,
    pub provider: String,
    pub model: String,

    #[serde(skip_serializing_if = "Option::is_none")]
    pub request_id: Option<String>,

    #[serde(skip_serializing_if = "Option::is_none")]
    pub output: Option<String>,

    #[serde(skip_serializing_if = "Option::is_none")]
    pub raw: Option<Value>,

    #[serde(skip_serializing_if = "Option::is_none")]
    pub error: Option<Value>,
}