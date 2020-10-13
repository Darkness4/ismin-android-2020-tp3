package com.ismin.android.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ismin.android.core.result.Result
import com.ismin.android.domain.entities.Book
import com.ismin.android.domain.repositories.BookRepository
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val bookRepository: Lazy<BookRepository>) : ViewModel() {
    init {
        refreshBooks()
    }

    /* Book Repository Requests */
    val books = bookRepository.get().watchAllBooks()
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.Default)

    private val _networkStatus = MutableLiveData<Result<Unit>>()
    val networkStatus: LiveData<Result<Unit>>
        get() = _networkStatus

    private fun refreshBooks() {
        viewModelScope.launch {
            _networkStatus.value = bookRepository.get().getAllBooks().map { Unit }
        }
    }

    fun addBook(book: Book) {
        viewModelScope.launch {
            _networkStatus.value = bookRepository.get().addBook(book)
        }
    }

    fun removeBook(book: Book) {
        viewModelScope.launch {
            _networkStatus.value = bookRepository.get().removeBook(book)
        }
    }

    fun clearAllBooks() {
        viewModelScope.launch {
            _networkStatus.value = bookRepository.get().clear()
        }
    }

    /* Navigation */
    private val _navigateToCreateBook = MutableLiveData<Unit?>()
    val navigateToCreateBook: LiveData<Unit?>
        get() = _navigateToCreateBook

    fun navigateToCreateBook() {
        _navigateToCreateBook.value = Unit
    }

    fun navigateToCreateBookDone() {
        _navigateToCreateBook.value = null
    }

    /* Refresh logic */
    private val _isManuallyRefreshing = MutableLiveData(false)
    val isManuallyRefreshing
        get() = _isManuallyRefreshing

    fun manualRefresh() {
        _isManuallyRefreshing.value = true
        refreshBooks()
    }

    fun manualRefreshDone() {
        _isManuallyRefreshing.value = false
    }

    class Factory(
        private val bookRepository: Lazy<BookRepository>,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(bookRepository) as T
        }
    }
}
