# 1. Enable New Home Layout V2 Feature Flag
resource "launchdarkly_feature_flag" "enable_new_home_layout_v2" {
  project_key     = var.project_key
  key             = "enable-new-home-layout-v2"
  name            = "Enable New Home Layout V2"
  description     = "Controls rollout of the new home page v2."
  temporary       = true
  variation_type  = "boolean"

  # index 0
  variations {
    value = false
    name  = "Old home layout"
  }

  # index 1
  variations {
    value = true
    name  = "New home layout v2"
  }
}

# environment-specific rules
resource "launchdarkly_feature_flag_environment" "new_home_env" {
  flag_id = launchdarkly_feature_flag.enable_new_home_layout_v2.id
  env_key = var.env_key

  on            = true
  off_variation = 0 # when flag is OFF, serve "false" (old layout)

  # RULE 1 — Premium users ALWAYS get new home
  # Examples: curl "http://localhost:8080/api/home?userId=101"
  rules {
    variation = 1 # true

    clauses {
      context_kind = "user"
      attribute    = "segment"
      op           = "in"
      values       = ["premium"]
      negate       = false
    }
  }

  # RULE 2 — Indian free users on mobile get new layout
  # context: location.country == "IN" AND device.deviceType == "mobile"
  # Examples: curl "http://localhost:8080/api/home?userId=102" -H "X-Device-Type: mobile"
  rules {
    variation = 1 # true

    clauses {
      context_kind = "location"
      attribute    = "country"
      op           = "in"
      values       = ["IN"]
      negate       = false
    }

    clauses {
      context_kind = "device"
      attribute    = "deviceType"
      op           = "in"
      values       = ["mobile"]
      negate       = false
    }
  }

  # RULE 3 — US users get a 50% rollout (sticky bucketing)
  # 1: curl "http://localhost:8080/api/home?userId=202" & 2: curl "http://localhost:8080/api/home?userId=203"
  rules {
    # order of weights corresponds to variation indexes [0, 1]
    rollout_weights = [50000, 50000]

    # rollout happens per user, stable & unique
    context_kind = "user"
    bucket_by    = "key"

    clauses {
      context_kind = "location"
      attribute    = "country"
      op           = "in"
      values       = ["US"]
      negate       = false
    }
  }

  # FALLTHROUGH — If none of the above match, serve false (old layout)
  fallthrough {
    variation = 0
  }
}

# 2. enable-offers Flag
resource "launchdarkly_feature_flag" "enable_offers_flag" {
  project_key     = var.project_key
  key             = "enable-offers"
  name            = "Enable Offers"
  description     = "Controls rollout of offers."
  temporary       = true
  variation_type  = "boolean"

  # index 0
  variations {
    value = false
    name  = "Disable Offers"
  }

  # index 1
  variations {
    value = true
    name  = "Enable Offers"
  }
}


resource "launchdarkly_feature_flag_environment" "enable_offers_flag_env" {
  flag_id = launchdarkly_feature_flag.enable_offers_flag.id
  env_key = var.env_key

  on            = true
  off_variation = 0 # when flag is OFF, serve "false"

  # RULE 1 — Premium users ALWAYS have offers enabled
  rules {
    variation = 1 # true

    clauses {
      context_kind = "user"
      attribute    = "segment"
      op           = "in"
      values       = ["premium"]
      negate       = false
    }
  }

  # FALLTHROUGH — If none of the above match, serve false
  fallthrough {
    variation = 0
  }
}

# 3. enable-local-news Flag
resource "launchdarkly_feature_flag" "enable_local_news_flag" {
  project_key     = var.project_key
  key             = "enable-local-news"
  name            = "Enable Local News"
  description     = "Controls rollout of local news."
  temporary       = true
  variation_type  = "boolean"

  # index 0
  variations {
    value = false
    name  = "Disable Local News"
  }

  # index 1
  variations {
    value = true
    name  = "Enable Local News"
  }
}


resource "launchdarkly_feature_flag_environment" "enable_local_news_flag_env" {
  flag_id = launchdarkly_feature_flag.enable_local_news_flag.id
  env_key = var.env_key

  on            = true
  off_variation = 0 # when flag is OFF, serve "false"

  # RULE 1 — Premium users ALWAYS have local news enabled
  rules {
    variation = 1 # true

    clauses {
      context_kind = "location"
      attribute    = "country"
      op           = "in"
      values       = ["IN"]
      negate       = false
    }
  }

  # FALLTHROUGH — If none of the above match, serve false
  fallthrough {
    variation = 0
  }
}

# recommendation algo version flag
resource "launchdarkly_feature_flag" "recommendation_algo_version_flag" {
  project_key     = var.project_key
  key             = "recommendation-algo-version"
  name            = "Recommendation Algo Version"
  description     = "Controls rollout of recommendation algorithm versions."
  temporary       = true
  variation_type  = "string"

  variations {
    value = "v1"
  }

  variations {
    value = "v2"
  }

  variations {
    value = "experiment"
  }
}


resource "launchdarkly_feature_flag_environment" "recommendation_algo_version_flag_env" {
  flag_id = launchdarkly_feature_flag.recommendation_algo_version_flag.id
  env_key = var.env_key

  on            = true
  off_variation = 0 # when flag is OFF, serve "v1"

  # RULE 1 — Premium users ALWAYS have experiment version
  rules {
    variation = 2 # true

    clauses {
      context_kind = "user"
      attribute    = "segment"
      op           = "in"
      values       = ["premium"]
      negate       = false
    }
  }

  # FALLTHROUGH — If none of the above match, serve "v1"
  fallthrough {
    variation = 0
  }
}

# recommendation algo version flag
resource "launchdarkly_feature_flag" "promo_banner_type_flag" {
  project_key     = var.project_key
  key             = "promo-banner-type"
  name            = "Promo Banner Type"
  description     = "Controls rollout of promo banner type"
  temporary       = true
  variation_type  = "string"

  variations {
    value = "none"
  }

  variations {
    value = "diwali"
  }

  variations {
    value = "newyear"
  }
}


resource "launchdarkly_feature_flag_environment" "promo_banner_type_flag_env" {
  flag_id = launchdarkly_feature_flag.promo_banner_type_flag.id
  env_key = var.env_key

  on            = true
  off_variation = 0 # when flag is OFF, serve "none"

  # RULE 1 — Indian users get diwali banner
  rules {
    variation = 1 # true

    clauses {
      context_kind = "location"
      attribute    = "country"
      op           = "in"
      values       = ["IN"]
      negate       = false
    }
  }

  # FALLTHROUGH — If none of the above match, serve "none"
  fallthrough {
    variation = 0
  }
}