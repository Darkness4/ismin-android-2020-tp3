package com.ismin.android.presentation.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ismin.android.databinding.FragmentBookListBinding
import com.ismin.android.presentation.adapters.BookAdapter
import com.ismin.android.presentation.viewmodels.MainViewModel

class BookListFragment : Fragment() {
    private val activityViewModel by viewModels<MainViewModel>(
        ownerProducer = { requireActivity() }
    )
    private lateinit var binding: FragmentBookListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Bind
        binding = FragmentBookListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.activityViewModel = activityViewModel

        binding.bookList.layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager(context, 2)
            } else {
                GridLayoutManager(context, 4)
            }
        binding.bookList.adapter =
            BookAdapter(BookAdapter.OnClickListener { activityViewModel.removeBook(it) })

        activityViewModel.navigateToCreateBook.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    findNavController().navigate(
                        BookListFragmentDirections.actionBookListFragmentToCreateBookFragment()
                    )
                    activityViewModel.navigateToCreateBookDone()
                }
            }
        )
        return binding.root
    }
}
