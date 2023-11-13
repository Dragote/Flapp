package com.dragote.feature.settings.domain

import com.dragote.shared.stats.domain.StatsRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ClearProgressUseCaseTest {

    private val statsRepository: StatsRepository = mock()
    private val useCase = ClearProgressUseCase(statsRepository)

    @Test
    fun `invoke EXPECT repository clear progress`() = runTest {
        useCase.invoke()

        verify(statsRepository).clearProgress()
    }
}