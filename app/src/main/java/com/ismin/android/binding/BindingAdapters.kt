package com.ismin.android.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ismin.android.adapters.BookAdapter
import com.ismin.android.entities.Book
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@BindingAdapter("bind")
fun bindBookAdapter(
    recyclerView: RecyclerView,
    books: List<Book>?
) {
    val adapter = recyclerView.adapter as BookAdapter
    books?.let { adapter.submitList(it) }
}

@BindingAdapter("date")
fun bindDate(
    textView: TextView,
    date: DateTime
) {
    val fmt = DateTimeFormat.forPattern("d/M/y")
    textView.text = date.toString(fmt)
}
