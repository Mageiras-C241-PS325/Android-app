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
    )

    fun getDummyRecipesData() = dummyRecipes

    @Parcelize
    data class Ingredients(val name: String, val amount: String, val picture: String) : Parcelable {
    }

    val dummyIngredients: ArrayList<Ingredients> = arrayListOf(
        Ingredients(
            "Bawang Putih",
            "2 siung",
            "https://umsu.ac.id/health/wp-content/uploads/2023/12/khasiat-luar-biasa-bawang-putih-untuk-kesehatan.jpg"
        ),
        Ingredients(
            "Bawang Putih",
            "2 siung",
            "https://umsu.ac.id/health/wp-content/uploads/2023/12/khasiat-luar-biasa-bawang-putih-untuk-kesehatan.jpg"
        ),
        Ingredients(
            "Bawang Putih",
            "2 siung",
            "https://umsu.ac.id/health/wp-content/uploads/2023/12/khasiat-luar-biasa-bawang-putih-untuk-kesehatan.jpg"
        ),
        Ingredients(
            "Bawang Putih",
            "2 siung",
            "https://umsu.ac.id/health/wp-content/uploads/2023/12/khasiat-luar-biasa-bawang-putih-untuk-kesehatan.jpg"
        ),
        Ingredients(
            "Bawang Putih",
            "2 siung",
            "https://umsu.ac.id/health/wp-content/uploads/2023/12/khasiat-luar-biasa-bawang-putih-untuk-kesehatan.jpg"
        ),
        Ingredients(
            "Bawang Putih",
            "2 siung",
            "https://umsu.ac.id/health/wp-content/uploads/2023/12/khasiat-luar-biasa-bawang-putih-untuk-kesehatan.jpg"
        ),
        Ingredients(
            "Bawang Putih",
            "2 siung",
            "https://umsu.ac.id/health/wp-content/uploads/2023/12/khasiat-luar-biasa-bawang-putih-untuk-kesehatan.jpg"
        ),
        Ingredients(
            "Bawang Putih",
            "2 siung",
            "https://umsu.ac.id/health/wp-content/uploads/2023/12/khasiat-luar-biasa-bawang-putih-untuk-kesehatan.jpg"
        ),
        Ingredients(
            "Bawang Merah",
            "2 siung",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQud7gu-Nlkv1XiOHBPXLN2QXKRk3Xdow0Hg&s"
        ),
        Ingredients(
            "Kunyit",
            "1 ruas",
            "https://umsu.ac.id/artikel/wp-content/uploads/2023/07/Kunyit-650x375.jpg"
        ),
        Ingredients(
            "Kemiri",
            "2 butir",
            "https://res.cloudinary.com/dk0z4ums3/image/upload/v1671590469/attached_image/kemiri-ketahui-kandungan-dan-manfaatnya-untuk-kesehatan.jpg"
        ),
    )

    fun getDummyIngredientsData() = dummyIngredients
}