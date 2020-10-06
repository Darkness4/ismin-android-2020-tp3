package com.ismin.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ismin.android.databinding.ActivityCreateBookBinding
import com.ismin.android.viewmodels.CreateBookViewModel
import org.joda.time.DateTime

class CreateBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCreateBookBinding.inflate(layoutInflater)
        val viewModel by viewModels<CreateBookViewModel>()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.saveButton.setOnClickListener {
            viewModel.setDate(DateTime(binding.datePicker.year, binding.datePicker.month, binding.datePicker.dayOfMonth, 0, 0, 0))

            try {
                val book = viewModel.getBook()
                val output = Intent()
                output.putExtra("book", book)
                setResult(RESULT_OK, output)
                finish()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }

        setContentView(binding.root)
    }
}
