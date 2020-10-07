package com.ismin.android.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ismin.android.entities.Book
import com.ismin.android.services.Bookshelf

class MainViewModel : ViewModel() {
    private val _bookshelf = Bookshelf()
    private val _books = MutableLiveData<List<Book>>(emptyList())
    val books: LiveData<List<Book>>
        get() = _books

    fun addBook(book: Book) {
        _bookshelf.addBook(book)
        _books.value = _bookshelf.getAllBooks()
    }

    fun removeBook(book: Book) {
        _bookshelf.removeBook(book.title)
        _books.value = _bookshelf.getAllBooks()
    }

    fun clearAllBooks() {
        _bookshelf.clear()
        _books.value = _bookshelf.getAllBooks()
    }

    private val _navigateToCreateBook = MutableLiveData<Unit>()
    val navigateToCreateBook: LiveData<Unit>
        get() = _navigateToCreateBook

    fun navigateToCreateBook() {
        _navigateToCreateBook.value = Unit
    }

    fun navigateToCreateBookDone() {
        _navigateToCreateBook.value = null
    }
}
