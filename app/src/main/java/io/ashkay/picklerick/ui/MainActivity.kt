package io.ashkay.picklerick.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.ashkay.picklerick.databinding.ActivityMainBinding
import io.ashkay.picklerick.ui.MainUiState.Error
import io.ashkay.picklerick.ui.MainUiState.Loading
import io.ashkay.picklerick.ui.MainUiState.Success
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                viewModel.uiState.collect {
                    when(it) {
                        Loading -> {
                            binding.text.text = "Loading"
                        }
                        is Error -> {
                            binding.text.text = it.throwable.message
                        }
                        is Success -> {
                            binding.text.text = it.data.toString()
                        }
                    }
                }
            }
        }
    }
}