package com.med.coroutinelab.ui.lab2_fan_out_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.med.coroutinelab.data.FakeDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * LAB 2 — produce{} / Fan-Out / Fan-In
 *
 * Topics:
 *  - produce{} builder (auto-closes the channel)
 *  - Pipeline: chaining channels together
 *  - Fan-Out: one producer → many consumers (load balancing)
 *  - Fan-In: many producers → one consumer (aggregation)
 */
class Lab2ViewModel : ViewModel() {

    // -------------------------------------------------------------------------
    // EXERCISE 1 — produce{} builder
    // produce{} is a coroutine builder that returns a ReceiveChannel.
    // The channel is closed automatically when the block finishes.
    // -------------------------------------------------------------------------

    /**
     * TODO 1:
     *  1. Create a function using produce{} that emits numbers 1..5 with 300ms delay
     *  2. In startProduceDemo(), collect from it with a for loop and call onReceive()
     *
     * HINT:
     *   fun CoroutineScope.numberProducer(): ReceiveChannel<Int> = produce {
     *       for (i in 1..5) { send(i); delay(300) }
     *   }
     *
     * Notice: you don't call close() — produce{} handles it.
     */
    fun startProduceDemo(onReceive: (String) -> Unit) {

        viewModelScope.launch {
            for(number in numberProducer()){
                onReceive("number is $number")
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun CoroutineScope.numberProducer(): ReceiveChannel<Int> = produce {
        for (i in 1..5) {
            send(i);
            delay(300)
        }
    }

    // -------------------------------------------------------------------------
    // EXERCISE 2 — Pipeline
    // Chain two channels: one produces raw numbers, another transforms them.
    // -------------------------------------------------------------------------

    /**
     * TODO 2:
     *  1. Create a producer that emits 1..5
     *  2. Create a transformer that receives from step 1 and emits each number squared
     *  3. Collect the final channel and call onReceive() with each squared value
     *
     * Expected output: 1, 4, 9, 16, 25
     *
     * HINT: The transformer also uses produce{} and takes a ReceiveChannel as input:
     *   fun CoroutineScope.square(input: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
     *       for (n in input) send(n * n)
     *   }
     */
    fun startPipelineDemo(onReceive: (String) -> Unit) {`

        val numbers = viewModelScope.numberProducer()

        val transformedNumbers = viewModelScope.square(numbers)

        viewModelScope.launch {
            for (transformedNumber in transformedNumbers) {
                onReceive("transformed is $transformedNumber")
            }
        }
    }

    fun CoroutineScope.square(input: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        for (n in input) send(n * n)
    }

    // -------------------------------------------------------------------------
    // EXERCISE 3 — Fan-Out
    // One channel, multiple consumers. Each item goes to EXACTLY ONE consumer.
    // -------------------------------------------------------------------------

    /**
     * TODO 3:
     *  1. Create a channel that emits 9 work items ("Task_1" .. "Task_9")
     *  2. Launch 3 worker coroutines, each consuming from the SAME channel
     *  3. Each worker should have a different artificial delay (200ms, 500ms, 800ms)
     *     to simulate unequal processing speeds
     *  4. Call onReceive("Worker $id processed: $item") in each worker
     *
     * Observe: faster workers pick up more tasks naturally — this is load balancing.
     */
    fun startFanOutDemo(onReceive: (String) -> Unit) {
        // TODO 3 — your code here
    }

    // -------------------------------------------------------------------------
    // EXERCISE 4 — Fan-In
    // Multiple producers feeding into one channel, consumed by one collector.
    // -------------------------------------------------------------------------

    /**
     * TODO 4:
     *  1. Create a single output channel
     *  2. Launch 3 producer coroutines:
     *       - Producer A: emits "A_1", "A_2", "A_3" with 300ms delay
     *       - Producer B: emits "B_1", "B_2", "B_3" with 500ms delay
     *       - Producer C: emits "C_1", "C_2", "C_3" with 700ms delay
     *  3. Close the channel after all producers finish (use a launch + join or a counter)
     *  4. One consumer reads all items and calls onReceive()
     *
     * Observe: items from all 3 producers are interleaved based on their speed.
     */
    fun startFanInDemo(onReceive: (String) -> Unit) {
        // TODO 4 — your code here
    }
}
