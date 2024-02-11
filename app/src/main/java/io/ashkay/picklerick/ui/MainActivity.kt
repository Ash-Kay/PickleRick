package io.ashkay.picklerick.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.ashkay.picklerick.databinding.ActivityMainBinding
import io.ashkay.picklerick.ui.MainUiState.Error
import io.ashkay.picklerick.ui.MainUiState.Loading
import io.ashkay.picklerick.ui.MainUiState.Success
import io.ashkay.picklerick.ui.adapter.CharacterAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeState()
    }

    private fun setupRecyclerView() {
        characterAdapter = CharacterAdapter()
        binding.recyclerView.adapter = characterAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity)
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        Loading -> {
                            binding.status.visibility = View.VISIBLE
                            binding.status.text = "Loading"
                        }

                        is Error -> {
                            binding.status.visibility = View.VISIBLE
                            binding.status.text = it.throwable.message
                        }

                        is Success -> {
                            binding.status.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            characterAdapter.submitList(it.data)
                        }
                    }
                }
            }
        }
    }
}