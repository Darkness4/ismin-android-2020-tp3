package com.ismin.android.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ismin.android.R
import com.ismin.android.databinding.FragmentCreateBookBinding
import com.ismin.android.presentation.viewmodels.CreateBookViewModel
import com.ismin.android.presentation.viewmodels.MainViewModel
import org.joda.time.DateTime

class CreateBookFragment : Fragment() {
    private val activityViewModel by viewModels<MainViewModel>(
        ownerProducer = { requireActivity() }
    )
    private val viewModel by viewModels<CreateBookViewModel>()

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
                it?.let {
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

        binding.editTextTitle.doAfterTextChanged {
            binding.editTextTitle.error = if (viewModel.title.value.isNullOrBlank()) {
                getString(R.string.title_empty_error)
            } else {
                null
            }
        }

        binding.editTextAuthor.doAfterTextChanged {
            binding.editTextAuthor.error = if (viewModel.author.value.isNullOrBlank()) {
                getString(R.string.author_empty_error)
            } else {
                null
            }
        }

        binding.saveButton.setOnClickListener {
            closeKeyBoard()
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
            if (binding.editTextAuthor.error == null && binding.editTextTitle.error == null) {
                viewModel.navigateToBookList(viewModel.asEntity())
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.create_book_validation_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return binding.root
    }

    private fun closeKeyBoard() {
        activity?.currentFocus?.let {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}
