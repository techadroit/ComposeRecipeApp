//package com.recipeapp.view.viewmodel
//
//import androidx.lifecycle.MutableLiveData
//import com.example.composerecipeapp.core.Resource
//import com.example.composerecipeapp.core.platform.ViewState
//import com.example.composerecipeapp.core.usecase.FlowUseCase
//import com.example.composerecipeapp.core.viewmodel.BaseViewModel
//import com.example.composerecipeapp.core.viewmodel.State
//import com.example.composerecipeapp.domain.usecases.LoadSavedRecipeUsecase
//import com.example.composerecipeapp.ui.pojo.RecipeModel
//
//class FragmentShowSavedRecipesViewmodel(initalState: SaveRecipeState) :
//    BaseViewModel<SaveRecipeState>(initalState) {
//
//    val liveData = MutableLiveData<ViewState>()
//    lateinit var usecase: LoadSavedRecipeUsecase
//
//    fun loadSavedRecipes() {
//        setState {
//            copy(event = Resource.Loading)
//        }
//        usecase(FlowUseCase.None()).catch {
//            handleRecipesFailure(this as Failure)
//        }.collectIn(viewModelScope) {
//            handleOnSuccessForLoadingRecipesFromDatabase(it)
//        }
//    }
//
//    private fun handleRecipesFailure(failure: Failure) {
//        setState {
//            copy(event = Resource.Error(data = null, error = failure))
//        }
//    }
//
//    private fun handleOnSuccessForLoadingRecipesFromDatabase(list: List<RecipeModel>) {
//        setState {
//            copy(event = Resource.Success(list))
//        }
//    }
//}
//
//data class SaveRecipeState(val event: Resource<Any>? = Resource.Uninitialized) : State
//
