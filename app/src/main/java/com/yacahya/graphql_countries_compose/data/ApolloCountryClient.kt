package com.yacahya.graphql_countries_compose.data

import com.apollographql.apollo3.ApolloClient
import com.yacahya.CountriesQuery
import com.yacahya.CountryQuery
import com.yacahya.graphql_countries_compose.domain.CountryClient
import com.yacahya.graphql_countries_compose.domain.DetailedCountry
import com.yacahya.graphql_countries_compose.domain.SimpleCountry

class ApolloCountryClient(
    private val apolloClient: ApolloClient
): CountryClient {

    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map {
                it.toSimpleCountry()
            }
            ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}