package com.ismin.android.entities

import org.joda.time.DateTime
import java.io.Serializable

data class Book(
    val title: String,
    val author: String,
    val date: DateTime
) : Serializable
