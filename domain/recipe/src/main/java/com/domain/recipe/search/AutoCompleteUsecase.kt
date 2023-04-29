package com.domain.recipe.search

import com.core.platform.usecase.NewFlowUseCase
import com.data.repository.repositories.NewRecipeRepository
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
