package com.recipe.app.navigation.intent

import android.os.Bundle
import com.core.navigtion.intent.DestinationIntent

data class RecipeListIntent(
    override val screenName: String = getScreenName(),
    val cuisine: String? = null
) : DestinationIntent {

    override fun toRoute(): String = "recipes/${cuisine}"

    data class Arguments constructor(val cuisine: String)

    companion object {
        fun getScreenName(): String = "recipes/{cuisine}"

        fun getArguments(bundle: Bundle?): RecipeDetailIntent.Arguments {
            return RecipeDetailIntent.Arguments(
                bundle?.getString("cuisine")
                    ?: throw NullPointerException("Cannot Open RecipeList without keyword")
            )
        }
    }
}

data class HomeViewIntent(override val screenName: String = getScreenName()) :
    DestinationIntent {

    companion object {
        fun getScreenName(): String = "home_view"
    }
}

data class RecipeVideoListIntent(override val screenName: String = getScreenName()) :
    DestinationIntent {

    companion object {
        fun getScreenName(): String = "recipe_video"
    }
}

data class SavedRecipeIntent(override val screenName: String = getScreenName()) :
    DestinationIntent {

    companion object {
        fun getScreenName(): String = "saved_recipe"
    }
}

data class SearchViewIntent(override val screenName: String = getScreenName()) :
    DestinationIntent {

    companion object {
        fun getScreenName(): String = "search_view"
    }
}

data class SettingsViewIntent(override val screenName: String = getScreenName()) :
    DestinationIntent {

    companion object {
        fun getScreenName(): String = "settings_view"
    }
}
