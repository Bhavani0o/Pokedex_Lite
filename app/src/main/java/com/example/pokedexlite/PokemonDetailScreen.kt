package com.example.pokedexlite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    navController: NavController
) {

    var pokemon by remember {
        mutableStateOf<PokemonDetail?>(null)
    }

    LaunchedEffect(pokemonName) {

        try {
            pokemon =
                RetrofitClient.api.getPokemonDetails(pokemonName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // =========================
    // LOADING
    // =========================

    if (pokemon == null) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CircularProgressIndicator()

            Text(
                text = "Loading Pokémon...",
                modifier = Modifier.padding(top = 12.dp)
            )
        }

    } else {

        val data = pokemon!!

        // Get first Pokémon type
        val mainType =
            data.types.firstOrNull()?.type?.name ?: "normal"

        val mainColor =
            pokemonTypeColor(mainType)

        val backgroundColor =
            pokemonTypeLightColor(mainType)


        // =========================
        // MAIN SCREEN
        // =========================

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),

            contentPadding = PaddingValues(
                bottom = 30.dp
            )
        ) {

            item {

                // =========================
                // BACK BUTTON
                // =========================

                Text(
                    text = "←  Back",

                    color = mainColor,

                    style =
                        MaterialTheme.typography.titleMedium,

                    fontWeight =
                        FontWeight.Bold,

                    modifier = Modifier
                        .padding(
                            start = 20.dp,
                            top = 25.dp,
                            bottom = 12.dp
                        )
                        .clickable {

                            navController.popBackStack()
                        }
                )


                // =========================
                // HERO CARD
                // =========================

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),

                    shape =
                        RoundedCornerShape(32.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                mainColor
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 18.dp,
                                bottom = 25.dp
                            ),

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        // POKÉDEX NUMBER

                        Text(
                            text = "#${
                                data.id
                                    .toString()
                                    .padStart(3, '0')
                            }",

                            color =
                                Color.White.copy(
                                    alpha = 0.85f
                                ),

                            fontWeight =
                                FontWeight.Bold
                        )


                        // POKÉMON IMAGE

                        AsyncImage(
                            model =
                                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${data.id}.png",

                            contentDescription =
                                data.name,

                            modifier =
                                Modifier.size(250.dp)
                        )


                        // POKÉMON NAME

                        Text(
                            text =
                                data.name.replaceFirstChar {
                                    it.uppercase()
                                },

                            color =
                                Color.White,

                            style =
                                MaterialTheme.typography
                                    .headlineLarge,

                            fontWeight =
                                FontWeight.ExtraBold
                        )


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )


                        // TYPE BADGES

                        Row(
                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            data.types.forEach {

                                TypeBadge(
                                    type =
                                        it.type.name
                                )
                            }
                        }
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )


                // =========================
                // ABOUT CARD
                // =========================

                DetailCard(
                    title = "About",
                    color = mainColor
                ) {

                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.SpaceEvenly
                    ) {

                        InfoItem(
                            title = "Height",
                            value =
                                "${data.height / 10.0} m"
                        )

                        InfoItem(
                            title = "Weight",
                            value =
                                "${data.weight / 10.0} kg"
                        )
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )


                // =========================
                // ABILITIES CARD
                // =========================

                DetailCard(
                    title = "Abilities",
                    color = mainColor
                ) {

                    data.abilities.forEach {

                        Card(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 5.dp
                                    ),

                            shape =
                                RoundedCornerShape(18.dp),

                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        backgroundColor
                                )
                        ) {

                            Text(
                                text =
                                    it.ability.name
                                        .replaceFirstChar {
                                                c ->
                                            c.uppercase()
                                        },

                                color =
                                    mainColor,

                                style =
                                    MaterialTheme.typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold,

                                modifier =
                                    Modifier.padding(16.dp)
                            )
                        }
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )


                // =========================
                // BASE STATS CARD
                // =========================

                DetailCard(
                    title = "Base Stats",
                    color = mainColor
                ) {

                    data.stats.forEach {

                        StatRow(
                            name =
                                it.stat.name,

                            value =
                                it.base_stat,

                            color =
                                mainColor
                        )
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(20.dp)
                )
            }
        }
    }
}


// ==========================================
// DETAIL CARD
// ==========================================

@Composable
fun DetailCard(
    title: String,
    color: Color,
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

        shape =
            RoundedCornerShape(26.dp),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
    ) {

        Column(
            modifier =
                Modifier.padding(20.dp)
        ) {

            Text(
                text = title,

                color = color,

                style =
                    MaterialTheme.typography
                        .titleLarge,

                fontWeight =
                    FontWeight.ExtraBold
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            content()
        }
    }
}


