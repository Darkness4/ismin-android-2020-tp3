package com.ismin.android.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ismin.android.data.models.BookModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(books: List<BookModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg book: BookModel)

    @Query("DELETE FROM Book")
    suspend fun clear()

    @Delete
    suspend fun delete(book: BookModel)

    @Query("SELECT * FROM Book")
    suspend fun getAllBooks(): List<BookModel>

    @Query("SELECT * FROM Book")
    fun watchAllBooks(): Flow<List<BookModel>>
}
