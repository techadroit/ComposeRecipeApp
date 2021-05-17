package com.example.composerecipeapp.data.network.response

data class RecipeDetailResponse(
    val aggregateLikes: Int,
    val analyzedInstructions: List<Any>,
    val cheap: Boolean,
    val cookingMinutes: Int,
    val creditsText: String,
    val cuisines: List<String>,
    val dairyFree: Boolean,
    val diets: List<String>,
    val dishTypes: List<String>,
    val extendedIngredients: List<ExtendedIngredient>,
    val gaps: String,
    val glutenFree: Boolean,
    val healthScore: Double,
    val id: String,
    val image: String,
    val imageType: String,
    val instructions: Any,
    val ketogenic: Boolean,
    val lowFodmap: Boolean,
    val nutrition: Nutrition,
    val occasions: List<Any>,
    val preparationMinutes: Int,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String,
    val sustainable: Boolean,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int,
    val whole30: Boolean,
    val winePairing: WinePairing
)




data class Nutrition(
    val caloricBreakdown: CaloricBreakdown,
    val ingredients: List<Ingredient>,
    val nutrients: List<NutrientX>,
    val weightPerServing: WeightPerServing
)

data class CaloricBreakdown(
    val percentCarbs: Double,
    val percentFat: Double,
    val percentProtein: Double
)

data class Ingredient(
    val amount: Double,
    val name: String,
    val nutrients: List<Nutrient>?,
    val unit: String
)

data class Nutrient(
    val amount: Double,
    val name: String,
    val unit: String
)

data class NutrientX(
    val amount: Double,
    val percentOfDailyNeeds: Double,
    val title: String,
    val unit: String
)

data class WeightPerServing(
    val amount: Int,
    val unit: String
)

