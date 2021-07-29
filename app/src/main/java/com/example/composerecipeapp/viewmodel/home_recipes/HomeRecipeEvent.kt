package com.example.composerecipeapp.viewmodel.home_recipes

import com.archerviewmodel.events.ArcherEvent

sealed class HomeRecipeEvent() : ArcherEvent

object LoadRecipeEvent : HomeRecipeEvent()
