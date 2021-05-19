package com.recipeapp.data.network.response

data class RecipeDetailResponse(
    val aggregateLikes: Int,
    val analyzedInstructions: List<AnalyzedInstructionDetail>,
    val cheap: Boolean,
    val creditsText: String,
    val cuisines: List<Any>,
    val dairyFree: Boolean,
    val diets: List<String>,
    val dishTypes: List<String>,
    val extendedIngredients: List<ExtendedIngredientDetail>,
    val gaps: String,
    val glutenFree: Boolean,
    val healthScore: Double,
    val id: Int,
    val image: String,
    val imageType: String,
    val instructions: String,
    val license: String,
    val lowFodmap: Boolean,
    val nutrition: Nutrition,
    val occasions: List<Any>,
    val originalId: Any,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String,
    val summary: String,
    val sustainable: Boolean,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int,
    val winePairing: WinePairingDetail
)

data class AnalyzedInstructionDetail(
    val name: String,
    val steps: List<StepDetail>
)

data class ExtendedIngredientDetail(
    val aisle: String,
    val amount: Double,
    val consistency: String,
    val id: Int,
    val image: String,
    val measures: Measures,
    val meta: List<String>,
    val metaInformation: List<String>,
    val name: String,
    val nameClean: String,
    val original: String,
    val originalName: String,
    val originalString: String,
    val unit: String
)

data class Nutrition(
    val caloricBreakdown: CaloricBreakdown,
    val flavanoids: List<Flavanoid>,
    val ingredients: List<IngredientX>,
    val nutrients: List<NutrientX>,
    val properties: List<Property>,
    val weightPerServing: WeightPerServing
)

data class WinePairingDetail(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<ProductMatche>
)

data class StepDetail(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val length: Length,
    val number: Int,
    val step: String
)

data class Equipment(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String
)

data class Ingredient(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String
)

data class CaloricBreakdown(
    val percentFat: Double,
    val percentCarbs: Double,
    val percentProtein: Double
)

data class Flavanoid(
    val amount: Double,
    val name: String,
    val title: String,
    val unit: String
)

data class IngredientX(
    val amount: Double,
    val id: Int,
    val name: String,
    val nutrients: List<Nutrient>,
    val unit: String
)

data class NutrientX(
    val amount: Double,
    val name: String,
    val percentOfDailyNeeds: Double,
    val title: String,
    val unit: String
)

data class Property(
    val amount: Double,
    val name: String,
    val title: String,
    val unit: String
)

data class WeightPerServing(
    val amount: Int,
    val unit: String
)

data class Nutrient(
    val amount: Double,
    val name: String,
    val title: String,
    val unit: String
)

data class ProductMatche(
    val averageRating: Double,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val link: String,
    val price: String,
    val ratingCount: Double,
    val score: Double,
    val title: String
)