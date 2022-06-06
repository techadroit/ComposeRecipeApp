package com.example.composerecipeapp.viewmodel.recipe_list

import com.example.composerecipeapp.BaseUnitTest
import com.example.composerecipeapp.domain.usecases.DeleteSavedRecipe
import com.example.composerecipeapp.domain.usecases.SaveRecipeUsecase
import com.example.composerecipeapp.domain.usecases.SearchRecipeUsecase
import com.example.composerecipeapp.ui.pojo.RecipeModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Before
import org.junit.Test

class RecipeListViewmodelUnitTest : BaseUnitTest() {

    var recipeModel = RecipeModel(1, "Title", 5, "", 45, false)
    private val initialState = RecipeListState(recipes = RecipeData(listOf(recipeModel)))

    @MockK
    lateinit var savedRecipeUsecase: SaveRecipeUsecase

    @MockK
    lateinit var searchUsecase: SearchRecipeUsecase

    @MockK
    lateinit var deleteSavedRecipe: DeleteSavedRecipe

    var viewModel: RecipeListViewmodel? = null

    @After
    fun afterTest() {
        viewModel?.clearResource()
        viewModel = null
        println("I was called after")
    }

    @Before
    fun beforeTest() {
        recipeModel = RecipeModel(1, "Title", 5, "", 45, false)
        viewModel = RecipeListViewmodel(
            initialState,
            savedRecipeUsecase,
            searchUsecase,
            deleteSavedRecipe,
            testContext
        )
        println("I was called before")
    }

    @Test
    fun testLoadRecipes() {
        coEvery { searchUsecase(any()) } returns flow { emit(listOf(recipeModel) to false) }
        executeTest {
            viewModel?.runStateTest(LoadRecipes("query")) { e, s ->
                assert(s.isLoading)
            }
        }
    }

    @Test
    fun testSavedRecipes() {
        coEvery { savedRecipeUsecase(any()) } returns flow { emit(1L) }
        executeTest {
            viewModel?.runStateTest(SaveRecipeEvent(recipeModel)) { e, s ->
                assert(e is SaveRecipeEvent)
                verify(atLeast = 1) { savedRecipeUsecase(any()) }
            }
        }
    }

    @Test
    fun testDelete() {
        coEvery { deleteSavedRecipe(any()) } returns flow { emit(1) }
        executeTest {
            viewModel?.runStateTest(RemoveSavedRecipeEvent(recipeModel)) { e, s ->
                assert(e is RemoveSavedRecipeEvent)
                assert(!s.recipes.isEmpty())
            }
        }
    }

}
