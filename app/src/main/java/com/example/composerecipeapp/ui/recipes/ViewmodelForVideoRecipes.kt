//package com.example.composerecipeapp.ui.recipes
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.composerecipeapp.core.Resource
//import com.example.composerecipeapp.core.network.NetworkHandler
//import com.example.composerecipeapp.core.usecase.UseCase
//import com.example.composerecipeapp.data.network.response.toVideoRecipes
//import com.example.composerecipeapp.data.repositories.RecipeRepository
//import com.example.composerecipeapp.domain.usecases.SearchVideoRecipeUsecase
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.launch
//
//data class VideoRecipesState(val data: Resource<*> = Resource.Uninitialized)
//
//class ViewmodelForVideoRecipes : ViewModel() {
//    val stateFlow: MutableStateFlow<VideoRecipesState> = MutableStateFlow(VideoRecipesState())
//
//    fun loadVideoRecipes() {
//        val recipeApi = NetworkHandler.getRecipeService()
//        val remoteRepository = RecipeRepository(recipeApi)
//        val searchRecipeUsecase = SearchVideoRecipeUsecase(remoteRepository)
//
//        viewModelScope.launch {
//            searchRecipeUsecase(SearchVideoRecipeUsecase.Param(query = "chicken")) {
//                stateFlow.value = it.either({
//                    VideoRecipesState(data = Resource.Error(error = UseCase.None()))
//                }, { response ->
//                    VideoRecipesState(data = Resource.Success(data = response.toVideoRecipes()))
//                })
//            }
//        }
//
//    }
//}