// ==========================================
// TYPE BADGE
// ==========================================

@Composable
fun TypeBadge(
    type: String
) {

    Box(
        modifier =
            Modifier
                .clip(
                    RoundedCornerShape(50.dp)
                )
                .background(
                    Color.White.copy(
                        alpha = 0.25f
                    )
                )
                .padding(
                    horizontal = 18.dp,
                    vertical = 8.dp
                )
    ) {

        Text(
            text =
                type.replaceFirstChar {
                    it.uppercase()
                },

            color =
                Color.White,

            fontWeight =
                FontWeight.Bold
        )
    }
}


// ==========================================
// INFO ITEM
// ==========================================

@Composable
fun InfoItem(
    title: String,
    value: String
) {

    Column(
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = title,

            style =
                MaterialTheme.typography
                    .labelMedium
        )

        Spacer(
            modifier =
                Modifier.height(5.dp)
        )

        Text(
            text = value,

            style =
                MaterialTheme.typography
                    .titleMedium,

            fontWeight =
                FontWeight.Bold
        )
    }
}


// ==========================================
// STAT ROW
// ==========================================

@Composable
fun StatRow(
    name: String,
    value: Int,
    color: Color
) {

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 7.dp)
    ) {

        Row(
            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Text(
                text =
                    name.replaceFirstChar {
                        it.uppercase()
                    },

                fontWeight =
                    FontWeight.Medium
            )

            Text(
                text = value.toString(),

                color = color,

                fontWeight =
                    FontWeight.ExtraBold
            )
        }


        Spacer(
            modifier =
                Modifier.height(6.dp)
        )


        // BACKGROUND OF STAT BAR

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(9.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(
                        Color.LightGray.copy(
                            alpha = 0.35f
                        )
                    )
        ) {

            // COLORED STAT BAR

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(
                            (value / 150f)
                                .coerceIn(
                                    0f,
                                    1f
                                )
                        )
                        .height(9.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .background(color)
            )
        }
    }
}


// ==========================================
// POKÉMON TYPE COLORS
// ==========================================

fun pokemonTypeColor(
    type: String
): Color {

    return when (type.lowercase()) {

        "fire" ->
            Color(0xFFF4511E)

        "water" ->
            Color(0xFF1E88E5)

        "grass" ->
            Color(0xFF43A047)

        "electric" ->
            Color(0xFFF9A825)

        "psychic" ->
            Color(0xFFD81B60)

        "ice" ->
            Color(0xFF00ACC1)

        "dragon" ->
            Color(0xFF5E35B1)

        "dark" ->
            Color(0xFF424242)

        "fairy" ->
            Color(0xFFEC407A)

        "fighting" ->
            Color(0xFFE53935)

        "poison" ->
            Color(0xFF8E24AA)

        "ground" ->
            Color(0xFF8D6E63)

        "rock" ->
            Color(0xFF6D4C41)

        "ghost" ->
            Color(0xFF5E548E)

        "bug" ->
            Color(0xFF7CB342)

        "flying" ->
            Color(0xFF5C6BC0)

        "steel" ->
            Color(0xFF78909C)

        else ->
            Color(0xFF757575)
    }
}


// ==========================================
// LIGHT BACKGROUND COLORS
// ==========================================

fun pokemonTypeLightColor(
    type: String
): Color {

    return when (type.lowercase()) {

        "fire" ->
            Color(0xFFFFE5DC)

        "water" ->
            Color(0xFFE0F0FF)

        "grass" ->
            Color(0xFFE3F5E5)

        "electric" ->
            Color(0xFFFFF7D6)

        "psychic" ->
            Color(0xFFFFE1ED)

        "ice" ->
            Color(0xFFE0F7FA)

        "dragon" ->
            Color(0xFFEDE4FF)

        "dark" ->
            Color(0xFFE8E8E8)

        "fairy" ->
            Color(0xFFFFE4EF)

        "poison" ->
            Color(0xFFF2E0F7)

        "fighting" ->
            Color(0xFFFFE1E1)

        "ground" ->
            Color(0xFFF2E8E3)

        "rock" ->
            Color(0xFFEDE5E1)

        "ghost" ->
            Color(0xFFE9E6F5)

        "bug" ->
            Color(0xFFEAF5DD)

        "flying" ->
            Color(0xFFE7E9FA)

        "steel" ->
            Color(0xFFE8EEF0)

        else ->
            Color(0xFFF4F4F4)
    }
}