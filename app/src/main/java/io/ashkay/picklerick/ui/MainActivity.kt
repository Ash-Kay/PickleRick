package io.ashkay.picklerick.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        binding.search.addTextChangedListener {
            viewModel.search(it.toString())
        }
    }

    private fun setupRecyclerView() {
        characterAdapter = CharacterAdapter()
        binding.recyclerView.adapter = characterAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val state = viewModel.uiState.value

                if (state is Success) {
                    val last =
                        (binding.recyclerView.layoutManager as LinearLayoutManager?)?.findLastVisibleItemPosition()

                    if (last == state.data.size - 1) {
                        println()
                        viewModel.loadMore()
                    }
                }
            }
        })
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
                            //Note: toList needed as it list reference same, items are not updated :(
                            characterAdapter.submitList(it.data.toList())
                        }
                    }
                }
            }
        }
    }
}