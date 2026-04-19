package com.med.coroutinelab.ui.lab4_flow_basics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.med.coroutinelab.data.FakeDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

/**
 * LAB 4 — Flow Basics
 *
 * Topics:
 *  - Cold vs Hot
 *  - flow{} builder
 *  - flowOf() and asFlow()
 *  - Cancellation behaviour
 *  - flow is lazy — nothing runs until you collect
 */
class Lab4ViewModel : ViewModel() {

    // -------------------------------------------------------------------------
    // EXERCISE 1 — Cold flow: nothing runs until collected
    // -------------------------------------------------------------------------

    /**
     * TODO 1:
     *  1. Create a Flow<String> using the flow{} builder
     *  2. Inside it, emit "Item 1", "Item 2", "Item 3" with 500ms delays
     *  3. Add a log at the TOP of the flow block: "Flow started"
     *  4. Collect it TWICE in startColdDemo() — back to back
     *
     *  Observe: "Flow started" appears TWICE — because each collect() triggers
     *  a fresh execution. This is what "cold" means.
     *
     * HINT:
     *   val myFlow: Flow<String> = flow {
     *       onReceive("Flow started")
     *       emit("Item 1")
     *       ...
     *   }
     */
    fun startColdDemo(onReceive: (String) -> Unit) {
        // TODO 1 — your code here
        // Collect the flow TWICE to prove it's cold
    }

    // -------------------------------------------------------------------------
    // EXERCISE 2 — flowOf() and asFlow()
    // Convenience builders for known values
    // -------------------------------------------------------------------------

    /**
     * TODO 2:
     *  1. Use flowOf("Red", "Green", "Blue") and collect it
     *  2. Use listOf(10, 20, 30).asFlow() and collect it
     *  3. Call onReceive() for each item
     *
     * These are still cold — each collect triggers a new emission.
     */
    fun startFlowOfDemo(onReceive: (String) -> Unit) {
        // TODO 2 — your code here
    }

    // -------------------------------------------------------------------------
    // EXERCISE 3 — Flow is lazy (nothing runs before collect)
    // -------------------------------------------------------------------------

    /**
     * TODO 3:
     *  1. Build a flow that emits 3 items and logs "Producing item X" for each
     *  2. In startLazyDemo(), create the flow but DON'T collect it immediately
     *  3. Call onReceive("Flow created — not started yet")
     *  4. Wait 1 second (delay), then call onReceive("Now collecting...")
     *  5. Then collect it
     *
     *  Observe: "Producing item X" only appears AFTER the 1 second delay.
     *  The flow block did not execute at creation time.
     */
    fun startLazyDemo(onReceive: (String) -> Unit) {
        // TODO 3 — your code here
    }

    // -------------------------------------------------------------------------
    // EXERCISE 4 — Flow cancellation
    // Flows respect coroutine cancellation automatically
    // -------------------------------------------------------------------------

    /**
     * TODO 4:
     *  1. Build an infinite flow that emits incrementing numbers every 400ms
     *  2. Collect it inside a Job (val job = viewModelScope.launch { ... })
     *  3. After 2 seconds, cancel the job
     *  4. Call onReceive("Cancelled after X items")
     *
     *  Observe: the flow stops cleanly when the coroutine is cancelled.
     *  No special cleanup needed — this is a key Flow advantage over callbacks.
     */
    fun startCancellationDemo(onReceive: (String) -> Unit) {
        // TODO 4 — your code here
    }
}
