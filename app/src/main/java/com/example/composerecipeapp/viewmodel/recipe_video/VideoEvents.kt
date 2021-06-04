package com.example.composerecipeapp.viewmodel.recipe_video

import com.example.composerecipeapp.core.viewmodel.AppEvent

interface VideoEvents : AppEvent

data class LoadVideos(var isPaginate: Boolean = false, val query: String = "Chicken") : VideoEvents

