package com.ismin.android.domain.services

import com.ismin.android.domain.entities.Book
import org.joda.time.DateTime

class Bookshelf {
    private val storage = mutableMapOf(
        Pair(
            "The Lord of the Rings",
            Book(
                title = "The Lord of the Rings",
                author = "J. R. R. Tolkien",
                date = DateTime(1954, 2, 15, 0, 0)
            )
        ),
        Pair(
            "The Hobbit",
            Book(
                title = "The Hobbit",
                author = "J. R. R. Tolkien",
                date = DateTime(1937, 9, 21, 0, 0)
            )
        ),
        Pair(
            "À la recherche du temps perdu",
            Book(
                title = "À la recherche du temps perdu",
                author = "Marcel Proust",
                date = DateTime(1927, 1, 1, 0, 0)
            )
        )
    )

    fun addBook(book: Book) = this.storage.set(book.title, book)

    fun getBook(title: String): Book? = this.storage[title]

    fun getAllBooks(): List<Book> = this.storage.values.sortedBy { book -> book.title }

    fun getBooksOf(author: String): List<Book> =
        this.storage.filter { it.value.author == author }.values.sortedBy { book -> book.title }

    fun getTotalNumberOfBooks(): Int = this.storage.size

    fun removeBook(title: String) { this.storage.remove(title) }

    fun clear() = this.storage.clear()
}
