# 🐾 Pokédex Lite

A modern Android Pokédex application built with **Kotlin** and **Jetpack Compose**, using the **PokéAPI** to fetch real-time Pokémon data.

## 📱 About the Project

**Pokédex Lite** is an Android application that allows users to explore Pokémon, search for their favorite Pokémon, and view detailed information such as Pokémon types, abilities, images, and other characteristics.

The project was developed to practice modern Android development concepts including **Jetpack Compose, MVVM architecture, Retrofit, Kotlin Coroutines, StateFlow, and API integration**.

---

## ✨ Features

* 🔎 **Search Pokémon** by name
* 🐾 Browse a list of Pokémon
* 🖼️ Display Pokémon images
* 📋 View detailed Pokémon information
* 🎨 Pokémon type information
* ⚡ View Pokémon abilities
* 🌐 Fetch live data from PokéAPI
* 🔄 Loading state while fetching data
* ❌ Basic error handling
* 📱 Responsive and modern UI
* 🌙 Dark-themed Pokémon-inspired interface
* ⚡ Smooth navigation between Pokémon list and detail screens

---

## 🛠️ Tech Stack

| Technology            | Usage                        |
| --------------------- | ---------------------------- |
| **Kotlin**            | Primary programming language |
| **Jetpack Compose**   | Modern Android UI toolkit    |
| **MVVM**              | Application architecture     |
| **Retrofit**          | REST API communication       |
| **Kotlin Coroutines** | Asynchronous operations      |
| **StateFlow**         | Reactive UI state management |
| **Coil**              | Loading Pokémon images       |
| **Material Design**   | UI components and styling    |
| **PokéAPI**           | Pokémon data source          |
| **Android Studio**    | Development environment      |

---

## 🏗️ Architecture

The application follows the **MVVM (Model–View–ViewModel)** architecture.

```text
                ┌──────────────────┐
                │    Jetpack       │
                │     Compose      │
                │       UI         │
                └────────┬─────────┘
                         │
                         ▼
                ┌──────────────────┐
                │    ViewModel     │
                │                  │
                │  StateFlow       │
                │  Coroutines      │
                └────────┬─────────┘
                         │
                         ▼
                ┌──────────────────┐
                │   Repository     │
                └────────┬─────────┘
                         │
                         ▼
                ┌──────────────────┐
                │    Retrofit      │
                │       API        │
                └────────┬─────────┘
                         │
                         ▼
                ┌──────────────────┐
                │     PokéAPI      │
                └──────────────────┘
```

### Project Structure

```text
PokedexLite/
│
├── app/
│   └── src/
│       └── main/
│           └── java/
│               └── com.example.pokedexlite/
│
│               ├── model/
│               │   ├── Pokemon.kt
│               │   └── PokemonDetail.kt
│               │
│               ├── network/
│               │   ├── PokemonApi.kt
│               │   └── RetrofitInstance.kt
│               │
│               ├── repository/
│               │   └── PokemonRepository.kt
│               │
│               ├── viewmodel/
│               │   └── PokemonViewModel.kt
│               │
│               ├── ui/
│               │   ├── PokemonListScreen.kt
│               │   └── PokemonDetailScreen.kt
│               │
│               └── MainActivity.kt
│
└── README.md
```

> The exact package/file structure may vary depending on the final version of the project.

---

## 🌐 API

This project uses **PokéAPI**, a free and open RESTful API providing Pokémon data.

**API:** https://pokeapi.co/

The application retrieves information such as:

* Pokémon names
* Pokémon images
* Types
* Abilities
* Base information
* Pokémon details

---

## 🚀 Getting Started

### Prerequisites

Before running the project, make sure you have:

* Android Studio installed
* JDK configured
* Android SDK installed
* An Android emulator or physical Android device
* Internet connection

### Installation

**1. Clone the repository**

```bash
git clone https://github.com/Bhavani0o/Pokedex_Lite.git
```

**2. Open the project**

Open the cloned project in **Android Studio**.

**3. Sync Gradle**

Allow Android Studio to download and configure the required dependencies.

**4. Connect a device**

You can either:

* Connect a physical Android device with USB debugging enabled, or
* Start an Android Emulator.

**5. Run the application**

Click the **Run ▶** button in Android Studio.

---

## 🔄 How It Works

1. The user opens the Pokédex application.
2. The app requests Pokémon data from PokéAPI.
3. Retrofit handles the API communication.
4. The Repository manages data retrieval.
5. The ViewModel processes and exposes the data using StateFlow.
6. Jetpack Compose observes the state.
7. Pokémon information is displayed dynamically.
8. Selecting a Pokémon opens its detailed information screen.

---

## 💡 What I Learned

Through this project, I gained practical experience with:

* Building Android applications using Kotlin
* Creating modern UIs with Jetpack Compose
* Implementing MVVM architecture
* Consuming REST APIs
* Working with Retrofit
* Using Kotlin Coroutines
* Managing UI state using StateFlow
* Loading remote images using Coil
* Creating navigation between screens
* Handling API loading and error states
* Structuring an Android project for maintainability

---

## 🔮 Future Improvements

Possible improvements for future versions include:

* ❤️ Add favorite Pokémon
* 💾 Add local database support using Room
* 📶 Add offline caching
* 🔍 Improve advanced search and filtering
* ⚔️ Add Pokémon statistics charts
* 🏆 Add Pokémon comparison
* 📊 Add base-stat visualizations
* 🌐 Add pagination for larger Pokémon lists
* 🎵 Add Pokémon sound effects
* ✨ Add animations and transitions

---

## 📚 Resources

* **PokéAPI:** https://pokeapi.co/
* **Kotlin:** https://kotlinlang.org/
* **Jetpack Compose:** https://developer.android.com/compose
* **Retrofit:** https://square.github.io/retrofit/
* **Coil:** https://coil-kt.github.io/coil/

---

## 👩‍💻 Developer

**Bhavani Asritha**

B.Tech Computer Science & Engineering
Lovely Professional University

Interested in **Data Science, Artificial Intelligence, Android Development, and Software Development**.

---

## ⭐ Project

If you found this project interesting, consider giving the repository a ⭐ on GitHub!

**GitHub Repository:**
https://github.com/Bhavani0o/Pokedex_Lite

---

## 📄 License

This project was created for **educational and portfolio purposes**.
