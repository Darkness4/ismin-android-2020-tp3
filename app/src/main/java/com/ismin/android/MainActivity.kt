package com.ismin.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ismin.android.adapters.BookAdapter
import com.ismin.android.databinding.ActivityMainBinding
import com.ismin.android.entities.Book
import com.ismin.android.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    companion object {
        const val CREATE_BOOK_REQUEST_CODE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.bookList.adapter = BookAdapter()

        setContentView(binding.root)
    }

    fun startBookActivity(view: View) {
        val intent = Intent(this, CreateBookActivity::class.java)

        startActivityForResult(intent, CREATE_BOOK_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CREATE_BOOK_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.getSerializableExtra("book")
                    ?.let { viewModel.addBook(it as Book) }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
