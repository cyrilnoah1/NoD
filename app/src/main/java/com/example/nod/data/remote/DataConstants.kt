package com.example.nod.data.remote

import com.example.nod.BuildConfig

// Base URL for fetching the news information using the News API.
const val BASE_URL = BuildConfig.SERVER_URL
// API key required for fetching information from the News API.
const val API_KEY = BuildConfig.NEWS_API_KEY

// Current API version for the endpoints.
const val API_VERSION_V2 = "v2"

// Key constants.
const val COUNTRY_CODE_KEY = "country"
const val SOURCES_KEY = "sources"
const val NEWS_API_KEY = "apiKey"