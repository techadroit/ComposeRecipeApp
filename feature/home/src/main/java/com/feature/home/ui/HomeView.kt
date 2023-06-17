package com.feature.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.archerviewmodel.ArcherViewModel
import com.core.navigtion.AppNavigator
import com.core.platform.functional.ViewEffect
import com.feature.common.ui.buttons.RecipeOutlineButton
import com.feature.common.ui.containers.FullScreenBox
import com.core.themes.dimension
import com.core.themes.homeCard
import com.core.themes.homePadding
import com.domain.common.pojo.RecipeModel
import com.domain.recipe.cuisines.RecipeWithCuisine
import com.feature.common.Dispatch
import com.feature.common.OnClick
import com.feature.common.OnUnit
import com.feature.common.observeState
import com.feature.common.ui.common_views.LoadingView
import com.feature.common.ui.common_views.RefreshView
import com.feature.common.ui.recipes.ImageThumbnail
import com.feature.home.state.HomeRecipeEvent
import com.feature.home.state.HomeRecipeState
import com.feature.home.state.LoadingError
import com.feature.home.state.RefreshHomeEvent
import com.feature.home.state.ViewAllRecipes
import com.feature.home.state.ViewAllViewEffect
import com.feature.home.state.ViewRecipeDetail
import com.feature.home.state.ViewRecipesDetailViewEffect
import com.feature.home.viewmodel.HomeRecipeViewModel
import com.feature.recipe.home.R
import com.recipe.app.navigation.intent.RecipeDetailIntent
import com.recipe.app.navigation.intent.RecipeListIntent
import com.recipe.app.navigation.provider.MainViewNavigator
import com.recipe.app.navigation.provider.ParentNavHostController

@Composable
fun HomeScreen(viewModel: HomeRecipeViewModel = hiltViewModel<HomeRecipeViewModel>()) {

    val topLevelNavigator = ParentNavHostController.current
    val mainViewNavigator = MainViewNavigator.current
    val state = viewModel.observeState()
    val viewEffect = state.viewEffect?.consume()

    fun refresh() {
        viewModel.dispatch(RefreshHomeEvent)
    }

    HomeView(state = state, viewModel = viewModel) {
        refresh()
    }

    viewEffect?.let { onViewEffect(it, topLevelNavigator, mainViewNavigator) }
}

@Composable
fun HomeView(state: HomeRecipeState, viewModel: HomeRecipeViewModel, refresh: OnUnit) {

    when {
        state.list.isNotEmpty() ->
            RefreshView(content = {
                HomeViewContent(list = state.list, viewModel = viewModel)
            }) {
                refresh()
            }

        state.isLoadingPage -> LoadingView()
        state.viewEffect?.consume() is LoadingError -> FullScreenBox {
            RecipeOutlineButton.ErrorButton(
                text = "Retry",
                modifier = Modifier.align(Alignment.Center)
            ) {
                refresh()
            }
        }
    }
}

@Composable
fun HomeViewContent(
    list: List<RecipeWithCuisine>,
    viewModel: ArcherViewModel<HomeRecipeState, HomeRecipeEvent>
) {
    LazyColumn(
        content = {
            items(list) {
                Text(
                    text = it.cuisine,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(8.dp)
                )
                RecipeListWithCuisine(recipe = it, viewModel = viewModel)
            }
        }
    )
}

@Composable
fun RecipeListWithCuisine(
    recipe: RecipeWithCuisine,
    viewModel: ArcherViewModel<HomeRecipeState, HomeRecipeEvent>
) {
    val scrollState = rememberLazyListState()
    LazyRow(
        state = scrollState,
        content = {
            itemsIndexed(recipe.recipeList) { index, recipe ->
                key(index) {
                    RecipeItem(
                        recipe = recipe
                    ) {
                        viewModel.dispatch(ViewRecipeDetail(recipeId = it.toString()))
                    }
                }
            }
            item {
                ViewAll {
                    viewModel.dispatch(ViewAllRecipes(recipe.cuisine))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeItem(recipe: RecipeModel, onRowClick: OnClick<Int>) {
    Box(
        modifier = Modifier.homePadding()
    ) {
        Card(
            onClick = { onRowClick(recipe.id) },
            modifier = Modifier
                .homeCard()
                .shadow(shape = RoundedCornerShape(16.dp), elevation = 0.dp)
        ) {
            ConstraintLayout {
                val (thumbnail, title) = createRefs()
                ImageThumbnail(
                    imageUrl = recipe.imageUrl,
                    modifier = Modifier.constrainAs(thumbnail) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(thumbnail.bottom)
                            start.linkTo(thumbnail.start)
                            end.linkTo(thumbnail.end)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                        .padding(MaterialTheme.dimension().paddingSubtitle)
                )
            }
        }
    }
}

@Composable
fun ViewAll(dispatch: Dispatch<Unit>) {
    Card(
        modifier = Modifier
            .homeCard()
            .homePadding()
            .clickable { dispatch(Unit) }
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.view_all),
                style = MaterialTheme.typography.displayLarge,
                maxLines = 2,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun onViewEffect(
    viewEffect: ViewEffect,
    parentNavigation: AppNavigator,
    appMainNavigation: AppNavigator
) {
    when (viewEffect) {
        is ViewAllViewEffect ->
            appMainNavigation.navigateTo(RecipeListIntent(cuisine = viewEffect.cuisine))

        is ViewRecipesDetailViewEffect ->
            parentNavigation.navigateTo(RecipeDetailIntent(detailId = viewEffect.recipeId))

        is LoadingError -> {
            println("there is an error")
        }
    }
}
