package com.med.coroutinelab.ui.lab5_stateflow_sharedflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.med.coroutinelab.data.FakeDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * LAB 5 — StateFlow & SharedFlow
 *
 * Topics:
 *  - StateFlow: always has a value, replays current to new collectors (UI state)
 *  - SharedFlow: configurable replay, multiple collectors (event broadcasting)
 *  - StateFlow vs SharedFlow vs Channel — choosing the right tool
 *  - update{} for safe StateFlow mutation
 *  - replay parameter and its dangers for one-shot events
 */
class Lab5ViewModel : ViewModel() {

    // -------------------------------------------------------------------------
    // EXERCISE 1 — StateFlow for UI state (counter)
    // StateFlow always holds a value. New collectors immediately get the current value.
    // -------------------------------------------------------------------------

    /**
     * TODO 1:
     *  1. Create a MutableStateFlow<Int> with initial value 0
     *  2. Expose it as StateFlow<Int> using asStateFlow()
     *  3. Implement increment() and decrement() using .update { it + 1 } / { it - 1 }
     *  4. Implement reset() to set value back to 0
     *
     * HINT:
     *   private val _counter = MutableStateFlow(0)
     *   val counter: StateFlow<Int> = _counter.asStateFlow()
     *
     * Q: Why update{} instead of _counter.value = _counter.value + 1 ?
     * A: update{} is atomic — safe if multiple coroutines mutate simultaneously.
     */

    // TODO 1 — declare _counter and counter here

    fun increment() { /* TODO 1 */ }
    fun decrement() { /* TODO 1 */ }
    fun reset()     { /* TODO 1 */ }

    // -------------------------------------------------------------------------
    // EXERCISE 2 — StateFlow for loading state (data class)
    // Real-world pattern: wrap your whole screen state in one data class
    // -------------------------------------------------------------------------

    data class UserScreenState(
        val isLoading: Boolean = false,
        val userName: String = "",
        val errorMessage: String = ""
    )

    /**
     * TODO 2:
     *  1. Create a MutableStateFlow<UserScreenState> with default value
     *  2. Expose as StateFlow<UserScreenState>
     *  3. Implement loadUser():
     *       a. update state: isLoading = true
     *       b. Call FakeDataSource.fetchUser(42)
     *       c. update state: isLoading = false, userName = result
     *  4. Implement simulateError():
     *       a. update state: isLoading = true
     *       b. delay(800)
     *       c. update state: isLoading = false, errorMessage = "Network timeout"
     *
     * This is the exact pattern you use in production ViewModels.
     */

    // TODO 2 — declare _userState and userState here

    fun loadUser()        { /* TODO 2 */ }
    fun simulateError()   { /* TODO 2 */ }

    // -------------------------------------------------------------------------
    // EXERCISE 3 — SharedFlow basics (broadcast to multiple collectors)
    // Unlike StateFlow, SharedFlow has no initial value and doesn't replay by default.
    // -------------------------------------------------------------------------

    /**
     * TODO 3:
     *  1. Create a MutableSharedFlow<String>() — no replay, no initial value
     *  2. Expose as SharedFlow<String>
     *  3. Implement broadcastMessage(msg: String) that emits to the shared flow
     *
     *  In startSharedFlowDemo() in the Activity, you'll collect with TWO collectors.
     *  Both will receive every message — this is different from Channel (Fan-Out).
     *
     * HINT:
     *   private val _messages = MutableSharedFlow<String>()
     *   val messages: SharedFlow<String> = _messages.asSharedFlow()
     */

    // TODO 3 — declare _messages and messages here

    fun broadcastMessage(msg: String) { /* TODO 3 */ }

    // -------------------------------------------------------------------------
    // EXERCISE 4 — SharedFlow replay: the rotation problem
    // -------------------------------------------------------------------------

    /**
     * TODO 4:
     *  1. Create a MutableSharedFlow<String>(replay = 1)
     *  2. Expose it as SharedFlow<String>
     *  3. Implement sendReplayableEvent() that emits "Navigate to Profile"
     *
     *  In the Activity, collect this flow, then ROTATE THE SCREEN.
     *  Observe: the "Navigate to Profile" event fires AGAIN after rotation.
     *  This is WHY we use Channel for navigation events, not SharedFlow.
     *
     * HINT: MutableSharedFlow<String>(replay = 1)
     */

    // TODO 4 — declare _replayFlow and replayFlow here

    fun sendReplayableEvent() { /* TODO 4 */ }

    // -------------------------------------------------------------------------
    // EXERCISE 5 — Understand the difference: who gets what?
    // -------------------------------------------------------------------------

    /**
     * TODO 5 — Reflection (no code):
     *
     *  Run each of these and note what each collector sees:
     *
     *  StateFlow:
     *   - Collector A starts → gets current value immediately
     *   - Value changes to X
     *   - Collector B starts → gets X immediately (current value)
     *   Both A and B receive all future updates.
     *
     *  SharedFlow (replay=0):
     *   - Collector A starts
     *   - Event emitted → A gets it
     *   - Collector B starts
     *   - B does NOT get the past event (no replay)
     *   Both receive future events.
     *
     *  Channel:
     *   - Event emitted
     *   - Only ONE collector gets it (not both)
     *   Perfect for one-shot events.
     *
     *  Fill in this mental model and keep it as a reference.
     */
}
