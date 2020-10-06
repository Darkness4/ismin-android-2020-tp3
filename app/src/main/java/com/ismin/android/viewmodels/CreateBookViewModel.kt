package com.ismin.android.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ismin.android.entities.Book
import org.joda.time.DateTime

class CreateBookViewModel : ViewModel() {
    val author = MutableLiveData("")
    val title = MutableLiveData("")
    private val _date = MutableLiveData<DateTime>()
    val date: LiveData<DateTime>
        get() = _date

    fun setDate(date: DateTime) {
        _date.value = date
    }

    fun getBook(): Book {
        return Book(title.value!!, author.value!!, date.value!!)
    }
}
