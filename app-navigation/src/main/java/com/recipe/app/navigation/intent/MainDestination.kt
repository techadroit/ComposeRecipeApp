package com.recipe.app.navigation.intent

import android.os.Bundle
import com.core.navigtion.intent.DestinationIntent

data class UserInterestIntent(override val screenName: String = getScreenName()) :
    DestinationIntent {
    companion object {
        fun getScreenName() = "user_interest"
    }
}

data class MainViewIntent(override val screenName: String = getScreenName()) :
    DestinationIntent {
    companion object {
        fun getScreenName() = "main_view"
    }
}

data class VideoPlayerIntent(override val screenName: String = getScreenName(), val youTubeId: String? = null) :
    DestinationIntent {

    override fun toRoute(): String {
        return "recipe/videos/${youTubeId}"
    }

    companion object {
        fun getScreenName() = "recipe/videos/{youtube_id}"
    }
}

data class SearchScreenIntent(override val screenName: String = getScreenName(), val text: String? = null) :
    DestinationIntent {

    override fun toRoute(): String {
        return "recipe/search/${text}"
    }

    companion object {
        fun getScreenName() = "recipe/search/{text}"
    }
}

data class RecipeDetailIntent(
    override val screenName: String = getScreenName(),
    val detailId: String? = null
) : DestinationIntent {

    override fun toRoute() = "recipe_details/${detailId}"

    data class Arguments constructor(val recipeId: String)

    companion object {
        fun getScreenName() = "recipe_details/{recipe_id}"

        fun getArguments(bundle: Bundle?): Arguments {
            return Arguments(
                bundle?.getString("recipe_id")
                    ?: throw NullPointerException("Cannot Open RecipeDetail without recipe id")
            )
        }
    }
}
