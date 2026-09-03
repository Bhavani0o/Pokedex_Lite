package com.example.pokedexlite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "pokemon_list"
            ) {

                composable("pokemon_list") {

                    PokemonScreen(
                        onPokemonClick = { pokemonName ->

                            navController.navigate(
                                "pokemon_detail/$pokemonName"
                            )
                        }
                    )
                }

                composable("pokemon_detail/{pokemonName}") { backStackEntry ->

                    val pokemonName =
                        backStackEntry.arguments?.getString("pokemonName") ?: ""

                    PokemonDetailScreen(
                        pokemonName = pokemonName,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonScreen(
    onPokemonClick: (String) -> Unit
) {

    var pokemonList by remember {
        mutableStateOf<List<Pokemon>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var searchText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        try {
            val response = RetrofitClient.api.getPokemon()
            pokemonList = response.results
        } catch (e: Exception) {
            e.printStackTrace()
        }

        isLoading = false
    }

    val filteredPokemon = pokemonList.filter {
        it.name.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFF4F6FF)
            )
    ) {

        // COLORFUL HEADER

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFEF5350),
                    RoundedCornerShape(
                        bottomStart = 35.dp,
                        bottomEnd = 35.dp
                    )
                )
                .padding(
                    start = 22.dp,
                    end = 22.dp,
                    top = 35.dp,
                    bottom = 25.dp
                )
        ) {

            Text(
                text = "Pokédex",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "Explore the Pokémon world ✨",
                color = Color.White.copy(alpha = 0.9f),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // SEARCH BAR

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Search Pikachu...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(18.dp)
            )
        }


        if (isLoading) {

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

            if (filteredPokemon.isEmpty()) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "No Pokémon found 😢",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Try another Pokémon name"
                    )
                }

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 18.dp,
                        end = 18.dp,
                        top = 18.dp,
                        bottom = 30.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    items(filteredPokemon) { pokemon ->

                        PokemonCard(
                            pokemon = pokemon,
                            onClick = {
                                onPokemonClick(pokemon.name)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PokemonCard(
    pokemon: Pokemon,
    onClick: () -> Unit
) {

    val pokemonId = pokemon.url
        .trimEnd('/')
        .substringAfterLast('/')

    val cardColor = when (pokemonId.toIntOrNull()?.rem(6)) {

        0 -> Color(0xFFE3F2FD)

        1 -> Color(0xFFFFF3E0)

        2 -> Color(0xFFE8F5E9)

        3 -> Color(0xFFFCE4EC)

        4 -> Color(0xFFF3E5F5)

        else -> Color(0xFFFFFDE7)
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // IMAGE CIRCLE

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        Color.White.copy(alpha = 0.75f)
                    ),
                contentAlignment = Alignment.Center
            ) {

                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png",
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(110.dp),
                    contentScale = ContentScale.Fit
                )
            }


            Spacer(
                modifier = Modifier.width(18.dp)
            )


            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "#${pokemonId.padStart(3, '0')}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = pokemon.name.replaceFirstChar {
                        it.uppercase()
                    },
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "View details →",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}