package com.med.coroutinelab.ui.lab4_flow_basics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
        // Collect the flow TWICE to prove it's cold
        val flow = flow {
            onReceive("Flow started")
            emit("Item 1")
            delay(500)
            emit("Item 2")
            delay(500)
            emit("Item 3")
        }

        viewModelScope.launch {
            flow.collect { onReceive(it) }
            flow.collect { onReceive(it) }
        }

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
        val colorsFlow = flowOf("Red", "Green", "Blue")
        val numbersFlow = listOf(10, 20, 30).asFlow()

        viewModelScope.launch {
            colorsFlow.collect { onReceive(it) }
            numbersFlow.collect { onReceive(it.toString()) }
        }
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
        viewModelScope.launch {

            val customFlow = flow {
                for (item in 1..3) {
                    Log.d("Flow", "Producing item $item")  // ← background log
                    emit("Item $item")                      // ← actual emitted value
                    delay(1000)
                }
            }

            onReceive("Flow created — not started yet")

            delay(1000)

            onReceive("Now collecting...")

            customFlow.collect {
                onReceive(it)
            }
        }
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
        var counter = 0

        val colorsFlow = flow {
            while (true) {
                delay(400)
                emit(counter)
            }
        }

        val job = viewModelScope.launch {
            colorsFlow.collect {
                onReceive(it.toString())
                counter++
            }
        }

        viewModelScope.launch {
            delay(5000)
            job.cancelAndJoin()
            onReceive("Cancelled after $counter items")
        }
    }
}
