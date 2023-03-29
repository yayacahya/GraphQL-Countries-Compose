package com.yacahya.graphql_countries_compose.domain

data class SimpleCountry(
    val code: String,
    val name: String,
    val emoji: String,
    val capital: String
)