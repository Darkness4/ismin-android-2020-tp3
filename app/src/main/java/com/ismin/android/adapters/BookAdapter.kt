package com.ismin.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ismin.android.databinding.RowBookBinding
import com.ismin.android.entities.Book

class BookAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Book, BookAdapter.ViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(
            oldItem: Book,
            newItem: Book
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Book,
            newItem: Book
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }

    class OnClickListener(val clickListener: (item: Book) -> Unit) {
        fun onClick(item: Book) = clickListener(item)
    }

    class ViewHolder(
        private var binding: RowBookBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book, onClickListener: OnClickListener) {
            binding.book = item
            binding.deleteBookRow.setOnClickListener {
                onClickListener.onClick(item)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            RowBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }
}