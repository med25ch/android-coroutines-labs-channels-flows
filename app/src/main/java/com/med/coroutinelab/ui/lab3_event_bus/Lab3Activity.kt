package com.med.coroutinelab.ui.lab3_event_bus

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.med.coroutinelab.databinding.ActivityLab3Binding
import kotlinx.coroutines.launch

class Lab3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab3Binding
    private val viewModel: Lab3ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLab3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Lab 3 — Channel as Event Bus"

        // Wire up buttons
        binding.btnShowToast.setOnClickListener { viewModel.onShowToastClicked() }
        binding.btnShowDialog.setOnClickListener { viewModel.onShowDialogClicked() }
        binding.btnLogin.setOnClickListener {
            binding.tvOutput.text = "⏳ Logging in...\n"
            viewModel.onLoginClicked()
        }

        // ------------------------------------------------------------------
        // EVENT COLLECTION
        // Note: we collect inside repeatOnLifecycle(STARTED) so that
        // collection pauses when the app goes to background, preventing
        // events from being processed when the UI isn't visible.
        //
        // The Channel holds undelivered events in its buffer — they are
        // delivered as soon as the UI returns to STARTED state.
        // ------------------------------------------------------------------
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // TODO: After you wire up Lab3ViewModel, uncomment this block:
                /*
                viewModel.events.collect { event ->
                    when (event) {
                        is Lab3UiEvent.ShowToast -> {
                            Toast.makeText(this@Lab3Activity, event.message, Toast.LENGTH_SHORT).show()
                            appendOutput("Toast shown: ${event.message}")
                        }
                        is Lab3UiEvent.ShowDialog -> {
                            AlertDialog.Builder(this@Lab3Activity)
                                .setTitle(event.title)
                                .setMessage(event.body)
                                .setPositiveButton("OK", null)
                                .show()
                            appendOutput("Dialog shown: ${event.title}")
                        }
                        is Lab3UiEvent.NavigateToNextScreen -> {
                            appendOutput("✅ Navigation triggered! (would go to next screen)")
                            // In a real app: findNavController().navigate(...)
                        }
                        is Lab3UiEvent.ShowError -> {
                            appendOutput("❌ Error: ${event.error}")
                        }
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
