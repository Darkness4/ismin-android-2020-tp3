package com.ismin.android.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ismin.android.adapters.BookAdapter
import com.ismin.android.entities.Book

@BindingAdapter("bind")
fun bindGithubAdapter(
    recyclerView: RecyclerView,
    books: List<Book>?
) {
    val adapter = recyclerView.adapter as BookAdapter
    books?.let{ adapter.submitList(it) }
}