package com.med.coroutinelab.ui.lab3_event_bus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.med.coroutinelab.data.FakeDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * LAB 3 — Channel as Event Bus (One-shot UI events)
 *
 * Topics:
 *  - Why Channel for events (not SharedFlow)
 *  - receiveAsFlow() to expose safely
 *  - Sending navigation, toast, and dialog events
 *  - Why consumed events don't replay on rotation
 */

// Sealed class for all possible UI events from this screen
sealed class Lab3UiEvent {
    data class ShowToast(val message: String) : Lab3UiEvent()
    data class ShowDialog(val title: String, val body: String) : Lab3UiEvent()
    object NavigateToNextScreen : Lab3UiEvent()
    data class ShowError(val error: String) : Lab3UiEvent()
}

class Lab3ViewModel : ViewModel() {

    // -------------------------------------------------------------------------
    // EXERCISE 1 — Wire up the event channel
    // -------------------------------------------------------------------------

    /**
     * TODO 1:
     *  1. Create a private Channel<Lab3UiEvent> with Channel.BUFFERED
     *  2. Expose it publicly as a Flow<Lab3UiEvent> using receiveAsFlow()
     *
     * HINT:
     *   private val _events = Channel<Lab3UiEvent>(Channel.BUFFERED)
     *   val events = _events.receiveAsFlow()
     *
     * Q: Why BUFFERED and not Rendezvous here?
     * A: If the UI isn't collecting yet (e.g. app is in background), a Rendezvous
     *    channel would suspend the ViewModel indefinitely. BUFFERED lets us
     *    send without waiting for a receiver.
     */

    // TODO 1 — declare _events and events here


    // -------------------------------------------------------------------------
    // EXERCISE 2 — Trigger a toast event
    // -------------------------------------------------------------------------

    /**
     * TODO 2:
     *  Call viewModelScope.launch { _events.send(ShowToast(...)) }
     *  when the user taps "Show Toast" button
     */
    fun onShowToastClicked() {
        // TODO 2 — your code here
    }

    // -------------------------------------------------------------------------
    // EXERCISE 3 — Trigger a dialog event
    // -------------------------------------------------------------------------

    /**
     * TODO 3:
     *  Send a ShowDialog event with a title and body
     */
    fun onShowDialogClicked() {
        // TODO 3 — your code here
    }

    // -------------------------------------------------------------------------
    // EXERCISE 4 — Trigger navigation after an async operation
    // -------------------------------------------------------------------------

    /**
     * TODO 4:
     *  1. Call FakeDataSource.login("med", "1234") in a coroutine
     *  2. On success → send NavigateToNextScreen
     *  3. On failure → send ShowError with the exception message
     *
     * This is the real pattern: async work → one-shot event to UI
     */
    fun onLoginClicked() {
        // TODO 4 — your code here
    }

    // -------------------------------------------------------------------------
    // EXERCISE 5 — Rotation test (the whole point of using Channel)
    // -------------------------------------------------------------------------

    /**
     * TODO 5 — No code needed here. Do this manually:
     *
     *  1. Implement TODOs 1-4 first
     *  2. Tap "Login" button
     *  3. QUICKLY rotate the screen before the event is consumed
     *  4. Observe: the NavigateToNextScreen event is NOT re-delivered after rotation
     *
     *  Now try the same test with SharedFlow(replay=1) instead of Channel.
     *  You'll see the event fires again after rotation — that's the bug Channel prevents.
     *
     *  Check the comments in Lab3Activity to see how events are collected.
     */
}
