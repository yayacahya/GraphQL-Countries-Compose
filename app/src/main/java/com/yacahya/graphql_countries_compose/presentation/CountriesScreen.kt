package com.yacahya.graphql_countries_compose.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yacahya.graphql_countries_compose.domain.DetailedCountry
import com.yacahya.graphql_countries_compose.domain.SimpleCountry
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountriesScreen(
    state: CountriesViewModel.CountriesState,
    onSelectCountry: (code: String) -> Unit,
    onDismissCountryDialog: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            state.selectedCountry?.let {
                CountryDialog(
                    country = it,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .padding(16.dp)
                )
            }
        },
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.countries) {country ->
                        CountryItem(
                            country = country,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectCountry(country.code) }
                                .padding(16.dp)
                        )
                    }
                }

                if (state.selectedCountry != null) {
                    scope.launch {
                        if (!sheetState.isVisible) {
                            sheetState.show()
                        } else {
                            sheetState.hide()
                            onDismissCountryDialog()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CountryDialog(
    country: DetailedCountry,
    modifier: Modifier = Modifier
) {
    // For example : English, French, German, etc
    val joinedLanguages = remember(country.languages) {
        country.languages.joinToString()
    }
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = country.emoji,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = country.name,
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Continent : ${country.continent}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Currency : ${country.currency}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Capital : ${country.capital}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Language(s) : $joinedLanguages")
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun CountryItem(
    country: SimpleCountry,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = country.emoji,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = country.name,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = country.capital)
        }
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    )
}