package com.example.composerecipeapp.ui.recipe_search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composerecipeapp.R
import com.example.composerecipeapp.platform.navigation.screens.RecipeListIntent
import com.example.composerecipeapp.platform.navigation.screens.SearchScreenIntent
import com.example.composerecipeapp.platform.navigation.screens.SearchViewIntent
import com.example.composerecipeapp.ui.Dispatch
import com.example.composerecipeapp.ui.Navigate
import com.example.composerecipeapp.ui.PopBackStack
import com.example.composerecipeapp.ui.provider.MainViewNavigator
import com.example.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import com.example.composerecipeapp.util.fullScreen
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.recipe_search.SearchEvent
import com.example.composerecipeapp.viewmodel.recipe_search.SearchTextEvent
import com.example.composerecipeapp.viewmodel.recipe_search.SearchViewModel

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    ComposeRecipeAppTheme(darkTheme = false) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                    },
                leadingIcon = { SearchIcon(onFocus = false, {}, {}) },
                placeholder = { Text(stringResource(id = R.string.text_to_search)) },
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchBarContainer(
    searchViewModel: SearchViewModel
) {
    val mainViewNavigator = MainViewNavigator.current
    SearchBar(
        navigate = {
            if (it is SearchViewIntent)
                mainViewNavigator.navigateTo(it)
            else
                mainViewNavigator.navigateTo(it)
        },
        dispatch = { searchViewModel.dispatch(it) }
    ) {
        mainViewNavigator.popBackStack()
    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    navigate: Navigate,
    dispatch: Dispatch<SearchEvent>,
    popBackStack: PopBackStack
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val view = LocalView.current

    val textState = remember { mutableStateOf(TextFieldValue()) }
    val onFocus = remember {
        mutableStateOf(false)
    }

    fun onSearchFocus() {
        navigate(SearchViewIntent())
        onFocus.value = true
    }

    fun onClearFocus() {
        textState.value = TextFieldValue("")
        onFocus.value = false
        popBackStack()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .testTag("search_bar"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                dispatch(SearchTextEvent(it.text))
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused)
                        onSearchFocus()
                },
            singleLine = true,
            leadingIcon = {
                SearchIcon(
                    onFocus = onFocus.value,
                    {
                        softwareKeyboardController?.hide()
                        onSearchFocus()
                    },
                    {
                        softwareKeyboardController?.hide()
                        onClearFocus()
                    }
                )
            },
            placeholder = { Text(stringResource(id = R.string.text_to_search)) },
            keyboardActions = KeyboardActions(
                onDone = {
                    val text = textState.value.text
                    navigate(SearchScreenIntent(text = text))
                    softwareKeyboardController?.hide()
                    view.clearFocus()
                    textState.value = TextFieldValue("")
                    onFocus.value = false
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onSurface
            )
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun SearchView(searchViewModel: SearchViewModel) {

    val mainViewNavigator = MainViewNavigator.current
    val state = searchViewModel.observeState()
    val focusManager = LocalFocusManager.current

    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it })
    ) {
        if (state.list.isEmpty())
            EmptySearchView()
        KeywordList(list = state.list) {
            mainViewNavigator.navigateTo(navItems = RecipeListIntent(it))
            focusManager.clearFocus()
        }
    }
}

@Composable
fun EmptySearchView() {
    Column(
        modifier = Modifier.fullScreen(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.search_result))
    }
}

@Composable
fun KeywordList(list: List<String>, onEvent: (String) -> Unit) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        itemsIndexed(list) { index, keyword ->
            Text(text = keyword, modifier = Modifier.clickable { onEvent.invoke(keyword) })
            Spacer(Modifier.height(4.dp))
        }
    }
}

@Composable
fun SearchIcon(onFocus: Boolean, onSearchClick: () -> Unit, onBackClick: () -> Unit) {
    var icon = remember {
        Icons.Default.Search
    }
    if (onFocus) icon = Icons.Default.ArrowBack
    val transition = updateTransition(targetState = icon, label = "transition_icon")
    Icon(
        transition.targetState, contentDescription = if (onFocus) "Search" else "Back",
        modifier = Modifier.clickable {
            if (onFocus) onBackClick() else onSearchClick()
        }
    )
}
