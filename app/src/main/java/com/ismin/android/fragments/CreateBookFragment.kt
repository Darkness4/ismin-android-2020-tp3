package com.ismin.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ismin.android.databinding.FragmentCreateBookBinding
import com.ismin.android.viewmodels.CreateBookViewModel
import com.ismin.android.viewmodels.MainViewModel
import org.joda.time.DateTime

class CreateBookFragment : Fragment() {
    private val activityViewModel by viewModels<MainViewModel>(
        ownerProducer = { requireActivity() }
    )
    val viewModel by viewModels<CreateBookViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCreateBookBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToBookList.observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    activityViewModel.addBook(it)
                    findNavController().navigate(
                        CreateBookFragmentDirections.actionCreateBookFragmentToBookListFragment(
                            it
                        )
                    )
                    viewModel.navigateToBookListDone()
                }
            }
        )

        binding.saveButton.setOnClickListener {
            viewModel.setDate(
                DateTime(
                    binding.datePicker.year,
                    binding.datePicker.month,
                    binding.datePicker.dayOfMonth,
                    0,
                    0,
                    0
                )
            )
            viewModel.navigateToBookList(viewModel.getBook())
        }

        return binding.root
    }
}
