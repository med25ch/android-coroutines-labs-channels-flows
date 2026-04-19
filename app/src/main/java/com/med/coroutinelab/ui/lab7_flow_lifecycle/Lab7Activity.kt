package com.med.coroutinelab.ui.lab7_flow_lifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.med.coroutinelab.databinding.ActivityLab7Binding
import kotlinx.coroutines.launch

class Lab7Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab7Binding
    private val viewModel: Lab7ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLab7Binding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Lab 7 — Flow + Lifecycle"

        // ------------------------------------------------------------------
        // ⚠️  WRONG WAY — No lifecycle awareness
        // This keeps collecting even when app is in background (screen off,
        // another app in foreground). Wastes battery and can crash if the
        // flow tries to update UI when the window is detached.
        //
        // Uncomment to see it leak in Logcat (filter: "LAB7_WRONG")
        // ------------------------------------------------------------------
        /*
        lifecycleScope.launch {
            viewModel.temperatureFlow.collect { temp ->
                Log.d("LAB7_WRONG", "Still running in background: $temp")
                binding.tvWrongOutput.text = temp
            }
        }
        */

        // ------------------------------------------------------------------
        // ✅  RIGHT WAY — repeatOnLifecycle(STARTED)
        // Collection PAUSES when lifecycle drops below STARTED (app backgrounded)
        // Collection RESUMES when lifecycle returns to STARTED (app foregrounded)
        // The coroutine is cancelled and re-launched on each lifecycle transition.
        // ------------------------------------------------------------------
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // ✅ Safe: only runs while Activity is at least STARTED
                viewModel.temperatureFlow.collect { temp ->
                    Log.d("LAB7_RIGHT", temp)
                    binding.tvLiveOutput.text = temp
                }
            }
        }

        // ------------------------------------------------------------------
        // ✅  BEST WAY for state — collect stateIn() StateFlow
        // stateIn() with WhileSubscribed(5_000) + repeatOnLifecycle is the
        // gold standard for production apps.
        // ------------------------------------------------------------------

        // TODO: After completing Lab7ViewModel Ex2, uncomment:
        /*
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.temperatureState.collect { temp ->
                        binding.tvStateInOutput.text = temp
                    }
                }
                launch {
                    // TODO: After completing Ex3, collect stockPriceState here
                    // viewModel.stockPriceState.collect { price ->
                    //     binding.tvStockOutput.text = price
                    // }
                }
            }
        }
        */

        // Button to trigger manual observation experiment
        binding.btnExplainLifecycle.setOnClickListener {
            binding.tvExplanation.text = buildExplanation()
        }
    }

    private fun buildExplanation(): String = """
        LIFECYCLE STATES:
        
        CREATED → onCreate() called, onDestroy() not yet
        STARTED → onStart() called, onStop() not yet  ← use this
        RESUMED → onResume() called, onPause() not yet
        
        WHY STARTED AND NOT RESUMED?
        If you use RESUMED, collection pauses during multi-window
        mode or when a dialog covers your Activity (it's still 
        STARTED but not RESUMED). STARTED is the safer default.
        
        WHAT HAPPENS ON ROTATION?
        Activity is STOPPED → collection pauses
        New Activity is STARTED → collection resumes
        WhileSubscribed(5_000) keeps the upstream flow alive
        during this brief gap, so no data is lost.
        
        Background the app now and watch Logcat:
          Filter "LAB7_RIGHT"  → stops emitting ✅
          Filter "LAB7_WRONG"  → keeps emitting ⚠️ (if uncommented)
    """.trimIndent()
}
