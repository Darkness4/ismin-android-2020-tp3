package com.ismin.android.domain.repositories

import com.ismin.android.core.result.Result
import com.ismin.android.domain.entities.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun addBook(book: Book): Result<Unit>

    suspend fun getAllBooks(): Result<List<Book>>

    fun watchAllBooks(): Flow<Result<List<Book>>>

    suspend fun removeBook(book: Book): Result<Unit>

    suspend fun clear(): Result<Unit>
}
