package com.yacahya.graphql_countries_compose.domain

class GetCountryUseCase(
    private val countryClient: CountryClient
) {
    suspend fun execute(code: String): DetailedCountry? {
        return countryClient
            .getCountry(code)
    }
}