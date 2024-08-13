package com.example.assignment3

object MovieDataSource {
    fun getRecommendations(category: String): List<Movie> {
        return listOf(
            Movie("Inception", "Action", R.drawable.poster_inception, "A mind-bending thriller with stunning visuals."),
            Movie("The Godfather", "Drama", R.drawable.poster_godfather, "A gripping tale of a powerful crime family."),
            Movie("The Dark Knight", "Action", R.drawable.poster_dark_knight, "A dark and intense superhero film with an iconic villain."),
            Movie("Pulp Fiction", "Crime", R.drawable.poster_pulp_fiction, "A quirky and nonlinear crime saga.")
            //add more movies in here
        ).filter { it.genre.equals(category, ignoreCase = true) }
    }
}