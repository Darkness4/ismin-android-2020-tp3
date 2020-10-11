package com.ismin.android.data.datasources

import com.ismin.android.data.models.BookModel
import com.ismin.android.data.models.PaginatedDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookshelfDataSource {
    companion object {
        const val BASE_URL = "https://bookshelf-marc-nguyen.cleverapps.io/"
        const val CONTENT_TYPE = "application/json; charset=utf-8"
    }

    @POST("books")
    suspend fun addBook(@Body book: BookModel)

    @GET("books?limit=100")
    suspend fun getAllBooks(): PaginatedDto<BookModel>

    @DELETE("books/{title}")
    suspend fun removeBook(@Path("title") title: String): BookModel?

    @DELETE("books")
    suspend fun clear()
}
