package com.med.coroutinelab.ui.lab1_channel_basics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * LAB 1 — Channel Basics
 *
 * Topics:
 *  - Creating a Channel
 *  - send() and receive()
 *  - Buffer types: Rendezvous, Buffered, Unlimited, Conflated
 *  - Closing a channel
 *  - Iterating with for loop
 */
class Lab1ViewModel : ViewModel() {

    // -------------------------------------------------------------------------
    // EXERCISE 1 — Rendezvous Channel (default, no buffer)
    // Sender suspends until receiver is ready, and vice versa.
    // -------------------------------------------------------------------------

    /**
     * TODO 1:
     *  1. Create a Rendezvous Channel<String> (no capacity argument)
     *  2. Launch a coroutine that sends 5 messages with a 500ms delay between each
     *  3. Launch another coroutine that receives each message and calls onReceive(message)
     *  4. Observe in Logcat that send and receive alternate — neither gets ahead
     *
     * HINT: Channel<String>() with no argument = Rendezvous
     */
    fun startRendezvousDemo(onReceive: (String) -> Unit) {
        val channel = Channel<String>()

        viewModelScope.launch {
            for (i in 1..5) {
                channel.send("Message $i")
                delay(500)
            }
        }

        viewModelScope.launch {
            for (i in 1..5) {
                onReceive(channel.receive())
                delay(600)
            }
        }
    }

    // -------------------------------------------------------------------------
    // EXERCISE 2 — Buffered Channel
    // Sender can get ahead by [capacity] items before suspending.
    // -------------------------------------------------------------------------

    /**
     * TODO 2:
     *  1. Create a Channel<String> with capacity = 3
     *  2. Launch a FAST producer that sends 6 items with only 100ms delay
     *  3. Launch a SLOW consumer that receives items with 600ms delay, calls onReceive()
     *  4. Observe: producer sends 3 items immediately (fills buffer), then suspends
     *     while consumer slowly drains it
     *
     * HINT: Channel<String>(capacity = 3)
     */
    fun startBufferedDemo(onReceive: (String) -> Unit) {
        val channel = Channel<String>(capacity = 3)
        viewModelScope.launch {
            for (i in 1..6) {
                channel.send("Message $i")
                delay(100)
            }
        }

        viewModelScope.launch {
            for (i in 1..6) {
                val msg = channel.receive()   // ← receive FIRST
                Log.d("Consumer", "Got $msg at ${System.currentTimeMillis()}")
                onReceive(" Consumer Got $msg at ${System.currentTimeMillis()}")
                delay(1000)                    // ← THEN slow down
            }
        }
    }

    // -------------------------------------------------------------------------
    // EXERCISE 3 — Conflated Channel
    // Only the LATEST value is kept. If consumer is slow, it misses old values.
    // -------------------------------------------------------------------------

    /**
     * TODO 3:
     *  1. Create a Channel<String>(Channel.CONFLATED)
     *  2. Launch a FAST producer that sends 5 items immediately (no delay)
     *  3. Launch a SLOW consumer (800ms delay per item) that calls onReceive()
     *  4. Observe: consumer only sees the latest value(s), not all 5
     *
     * This is useful for UI state where stale frames are irrelevant (e.g. a slider position)
     *
     * HINT: Channel<String>(Channel.CONFLATED)
     */
    fun startConflatedDemo(onReceive: (String) -> Unit) {
        val channel = Channel<String>(Channel.CONFLATED)

        viewModelScope.launch {
            for (i in 1..5) {
                channel.send("Message $i")
                Log.d("Producer", "Sent $i")
                delay(100)  // producer sends every 100ms
            }
        }

        viewModelScope.launch {
            for (msg in channel) {
                Log.d("Consumer", "Got $msg")
                onReceive(msg)
                delay(500)  // consumer takes 500ms per item
            }
        }
    }

    // -------------------------------------------------------------------------
    // EXERCISE 4 — Closing a Channel + iterating with for loop
    // -------------------------------------------------------------------------

    /**
     * TODO 4:
     *  1. Create a Channel<Int>
     *  2. Launch a producer that sends numbers 1..10, then calls channel.close()
     *  3. Launch a consumer that uses:
     *         for (item in channel) { onReceive("Received: $item") }
     *     The for loop automatically stops when the channel is closed.
     *
     * IMPORTANT: Without close(), the for loop suspends forever waiting for more items.
     * This is a common source of leaks — always close your channels.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun startCloseDemo(onReceive: (String) -> Unit) {
        val channel = Channel<Int>()

        viewModelScope.launch {
            for (i in 1..11) {
                channel.send(i)
                delay(500)
            }
            channel.close()
            onReceive(channel.isClosedForSend.toString())
        }

        viewModelScope.launch {
            onReceive(channel.isClosedForReceive.toString())
            for (item in channel) {
                onReceive("Received: $item")
            }
            onReceive(channel.isClosedForReceive.toString())
        }
    }

    // -------------------------------------------------------------------------
    // BONUS — What happens if you send to a closed channel?
    // -------------------------------------------------------------------------

    /**
     * TODO BONUS:
     *  1. Create a channel, close it immediately
     *  2. Try to send to it inside a try/catch
     *  3. Call onReceive() with the exception message
     *
     * Expected: ClosedSendChannelException
     */
    fun startClosedSendDemo(onReceive: (String) -> Unit) {
        val channel = Channel<String>()
        channel.close(Exception("Closed for no damn reason"))

        viewModelScope.launch {
            try {
                channel.send("Hello")
            } catch (e: Exception) {
                onReceive(e.message!!)
            }
        }
    }
}
