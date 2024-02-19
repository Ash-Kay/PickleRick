package io.ashkay.picklerick.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.ashkay.picklerick.domain.Repository
import io.ashkay.picklerick.models.CharacterModelResponse
import io.ashkay.picklerick.models.Info
import io.ashkay.picklerick.ui.MainUiState.Success
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun loadCharacters() = runTest {
        //setup
        val page = 1
        val characterModelResponse = CharacterModelResponse(
            Info(
                1,
                "",
                1,
                ""
            ), emptyList()
        )
        whenever(repository.getCharacters(page, null)).thenReturn(characterModelResponse)

        //trigger
        viewModel.loadCharacters(page)

        //assert
        verify(repository, times(2)).getCharacters(page, null)
        val state = viewModel.uiState.first()
        assert(state is Success)
    }

    @Test
    fun loadMore() {
    }

    @Test
    fun search() {
    }
}