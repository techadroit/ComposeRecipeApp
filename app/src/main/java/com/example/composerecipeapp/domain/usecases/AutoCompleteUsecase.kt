package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.FlowUseCase
import com.example.composerecipeapp.data.repositories.RecipeRepository

class AutoCompleteUsecase(val repository: RecipeRepository) : FlowUseCase<List<String>, String>() {
    override suspend fun run(params: String): List<String> {
        return repository.searchKeyword(params, 10).map {
            it.title
        }
    }
}
