package com.ismin.android.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ismin.android.entities.Book
import com.ismin.android.services.Bookshelf

class MainViewModel : ViewModel() {
    private val bookshelf = Bookshelf()

    private val _books = MutableLiveData<List<Book>>(emptyList())
    val books: LiveData<List<Book>>
        get() = _books

    fun addBook(book: Book) {
        bookshelf.addBook(book)
        _books.value = bookshelf.getAllBooks()
    }
}