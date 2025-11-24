# Help: Set following environment variables before running terraform commands:
#  export TF_VAR_launchdarkly_access_token="token"
#  export TF_VAR_project_key="proj_key"
#  export TF_VAR_env_key="env_key"
variable "launchdarkly_access_token" {
  type        = string
  description = "LaunchDarkly API Access Token"
}

variable "project_key" {
  type        = string
  description = "Existing LaunchDarkly project key"
  default     = "default"
}

variable "env_key" {
  type        = string
  description = "Existing environment key (ex: dev)"
  default     = "test"
}
