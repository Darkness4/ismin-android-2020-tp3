package com.ismin.android.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ismin.android.core.mappers.EntityMappable
import com.ismin.android.domain.entities.Book
import org.joda.time.DateTime

class CreateBookViewModel : ViewModel(), EntityMappable<Book> {
    /* Field */
    val author = MutableLiveData("")
    val title = MutableLiveData("")
    val date = MutableLiveData(DateTime.now())

    private val _showDatePicker = MutableLiveData<Unit?>()
    val showDatePicker: LiveData<Unit?>
        get() = _showDatePicker

    fun showDatePicker() {
        _showDatePicker.value = Unit
    }

    fun showDatePickerDone() {
        _showDatePicker.value = null
    }

    /* Navigation */
    private val _navigateToBookList = MutableLiveData<Book?>()
    val navigateToBookList: LiveData<Book?>
        get() = _navigateToBookList

    private fun navigateToBookList(book: Book) {
        _navigateToBookList.value = book
    }

    fun navigateToBookListDone() {
        _navigateToBookList.value = null
    }

    fun saveButtonPushed() {
        if (isValid) {
            navigateToBookList(asEntity())
        } else {
            showValidationError()
        }
    }

    /* Validation */
    private val _showValidationError = MutableLiveData<Unit?>()
    val showValidationError: LiveData<Unit?>
        get() = _showValidationError

    private fun showValidationError() {
        _showValidationError.value = Unit
    }

    fun showValidationErrorDone() {
        _showValidationError.value = null
    }

    private val _titleError = MutableLiveData(true)
    val titleError: LiveData<Boolean>
        get() = _titleError

    private val _authorError = MutableLiveData(true)
    val authorError: LiveData<Boolean>
        get() = _authorError

    private val isValid: Boolean
        get() = _titleError.value == false && _authorError.value == false

    fun validate() {
        validateTitle()
        validateAuthor()
    }

    fun validateTitle() {
        _titleError.value = title.value.isNullOrBlank()
    }

    fun validateAuthor() {
        _authorError.value = author.value.isNullOrBlank()
    }

    override fun asEntity() = Book(title.value!!, author.value!!, date.value!!)
}
