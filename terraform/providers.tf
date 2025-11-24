terraform {
  required_providers {
    launchdarkly = {
      source  = "launchdarkly/launchdarkly"
      version = "2.26.0"
    }
  }
}

provider "launchdarkly" {
  access_token = var.launchdarkly_access_token
}
