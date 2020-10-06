package com.ismin.android.services

import com.ismin.android.entities.Book

class Bookshelf {
    private val storage = mutableMapOf<String, Book>()

    fun addBook(book: Book) = this.storage.set(book.title, book)

    fun getBook(title: String): Book? = this.storage[title]

    fun getAllBooks(): List<Book> = this.storage.values.sortedBy { book -> book.title }

    fun getBooksOf(author: String): List<Book> = this.storage.filter { it.value.author == author }.values.sortedBy { book -> book.title }

    fun getTotalNumberOfBooks(): Int = this.storage.size
}
