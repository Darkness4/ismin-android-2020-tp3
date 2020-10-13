package com.ismin.android.presentation.fragments

import android.app.DatePickerDialog
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
    private lateinit var binding: FragmentCreateBookBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBookBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.navigateToBookList.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    closeKeyBoard()
                    activityViewModel.addBook(it)
                    findNavController().popBackStack()
                    viewModel.navigateToBookListDone()
                }
            }
        )

        binding.editTextTitle.doAfterTextChanged { viewModel.validateTitle() }
        binding.editTestAuthor.doAfterTextChanged { viewModel.validateAuthor() }

        viewModel.authorError.observe(
            viewLifecycleOwner,
            {
                binding.editTextLayoutAuthor.error = if (it) {
                    getString(R.string.author_empty_error)
                } else {
                    null
                }
            }
        )

        viewModel.titleError.observe(
            viewLifecycleOwner,
            {
                binding.editTextLayoutTitle.error = if (it) {
                    getString(R.string.title_empty_error)
                } else {
                    null
                }
            }
        )

        viewModel.showValidationError.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    Toast.makeText(
                        context,
                        getString(R.string.create_book_validation_error),
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.showValidationErrorDone()
                }
            }
        )

        viewModel.showDatePicker.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    val date = viewModel.date.value!!
                    val dialog = DatePickerDialog(
                        requireContext(),
                        { _, year, monthOfYear, dayOfMonth ->
                            viewModel.date.value = DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0)
                        },
                        date.year, date.monthOfYear - 1, date.dayOfMonth
                    )
                    dialog.show()
                    viewModel.showDatePickerDone()
                }
            }
        )

        // Fire validate at least once
        viewModel.validate()

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
