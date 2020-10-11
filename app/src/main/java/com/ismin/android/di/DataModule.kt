package com.ismin.android.di

import android.content.Context
import androidx.room.Room
import com.ismin.android.data.database.BookDao
import com.ismin.android.data.datasources.BookshelfDataSource
import com.ismin.android.data.datasources.LocalDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {
    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideBookshelfDataSource(client: Lazy<OkHttpClient>): BookshelfDataSource {
        return Retrofit.Builder()
            .baseUrl(BookshelfDataSource.BASE_URL)
            .client(client.get())
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }.asConverterFactory(
                    BookshelfDataSource.CONTENT_TYPE.toMediaType()
                )
            )
            .build()
            .create(BookshelfDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient() = OkHttpClient()

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): LocalDataSource {
        return Room.databaseBuilder(
            context,
            LocalDataSource::class.java,
            "app.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideBookDao(local: Lazy<LocalDataSource>): BookDao {
        return local.get().bookDao()
    }
}
