package com.med.coroutinelab.ui.lab7_flow_lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.med.coroutinelab.data.FakeDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * LAB 7 — Flow + Lifecycle
 *
 * Topics:
 *  - Why collect in repeatOnLifecycle (not just lifecycleScope.launch)
 *  - STARTED vs CREATED vs RESUMED
 *  - stateIn() — converting a cold Flow to StateFlow in the ViewModel
 *  - SharingStarted.WhileSubscribed(5_000) — the production standard
 *  - flowWithLifecycle() — alternative for single flows
 *  - The memory leak: what happens when you collect without lifecycle awareness
 */
class Lab7ViewModel : ViewModel() {

    // -------------------------------------------------------------------------
    // EXERCISE 1 — A continuous cold flow (simulates a sensor / location / ticker)
    // -------------------------------------------------------------------------

    /**
     * This is a cold flow that emits a temperature reading every second, forever.
     * In exercises below, you'll practice the RIGHT and WRONG ways to collect it.
     */
    val temperatureFlow: Flow<String> = flow {
        var temp = 20.0
        var count = 0
        while (true) {
            delay(1_000)
            temp += (-1..1).random()
            count++
            emit("🌡️ Temp: %.1f°C (tick #$count)".format(temp))
        }
    }

    // -------------------------------------------------------------------------
    // EXERCISE 2 — stateIn(): convert cold Flow → hot StateFlow in the ViewModel
    // -------------------------------------------------------------------------

    /**
     * TODO 2:
     *  Convert temperatureFlow into a StateFlow using stateIn().
     *
     *  Use:
     *    - scope = viewModelScope
     *    - started = SharingStarted.WhileSubscribed(5_000)
     *    - initialValue = "Waiting for sensor..."
     *
     *  This is the PRODUCTION STANDARD pattern. The flow:
     *    - Starts when the first collector subscribes
     *    - Stays alive for 5 seconds after the last collector unsubscribes
     *      (handles rotation: new Activity subscribes within ~300ms, no restart needed)
     *    - Stops fully after 5 seconds with no subscribers (e.g. app backgrounded)
     *
     * HINT:
     *   val temperatureState: StateFlow<String> = temperatureFlow
     *       .stateIn(
     *           scope = viewModelScope,
     *           started = SharingStarted.WhileSubscribed(5_000),
     *           initialValue = "Waiting for sensor..."
     *       )
     */

    // TODO 2 — declare temperatureState here


    // -------------------------------------------------------------------------
    // EXERCISE 3 — stateIn() with a transformed flow
    // -------------------------------------------------------------------------

    /**
     * TODO 3:
     *  1. Create a cold flow that emits stock prices every 2 seconds using
     *     FakeDataSource.fetchStockPrice("AMZN")
     *  2. Apply .map { price -> "AMZN: $${"%.2f".format(price)}" }
     *  3. Convert to StateFlow with stateIn() using WhileSubscribed(5_000)
     *  4. Initial value: "Loading price..."
     *
     *  The Activity will collect this and display it live.
     *  Notice: the flow only runs while the Activity is in the foreground.
     */

    // TODO 3 — declare stockPriceState here


    // -------------------------------------------------------------------------
    // EXERCISE 4 — Understand SharingStarted options
    // -------------------------------------------------------------------------

    /**
     * TODO 4 — No code, just read and understand:
     *
     *  SharingStarted.Eagerly
     *    - Starts immediately when the ViewModel is created
     *    - Never stops (runs even with no collectors)
     *    - Use for: critical data you need pre-loaded
     *    - Risk: wastes resources if nobody is collecting
     *
     *  SharingStarted.Lazily
     *    - Starts on first collector
     *    - Never stops after started
     *    - Use for: data you only need once, never re-subscribe
     *
     *  SharingStarted.WhileSubscribed(5_000)  ← THE ONE TO USE
     *    - Starts on first collector
     *    - Stops 5s after last collector leaves
     *    - Restarts if a new collector subscribes
     *    - The 5s window handles rotation gracefully
     *    - Use for: everything in production
     *
     *  Create one StateFlow with each strategy and observe in Logcat
     *  when they start and stop (add logging inside the source flow).
     */


    // -------------------------------------------------------------------------
    // EXERCISE 5 — The memory leak demo (illustrative)
    // -------------------------------------------------------------------------

    /**
     * TODO 5 — Read and understand (no code needed, it's in the Activity):
     *
     *  WRONG (leaks when app goes to background):
     *    lifecycleScope.launch {
     *        viewModel.temperatureFlow.collect { ... }  // keeps running in background!
     *    }
     *
     *  RIGHT (pauses when app is backgrounded, resumes on foreground):
     *    lifecycleScope.launch {
     *        repeatOnLifecycle(Lifecycle.State.STARTED) {
     *            viewModel.temperatureFlow.collect { ... }
     *        }
     *    }
     *
     *  Both are demonstrated in Lab7Activity. Run the app, background it,
     *  and watch Logcat — the WRONG version keeps emitting, the RIGHT one stops.
     */
}
