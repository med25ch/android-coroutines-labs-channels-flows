package com.med.coroutinelab.ui.lab6_flow_operators

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.med.coroutinelab.databinding.ActivityLab6Binding

class Lab6Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab6Binding
    private val viewModel: Lab6ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLab6Binding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Lab 6 — Flow Operators"

        binding.btnMapFilter.setOnClickListener {
            binding.tvOutput.text = "▶ map + filter demo...\n"
            viewModel.startMapFilterDemo { appendOutput(it) }
        }

        binding.btnCombine.setOnClickListener {
            binding.tvOutput.text = "▶ combine demo...\n"
            viewModel.startCombineDemo { appendOutput(it) }
        }

        binding.btnZip.setOnClickListener {
            binding.tvOutput.text = "▶ zip demo...\n"
            viewModel.startZipDemo { appendOutput(it) }
        }

        binding.btnFlatMapLatest.setOnClickListener {
            binding.tvOutput.text = "▶ flatMapLatest demo...\n"
            viewModel.startFlatMapLatestDemo { appendOutput(it) }
        }

        // Debounce: starts listening as soon as you type
        binding.btnStartDebounce.setOnClickListener {
            binding.tvOutput.text = "▶ Type in the search box (debounced)...\n"
            viewModel.startDebounceDemo { appendOutput(it) }
        }

        // Wire up search box to ViewModel
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.onSearchQueryChanged(s?.toString() ?: "")
            }
        })
    }

    private fun appendOutput(msg: String) {
        runOnUiThread { binding.tvOutput.append("$msg\n") }
    }
}
