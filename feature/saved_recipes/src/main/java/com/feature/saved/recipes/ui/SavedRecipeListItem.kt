//package com.feature.saved.recipes.ui
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Card
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.domain.common.pojo.RecipeModel
//import com.feature.common.OnClick
//import com.feature.common.ui.common_views.CookingTime
//import com.feature.common.ui.common_views.SaveIcon
//import com.feature.common.ui.common_views.Servings
//import com.skydoves.landscapist.glide.GlideImage
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SavedRecipeListItem(
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
//                Text(text = recipe.title, style = MaterialTheme.typography.bodyMedium)
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
