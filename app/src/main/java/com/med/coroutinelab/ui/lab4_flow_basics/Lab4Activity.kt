package com.med.coroutinelab.ui.lab4_flow_basics

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.med.coroutinelab.databinding.ActivityLab4Binding

class Lab4Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab4Binding
    private val viewModel: Lab4ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLab4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Lab 4 — Flow Basics"

        binding.btnCold.setOnClickListener {
            binding.tvOutput.text = "▶ Cold flow demo...\n"
            viewModel.startColdDemo { appendOutput(it) }
        }
        binding.btnFlowOf.setOnClickListener {
            binding.tvOutput.text = "▶ flowOf / asFlow demo...\n"
            viewModel.startFlowOfDemo { appendOutput(it) }
        }
        binding.btnLazy.setOnClickListener {
            binding.tvOutput.text = "▶ Lazy demo...\n"
            viewModel.startLazyDemo { appendOutput(it) }
        }
        binding.btnCancellation.setOnClickListener {
            binding.tvOutput.text = "▶ Cancellation demo...\n"
            viewModel.startCancellationDemo { appendOutput(it) }
        }
    }

    private fun appendOutput(msg: String) {
        runOnUiThread { binding.tvOutput.append("$msg\n") }
    }
}
