package com.capstone.mageiras.data.dummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class DummyData {

    @Parcelize
    data class Recipes(val picture: String, val title: String, val cookingTime: String): Parcelable {
    }

    val dummyRecipes: ArrayList<Recipes> = arrayListOf(
        Recipes(
            "https://img-global.cpcdn.com/recipes/962fdfc636d35cb1/680x482cq70/gado-gado-surabaya-gado2-siram-foto-resep-utama.jpg",
            "Gado-Gado",
            "20"
        ),
        Recipes(
            "https://img-global.cpcdn.com/recipes/df29e9f4c38facdc/400x400cq70/photo.jpg",
            "Gado-Gado",
            "20"
        ),
        Recipes(
            "https://awsimages.detik.net.id/api/wm/2024/02/14/resep-gado-gado-siram_169.jpeg?wid=54&w=650&v=1&t=jpeg",
            "Gado-Gado",
            "20"
        ),
        Recipes(
            "https://awsimages.detik.net.id/community/media/visual/2022/10/08/gado-gado_11.png?w=1200",
            "Gado-Gado",
            "20"
        ),
    )

    fun getDummyRecipesData() = dummyRecipes

    @Parcelize
    data class Ingredients(val name: String, val amount: String) : Parcelable {
    }

    val dummyIngredients: ArrayList<Ingredients> = arrayListOf(
        Ingredients(
            "Bawang Putih",
            "2 siung"
        ),
        Ingredients(
            "Bawang Merah",
            "2 siung"
        ),
        Ingredients(
            "Kunyit",
            "1 ruas"
        ),
        Ingredients(
            "Kemiri",
            "2 butir"
        ),
    )

    fun getDummyIngredientsData() = dummyIngredients
}