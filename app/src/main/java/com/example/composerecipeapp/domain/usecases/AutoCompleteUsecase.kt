package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.NewFlowUseCase
import com.example.composerecipeapp.data.repositories.NewRecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AutoCompleteUsecase(val repository: NewRecipeRepository) :
    NewFlowUseCase<List<String>, String>() {
    override fun run(params: String): Flow<List<String>> {
        return repository.searchKeyword(params).map {
            it.map { it.title }
        }
    }
}
