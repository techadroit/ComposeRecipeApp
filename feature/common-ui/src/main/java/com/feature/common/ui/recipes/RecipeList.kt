//package com.feature.common.ui.recipes
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.material3.Card
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.key
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import com.archerviewmodel.events.ArcherEvent
//import com.domain.common.pojo.RecipeModel
//import com.feature.common.Dispatch
//import com.feature.common.Navigate
//import com.feature.common.ui.common_views.CookingTime
//import com.feature.common.ui.common_views.PaginationLoading
//import com.feature.common.ui.common_views.SaveIcon
//import com.feature.common.ui.common_views.Servings
//import com.skydoves.landscapist.glide.GlideImage
//
//
//@Composable
//fun RecipeList(
//    recipeList: List<RecipeModel>,
//    dispatch: Dispatch<ArcherEvent>,
//    navigate: Navigate,
//    showPaginationLoading: Boolean,
//    keyword: String,
//    endOfList: Boolean
//) {
//    val scrollState = rememberLazyListState()
//    Column(modifier = Modifier.fillMaxSize()) {
//        LazyColumn(
//            state = scrollState,
//            contentPadding = PaddingValues(bottom = 80.dp),
//            content = {
//                itemsIndexed(recipeList) { index, recipe ->
//                    key(index) {
//                        RecipeListItem(
//                            recipe = recipe,
//                            index = index,
//                            {
//                                navigate(RecipeDetailIntent(detailId = it.toString()))
//                            },
//                            {
//                                dispatch(SaveRecipeEvent(it))
//                            },
//                            {
//                                dispatch(RemoveSavedRecipeEvent(it))
//                            }
//                        )
//                    }
//                    val totalItem = scrollState.layoutInfo.totalItemsCount
//                    if (index == (totalItem - 1)) {
//                        LaunchedEffect(true) {
//                            if (!endOfList)
//                                dispatch(
//                                    LoadRecipes(
//                                        isPaginate = true,
//                                        query = keyword
//                                    )
//                                )
//                        }
//                    }
//                }
//                if (showPaginationLoading) {
//                    item {
//                        PaginationLoading()
//                    }
//                }
//                if (endOfList && recipeList.isEmpty()) {
//                    item {
//                        Text(stringResource(id = R.string.no_result))
//                    }
//                }
//            }
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RecipeListItem(
//    recipe: RecipeModel,
//    index: Int,
//    onRowClick: OnClick<Int>,
//    onSaveClick: OnClick<RecipeModel>,
//    onRemoveClick: OnClick<RecipeModel>
//) {
//    Card(
//        onClick = { onRowClick.invoke(recipe.id) },
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(
//                horizontal = 12.dp,
//                vertical = if (index == 0) 8.dp else 4.dp
//            )
//    ) {
//        Row {
//            GlideImage(
//                imageModel = recipe.imageUrl,
//                modifier = Modifier
//                    .height(120.dp)
//                    .width(120.dp),
//            )
//            Column(modifier = Modifier.padding(8.dp)) {
//                Text(text = recipe.title, style = MaterialTheme.typography.displayLarge)
//                CookingTime(time = recipe.cookingTime.toString())
//                Servings(serving = recipe.servings.toString())
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
//                    SaveIcon(isSaved = recipe.isSaved) {
//                        if (it)
//                            onSaveClick(recipe)
//                        else
//                            onRemoveClick(recipe)
//                    }
//                }
//            }
//        }
//    }
//}
