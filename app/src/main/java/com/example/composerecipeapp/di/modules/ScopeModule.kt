package com.example.composerecipeapp.di.modules

import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.scopes.StateManagerCoroutineScopeImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ScopeModule {

    @Provides
    fun provideStateManagerScope() : StateManagerCoroutineScope = StateManagerCoroutineScopeImpl()
}