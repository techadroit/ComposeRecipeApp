package com.example.composerecipeapp.viewmodel.main

import com.archerviewmodel.events.ArcherEvent

sealed class MainViewEvent : ArcherEvent

object LoadSettings : MainViewEvent()
