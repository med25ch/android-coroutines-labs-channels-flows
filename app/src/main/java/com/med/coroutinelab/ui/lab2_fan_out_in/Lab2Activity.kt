package com.med.coroutinelab.ui.lab2_fan_out_in

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.med.coroutinelab.databinding.ActivityLab2Binding

class Lab2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab2Binding
    private val viewModel: Lab2ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLab2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Lab 2 — produce / Fan-Out / Fan-In"

        binding.btnProduce.setOnClickListener {
            binding.tvOutput.text = "▶ produce{} demo...\n"
            viewModel.startProduceDemo { appendOutput(it) }
        }
        binding.btnPipeline.setOnClickListener {
            binding.tvOutput.text = "▶ Pipeline demo...\n"
            viewModel.startPipelineDemo { appendOutput(it) }
        }
        binding.btnFanOut.setOnClickListener {
            binding.tvOutput.text = "▶ Fan-Out demo (3 workers)...\n"
            viewModel.startFanOutDemo { appendOutput(it) }
        }
        binding.btnFanIn.setOnClickListener {
            binding.tvOutput.text = "▶ Fan-In demo (3 producers)...\n"
            viewModel.startFanInDemo { appendOutput(it) }
        }
    }

    private fun appendOutput(msg: String) {
        runOnUiThread { binding.tvOutput.append("$msg\n") }
    }
}
