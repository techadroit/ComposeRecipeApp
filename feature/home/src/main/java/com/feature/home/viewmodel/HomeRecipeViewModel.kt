package com.feature.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.core.platform.exception.toFailure
import com.domain.recipe.cuisines.RecipesForSelectedCuisines
import com.feature.common.IoDispatcher
import com.feature.common.ui.error_screen.ErrorSideEffect
import com.feature.home.state.HomeRecipeEvent
import com.feature.home.state.HomeRecipeState
import com.feature.home.state.LoadRecipeEvent
import com.feature.home.state.RefreshHomeEvent
import com.feature.home.state.ViewAllRecipes
import com.feature.home.state.ViewAllViewEffect
import com.feature.home.state.ViewRecipeDetail
import com.feature.home.state.ViewRecipesDetailViewEffect
import com.feature.home.state.add
import com.feature.home.state.initialState
import com.feature.home.state.showLoading
import com.state_manager.extensions.collectInScope
import com.state_manager.managers.StateEventManager
import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.scopes.StateManagerCoroutineScopeImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeRecipeViewModel @Inject constructor(
    val recipeWithCuisine: RecipesForSelectedCuisines,
    private val initialHomeState: HomeRecipeState,
    val scope: StateManagerCoroutineScope = StateManagerCoroutineScopeImpl(),
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : StateEventManager<HomeRecipeState, HomeRecipeEvent>(initialHomeState,scope) {

    init {
        dispatch(LoadRecipeEvent)
    }
    override fun onEvent(event: HomeRecipeEvent, state: HomeRecipeState) {
        when (event) {
            is LoadRecipeEvent -> loadRecipes()
            is RefreshHomeEvent -> refresh()
            is ViewAllRecipes -> onViewAllRecipes(event.cuisine)
            is ViewRecipeDetail -> onViewRecipeDetail(event.recipeId)
        }
    }

    private fun refresh() {
        setState {
            initialState()
        }
        loadRecipes()
    }

    private fun onViewRecipeDetail(recipeId: String) {
        postSideEffect { ViewRecipesDetailViewEffect(recipeId) }
    }

    private fun onViewAllRecipes(cuisine: String) {
        postSideEffect { ViewAllViewEffect(cuisine) }
    }

    private fun loadRecipes() {
        setState {
            showLoading(true)
        }
        recipeWithCuisine()
            .flowOn(dispatcher)
            .catch {
                setState { showLoading(false) }
                postSideEffect { ErrorSideEffect(it.toFailure()) }
            }
            .collectInScope(viewModelScope){
            setState {
                add(it)
                    .showLoading(false)
            }
        }
    }
}
