package com.ismin.android.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ismin.android.core.mappers.EntityMappable
import com.ismin.android.domain.entities.Book
import org.joda.time.DateTime

class CreateBookViewModel : ViewModel(), EntityMappable<Book> {
    val author = MutableLiveData("")
    val title = MutableLiveData("")
    private val _date = MutableLiveData<DateTime>()

    fun setDate(date: DateTime) {
        _date.value = date
    }

    private val _navigateToBookList = MutableLiveData<Book>()
    val navigateToBookList: LiveData<Book>
        get() = _navigateToBookList

    fun navigateToBookList(book: Book) {
        _navigateToBookList.value = book
    }

    fun navigateToBookListDone() {
        _navigateToBookList.value = null
    }

    override fun asEntity() = Book(title.value!!, author.value!!, _date.value!!)
}
