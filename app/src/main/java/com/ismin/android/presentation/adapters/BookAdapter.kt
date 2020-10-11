package com.ismin.android.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ismin.android.databinding.RowBookBinding
import com.ismin.android.domain.entities.Book

class BookAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Book, BookAdapter.ViewHolder>(DiffCallback) {

    // This refresh the list if the contents or items are different
    companion object DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(
            oldItem: Book,
            newItem: Book
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Book,
            newItem: Book
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }

    // A click listener
    fun interface OnClickListener {
        fun onClick(item: Book)
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
