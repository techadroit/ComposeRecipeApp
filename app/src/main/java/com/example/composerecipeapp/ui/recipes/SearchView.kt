package com.example.composerecipeapp.ui.recipes

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import com.example.composerecipeapp.util.fullScreen
import com.google.android.material.color.MaterialColors

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
                    .focusModifier()
                    .onFocusChanged {
                    },
                leadingIcon = { SearchIcon(onFocus = false, {}, {}) },
                placeholder = { Text(text = "Enter Text To Search") },
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchBar(navController: NavHostController, searchViewModel: SearchViewModel) {

    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val view = LocalView.current

    val textState = remember { mutableStateOf(TextFieldValue()) }
    val onFocus = remember {
        mutableStateOf(false)
    }

    fun onSearchFocus() {
        navController.navigate("search")
        onFocus.value = true
    }

    fun onClearFocus() {
        textState.value = TextFieldValue("")
        onFocus.value = false
        navController.popBackStack()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                searchViewModel.dispatch(SearchEvent(it.text))
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusModifier()
                .onFocusChanged {
                    if (it.isFocused)
                        onSearchFocus()
                },
            singleLine = true,
            leadingIcon = {
                SearchIcon(onFocus = onFocus.value, {
                    softwareKeyboardController?.hide()
                    onSearchFocus()
                }, {
                    softwareKeyboardController?.hide()
                    onClearFocus()
                })
            },
            placeholder = { Text(text = "Enter Text To Search") },
            keyboardActions = KeyboardActions(onDone = {
                val text = textState.value.text
                navController.navigate("recipes/$text")
                softwareKeyboardController?.hideSoftwareKeyboard()
                view.clearFocus()
                textState.value = TextFieldValue("")
                onFocus.value = false
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary
            )
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun SearchView(navController: NavHostController, searchViewModel: SearchViewModel) {

    val state = searchViewModel.stateEmitter.collectAsState().value
    val focusManager = LocalFocusManager.current

    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it })
    ) {
        if (state.list.isEmpty())
            EmptySearchView()
        KeywordList(list = state.list) {
            navController.navigate("recipes/$it")
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
        Text("Search result will appear here")
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
    Icon(transition.targetState, contentDescription = "Search", modifier = Modifier.clickable {
        if (onFocus) onBackClick() else onSearchClick()
    })
}



