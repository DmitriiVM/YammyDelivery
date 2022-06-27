package com.dvm.menu_impl.domain.interactor

import com.dvm.menu_api.domain.HintInteractor
import com.dvm.menu_api.domain.model.Hint
import com.dvm.menu_impl.domain.repository.HintRepository
import kotlinx.coroutines.flow.Flow

internal class DefaultHintInteractor(
    private val hintRepository: HintRepository
) : HintInteractor {

    override fun hints(): Flow<List<String>> =
        hintRepository.hints()

    override suspend fun saveHint(hint: Hint) {
        if (hintRepository.hintCount() >= 5) {
            hintRepository.deleteOldest()
        }
        hintRepository.insert(hint)
    }

    override suspend fun delete(hint: String) {
        hintRepository.delete(hint)
    }
}