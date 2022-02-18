package com.jerryokafor.compose.domain.usecase

import com.jerryokafor.compose.domain.model.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @Author <Author>
 * @Project <Project>
 */

interface LoginUseCase {
    suspend operator fun invoke(code: String): Flow<Resource<Unit>>
}