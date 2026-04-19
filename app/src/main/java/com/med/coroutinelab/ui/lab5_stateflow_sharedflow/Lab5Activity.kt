package com.med.coroutinelab.ui.lab5_stateflow_sharedflow

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.med.coroutinelab.databinding.ActivityLab5Binding
import kotlinx.coroutines.launch

class Lab5Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab5Binding
    private val viewModel: Lab5ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLab5Binding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Lab 5 — StateFlow & SharedFlow"

        // Ex 1 — Counter buttons
        binding.btnIncrement.setOnClickListener { viewModel.increment() }
        binding.btnDecrement.setOnClickListener { viewModel.decrement() }
        binding.btnReset.setOnClickListener { viewModel.reset() }

        // Ex 2 — Data loading
        binding.btnLoadUser.setOnClickListener { viewModel.loadUser() }
        binding.btnSimulateError.setOnClickListener { viewModel.simulateError() }

        // Ex 3 — SharedFlow broadcast
        binding.btnBroadcast.setOnClickListener {
            viewModel.broadcastMessage("Hello from ViewModel @ ${System.currentTimeMillis()}")
        }

        // Ex 4 — Replay problem
        binding.btnReplayEvent.setOnClickListener {
            viewModel.sendReplayableEvent()
            appendOutput("Event sent — now rotate the screen and watch it fire again!")
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // TODO: After completing Lab5ViewModel Ex1, uncomment:
                /*
                launch {
                    viewModel.counter.collect { count ->
                        binding.tvCounter.text = "Counter: $count"
                    }
                }
                */

                // TODO: After completing Lab5ViewModel Ex2, uncomment:
                /*
                launch {
                    viewModel.userState.collect { state ->
                        binding.tvUserState.text = when {
                            state.isLoading -> "⏳ Loading..."
                            state.errorMessage.isNotEmpty() -> "❌ ${state.errorMessage}"
                            state.userName.isNotEmpty() -> "✅ ${state.userName}"
                            else -> "Idle"
                        }
                    }
                }
                */

                // TODO: After completing Lab5ViewModel Ex3, uncomment:
                // TWO collectors — both get every message (unlike Channel)
                /*
                launch {
                    viewModel.messages.collect { msg ->
                        appendOutput("[Collector A] $msg")
                    }
                }
                launch {
                    viewModel.messages.collect { msg ->
                        appendOutput("[Collector B] $msg")
                    }
                }
                */

                // TODO: After completing Lab5ViewModel Ex4, uncomment:
                /*
                launch {
                    viewModel.replayFlow.collect { event ->
                        appendOutput("⚠️ Replay event received: $event")
                    }
                }
                */
            }
        }
    }

    private fun appendOutput(msg: String) {
        runOnUiThread { binding.tvOutput.append("$msg\n") }
    }
}
