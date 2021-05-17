package com.example.composerecipeapp.ui.recipes

import com.example.composerecipeapp.core.Resource
import com.example.composerecipeapp.core.network.NetworkHandler
import com.example.composerecipeapp.core.usecase.UseCase
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.core.viewmodel.Event
import com.example.composerecipeapp.core.viewmodel.State
import com.example.composerecipeapp.data.network.response.RecipeSearchResponse
import com.example.composerecipeapp.data.network.response.toRecipes
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.domain.usecases.SearchRecipeUsecase
import com.example.composerecipeapp.ui.pojo.RecipeModel
import kotlinx.coroutines.launch

data class RecipesState(
    val data: Resource<*> = Resource.Uninitialized,
    val isFetchingRecipes: Boolean = false,
    val recipeList: MutableList<RecipeModel> = mutableListOf()
) : State

open class RecipeEvent : Event
data class LoadRecipes(val query: String = "chicken") : RecipeEvent()

class RecipesViewModel : BaseViewModel<RecipesState, RecipeEvent>(RecipesState()) {
    var offset = 0
    private fun loadRecipes() {
        val recipeApi = NetworkHandler.getRecipeService()
        val remoteRepository = RecipeRepository(recipeApi)
        val searchRecipeUsecase = SearchRecipeUsecase(remoteRepository)

        viewModelScope.launch {
            withState { currentState ->
                if (currentState.isFetchingRecipes)
                    return@withState
                setState {
                    copy(isFetchingRecipes = true)
                }
                searchRecipeUsecase(SearchRecipeUsecase.Param(query = "chicken", offset = offset)) {
                    setState {
                        copy(isFetchingRecipes = false)
                    }
                    offset++
                    setState {
                        it.either({
                            RecipesState(data = Resource.Error(error = UseCase.None()))
                        }, { response ->
                            parseResponse(response, copy())
                        })
                    }
                }
            }
        }
    }

    fun parseResponse(response: RecipeSearchResponse, currentState: RecipesState): RecipesState {
        val recipeList = response.toRecipes()
        val curRecipeList = currentState.recipeList
        curRecipeList.addAll(recipeList)
        return currentState.copy(data = Resource.Success(recipeList), recipeList = curRecipeList)
    }

    override fun onEvent(event: RecipeEvent) {
        when (event) {
            is LoadRecipes -> loadRecipes()
            else -> {
            }
        }
    }

}