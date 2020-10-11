package com.ismin.android.presentation.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ismin.android.core.result.Result
import com.ismin.android.domain.entities.Book
import com.ismin.android.presentation.adapters.BookAdapter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@BindingAdapter("bind")
fun bindBookAdapter(
    recyclerView: RecyclerView,
    books: Result<List<Book>>?
) {
    val adapter = recyclerView.adapter as BookAdapter
    books?.doOnSuccess { adapter.submitList(it) }
}

@BindingAdapter("date")
fun bindDate(
    textView: TextView,
    date: DateTime?
) {
    val fmt = DateTimeFormat.forPattern("d/M/y")
    date?.toString(fmt)?.let { textView.text = it }
}
