package com.ismin.android.data.repositories

import com.ismin.android.core.result.Result
import com.ismin.android.data.database.BookDao
import com.ismin.android.data.datasources.BookshelfDataSource
import com.ismin.android.domain.entities.Book
import com.ismin.android.domain.repositories.BookRepository
import dagger.Lazy
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookDaoLazy: Lazy<BookDao>,
    private val remoteLazy: Lazy<BookshelfDataSource>
) : BookRepository {
    override suspend fun addBook(book: Book): Result<Unit> {
        return try {
            val bookModel = book.asModel()
            remoteLazy.get().addBook(bookModel)
            bookDaoLazy.get().insert(bookModel)
            Result.Success(Unit)
        } catch (e: IOException) {
            Result.Failure(e)
        } catch (e: HttpException) {
            Result.Failure(e)
        }
    }

    override suspend fun getAllBooks(): Result<List<Book>> {
        val bookDao = bookDaoLazy.get()
        return try {
            val books = remoteLazy.get().getAllBooks().results
            bookDao.insert(books)
            Result.Success(books.map { it.asEntity() })
        } catch (e: IOException) {
            Result.Failure(e)
        } catch (e: HttpException) {
            Result.Failure(e)
        }
    }

    override fun watchAllBooks() =
        bookDaoLazy.get().watchAllBooks()
            .map { books -> Result.Success(books.map { it.asEntity() }) }

    override suspend fun removeBook(book: Book): Result<Unit> {
        return try {
            remoteLazy.get().removeBook(book.title)
            bookDaoLazy.get().delete(book.asModel())
            Result.Success(Unit)
        } catch (e: IOException) {
            Result.Failure(e)
        } catch (e: HttpException) {
            Result.Failure(e)
        }
    }

    override suspend fun clear(): Result<Unit> {
        return try {
            remoteLazy.get().clear()
            bookDaoLazy.get().clear()
            Result.Success(Unit)
        } catch (e: IOException) {
            Result.Failure(e)
        } catch (e: HttpException) {
            Result.Failure(e)
        }
    }
}
