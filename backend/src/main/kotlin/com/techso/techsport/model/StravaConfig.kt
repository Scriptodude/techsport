package com.techso.techsport.model

data class StravaConfig(val clientId: Int,
                        val clientSecret: String,
                        val oauthUrl: String,
                        val redirectUrl: String)