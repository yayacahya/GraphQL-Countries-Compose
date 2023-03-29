package com.yacahya.graphql_countries_compose.data

import com.yacahya.CountriesQuery
import com.yacahya.CountryQuery
import com.yacahya.graphql_countries_compose.domain.DetailedCountry
import com.yacahya.graphql_countries_compose.domain.SimpleCountry

fun CountryQuery.Country.toDetailedCountry(): DetailedCountry {
    return DetailedCountry(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital ?: "No capital",
        currency = currency ?: "No currency",
        languages = languages.map { it.name },
        continent = continent.name
    )
}

fun CountriesQuery.Country.toSimpleCountry(): SimpleCountry {
    return SimpleCountry(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital ?: "No capital"
    )
}