package com.recipeapp.view.viewmodel

import com.example.composerecipeapp.core.Consumable
import com.example.composerecipeapp.core.Resource
import com.example.composerecipeapp.core.collectIn
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.network.NetworkHandler
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.core.viewmodel.AppEvent
import com.example.composerecipeapp.core.viewmodel.AppState
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.domain.usecases.SaveRecipeUsecase
import com.example.composerecipeapp.domain.usecases.SearchRecipeUsecase
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.recipeapp.core.network.api_service.RecipeApi
import kotlinx.coroutines.flow.catch

class RecipeListViewmodel(initialState: RecipeListState = RecipeListState()) :
    BaseViewModel<RecipeListState, RecipeEvent>(initialState) {

    lateinit var localRepository: RecipeLocalRepository
    var page = 1
    private val repos =
        RecipeRepository(NetworkHandler.getRetrofitInstance().create(RecipeApi::class.java))
    val searchUsecase by lazy { SearchRecipeUsecase(repos) }
    val usecase by lazy { SaveRecipeUsecase(localRepository) }

//    fun saveRecipe(recipeModel: RecipeModel) =
//        usecase(SaveRecipeUsecase.Param(recipeModel))
//            .collectIn(viewModelScope) {
//                onRecipeSaved()
//            }
//
//    private fun onRecipeSaved() {
//        setState {
//            copy(
//                onSavedRecipes = Resource.Success(data = None),
//                sideEffect = Consumable(SideEffect.OnSavedRecipe)
//            )
//        }
//    }

    fun loadRecipes(query: String) =
        withState {
            if (!it.isLoading) {
                setState { this.onLoading() }
                searchRecipe(query = query)
            }
        }

    fun paginate(query: String) =
        withState {
            if (!it.isLoading) {
                page++
                setState { this.onLoading(true) }
                searchRecipe(query = query, isPaginate = true)
            }
        }

    private fun searchRecipe(query: String, isPaginate: Boolean = false) {
        searchUsecase(SearchRecipeUsecase.Param(query = query, offset = page))
            .catch { e ->
                handleFailure(e as Failure, isPaginate = isPaginate)
            }.collectIn(viewModelScope) {
                handleRecipeSearch(it.first, isPaginate, it.second)
            }
    }

    private fun handleFailure(failure: Failure, isPaginate: Boolean = false) {
        setState {
            this.onError(failure = failure)
        }
    }

    private fun handleRecipeSearch(
        recipeList: List<RecipeModel>,
        isPaginate: Boolean,
        endOfItems: Boolean
    ) =
        setState {
            this.onRecipeLoad(isPaginate, endOfItems, recipeList)
        }

    override fun onEvent(event: RecipeEvent,state: RecipeListState) {
        when (event) {
            is LoadRecipes -> if (event.isPaginate) paginate(event.query) else loadRecipes(event.query)
            else -> {
            }
        }
    }
}

sealed class SideEffect {
    object OnSavedRecipe : SideEffect()
}

data class RecipeData(var allRecipes: List<RecipeModel>) {
    operator fun plus(recipeList: List<RecipeModel>): RecipeData {
        val newList = allRecipes + recipeList
        return RecipeData(newList)
    }
}

data class RecipeListState(
    val onSavedRecipes: Resource<*>? = Resource.Uninitialized,
    var recipes: RecipeData = RecipeData(emptyList()),
    var sideEffect: Consumable<SideEffect>? = null,
    val isLoading: Boolean = false,
    val isPaginate: Boolean = false,
    val error: Failure? = null,
    val endOfItems: Boolean = false
) : AppState

open class RecipeEvent : AppEvent
data class LoadRecipes(val query: String, var isPaginate: Boolean = false) :
    RecipeEvent()

fun RecipeListState.onRecipeLoad(
    isPaginate: Boolean,
    endOfItems: Boolean,
    recipeList: List<RecipeModel>
) = this.copy(
    isLoading = false,
    isPaginate = isPaginate,
    recipes = this.recipes + recipeList,
    endOfItems = endOfItems
)

fun RecipeListState.onLoading(isPaginate: Boolean = false) =
    this.copy(isLoading = true, isPaginate = isPaginate)

fun RecipeListState.onError(failure: Failure) = this.copy(error = failure)
