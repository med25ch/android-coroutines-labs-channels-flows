package com.med.coroutinelab.ui.lab1_channel_basics

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.med.coroutinelab.databinding.ActivityLab1Binding

class Lab1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab1Binding
    private val viewModel: Lab1ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLab1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Lab 1 — Channel Basics"

        // Exercise 1 — Rendezvous
        binding.btnRendezvous.setOnClickListener {
            binding.tvOutput.text = "▶ Rendezvous demo...\n"
            viewModel.startRendezvousDemo { msg ->
                appendOutput(msg)
            }
        }

        // Exercise 2 — Buffered
        binding.btnBuffered.setOnClickListener {
            binding.tvOutput.text = "▶ Buffered demo...\n"
            viewModel.startBufferedDemo { msg ->
                appendOutput(msg)
            }
        }

        // Exercise 3 — Conflated
        binding.btnConflated.setOnClickListener {
            binding.tvOutput.text = "▶ Conflated demo...\n"
            viewModel.startConflatedDemo { msg ->
                appendOutput(msg)
            }
        }

        // Exercise 4 — Close + for loop
        binding.btnClose.setOnClickListener {
            binding.tvOutput.text = "▶ Close demo...\n"
            viewModel.startCloseDemo { msg ->
                appendOutput(msg)
            }
        }

        // Bonus — Send to closed channel
        binding.btnClosedSend.setOnClickListener {
            binding.tvOutput.text = "▶ Closed send demo...\n"
            viewModel.startClosedSendDemo { msg ->
                appendOutput(msg)
            }
        }
    }

    private fun appendOutput(msg: String) {
        runOnUiThread {
            binding.tvOutput.append("$msg\n")
        }
    }
}
