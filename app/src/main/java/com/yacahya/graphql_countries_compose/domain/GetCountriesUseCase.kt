package com.yacahya.graphql_countries_compose.domain

class GetCountriesUseCase(
    private val countryClient: CountryClient
) {
    suspend fun execute(): List<SimpleCountry> {
        return countryClient
            .getCountries()
            .sortedBy { it.name }
    }
}