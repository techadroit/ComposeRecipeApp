//package com.recipeapp.view.viewmodel
//
//import com.recipeapp.core.collectIn
//import com.recipeapp.core.exception.Failure
//import com.recipeapp.core.platform.BaseMVIViewmodel
//import com.recipeapp.core.platform.RecipeState
//import com.recipeapp.data.network.response.RecipeDetailResponse
//import com.recipeapp.domain.usecases.GetRecipeDetailUsecase
//import kotlinx.coroutines.flow.catch
//
//class RecipeDetailViewModel(
//    initialState: RecipeDetailState = RecipeDetailState()
//) : BaseMVIViewmodel<RecipeDetailState>(initialState) {
//    lateinit var usecase: GetRecipeDetailUsecase
//
//    fun getRecipeDetailForId(id: String) {
//        setState {
//            copy(isLoading = true)
//        }
//        usecase(GetRecipeDetailUsecase.Param(id = id))
//            .catch {
//                handleFailureResponse(this as Failure)
//            }
//            .collectIn(viewModelScope) {
//                handleSuccessResponse(it)
//            }
//    }
//
//    fun handleFailureResponse(failure: Failure) {
//        setState {
//            copy(isLoading = false, recipeDetail = null)
//        }
//    }
//
//    fun handleSuccessResponse(recipeDetailResponse: RecipeDetailResponse) {
//        setState {
//            copy(isLoading = false, recipeDetail = recipeDetailResponse)
//        }
//    }
//}
//
//data class RecipeDetailState(
//    val isLoading: Boolean = false,
//    val recipeDetail: RecipeDetailResponse? = null
//) :
//    RecipeState()
