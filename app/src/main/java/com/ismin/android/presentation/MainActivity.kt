package com.ismin.android.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ismin.android.R
import com.ismin.android.databinding.ActivityMainBinding
import com.ismin.android.domain.repositories.BookRepository
import com.ismin.android.presentation.viewmodels.MainViewModel
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var bookRepository: Lazy<BookRepository>

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> { MainViewModel.Factory(bookRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.networkStatus.observe(this) { result ->
            result?.doOnFailure {
                Toast.makeText(
                    this,
                    it.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
            viewModel.manualRefreshDone()
        }

        setContentView(binding.root)

        val navController = findNavController(R.id.my_nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.my_nav_host_fragment)
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear -> {
                viewModel.clearAllBooks()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
