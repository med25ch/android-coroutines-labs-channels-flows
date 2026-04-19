package com.med.coroutinelab.ui.lab6_flow_operators

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.med.coroutinelab.data.FakeDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * LAB 6 — Flow Operators
 *
 * Topics:
 *  - map / filter
 *  - combine (latest from each flow, any update triggers)
 *  - zip (pairs items 1-to-1, waits for both)
 *  - debounce (search box pattern)
 *  - flatMapLatest (cancels previous when new value arrives)
 *  - distinctUntilChanged (skip duplicate emissions)
 */
class Lab6ViewModel : ViewModel() {

    // -------------------------------------------------------------------------
    // EXERCISE 1 — map and filter
    // -------------------------------------------------------------------------

    /**
     * TODO 1:
     *  Given a flow of integers 1..10:
     *  1. Use filter { } to keep only even numbers
     *  2. Use map { } to multiply each by 100
     *  3. Collect and call onReceive() for each result
     *
     *  Expected: 200, 400, 600, 800, 1000
     *
     * HINT: (1..10).asFlow().filter { it % 2 == 0 }.map { it * 100 }
     */
    fun startMapFilterDemo(onReceive: (String) -> Unit) {
        // TODO 1 — your code here
    }

    // -------------------------------------------------------------------------
    // EXERCISE 2 — combine
    // Combines latest values from two flows. Emits whenever EITHER flow updates.
    // -------------------------------------------------------------------------

    /**
     * TODO 2:
     *  1. Create Flow A that emits "A1" after 0ms, "A2" after 600ms, "A3" after 1200ms
     *  2. Create Flow B that emits "B1" after 300ms, "B2" after 900ms
     *  3. Use combine(flowA, flowB) { a, b -> "$a + $b" }
     *  4. Collect and call onReceive() with each combined string
     *
     *  Expected timeline:
     *   300ms  → "A1 + B1"   (B1 arrives, combines with latest A = A1)
     *   600ms  → "A2 + B1"   (A2 arrives, combines with latest B = B1)
     *   900ms  → "A2 + B2"   (B2 arrives, combines with latest A = A2)
     *   1200ms → "A3 + B2"   (A3 arrives, combines with latest B = B2)
     *
     *  Use case: combining user prefs + fetched data for a single UI state.
     */
    fun startCombineDemo(onReceive: (String) -> Unit) {
        // TODO 2 — your code here
    }

    // -------------------------------------------------------------------------
    // EXERCISE 3 — zip
    // Pairs items from two flows strictly 1-to-1. Waits for both before emitting.
    // -------------------------------------------------------------------------

    /**
     * TODO 3:
     *  1. Create a flow of names: "Alice", "Bob", "Charlie"
     *  2. Create a flow of scores: 95, 80, 72
     *  3. Use zip to pair them: "Alice: 95", "Bob: 80", "Charlie: 72"
     *  4. Collect and call onReceive()
     *
     *  Note: if one flow has more items than the other, zip stops at the shorter one.
     *
     * HINT: flowA.zip(flowB) { a, b -> "$a: $b" }
     */
    fun startZipDemo(onReceive: (String) -> Unit) {
        // TODO 3 — your code here
    }

    // -------------------------------------------------------------------------
    // EXERCISE 4 — debounce (search box pattern)
    // Only emits after the value hasn't changed for X milliseconds.
    // -------------------------------------------------------------------------

    // This StateFlow simulates the search box input
    val searchQuery = MutableStateFlow("")

    /**
     * TODO 4:
     *  1. In startDebounceDemo(), collect searchQuery
     *  2. Apply debounce(500) — only proceed if no new value for 500ms
     *  3. Apply filter { it.isNotBlank() } — skip empty queries
     *  4. Apply distinctUntilChanged() — skip if same as previous
     *  5. For each passing value, call FakeDataSource.search(query)
     *  6. Call onReceive() with the results
     *
     *  In the Activity, the search box updates searchQuery on every keystroke.
     *  Without debounce, every letter triggers a network call. With it, only
     *  the "settled" value after the user pauses typing does.
     *
     * HINT: searchQuery.debounce(500).filter { ... }.distinctUntilChanged().collect { ... }
     */
    fun startDebounceDemo(onReceive: (String) -> Unit) {
        // TODO 4 — your code here
    }

    // Update called from Activity's search EditText
    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    // -------------------------------------------------------------------------
    // EXERCISE 5 — flatMapLatest
    // When a new value arrives, CANCELS the previous inner flow and starts fresh.
    // Essential for search: don't care about stale results.
    // -------------------------------------------------------------------------

    /**
     * TODO 5:
     *  1. Create a flow that emits user IDs: 1, 2, 3 with 300ms delay between each
     *  2. Use flatMapLatest { userId ->
     *         flow {
     *             emit("Loading user $userId...")
     *             delay(600)   // simulate slow fetch
     *             emit(FakeDataSource.fetchUser(userId))
     *         }
     *     }
     *  3. Collect and call onReceive()
     *
     *  Observe: "Loading user 1..." and "Loading user 2..." are likely cancelled
     *  before their 600ms fetch completes. Only user 3 fully loads.
     *  This is the correct pattern for type-ahead search — cancel stale requests.
     */
    fun startFlatMapLatestDemo(onReceive: (String) -> Unit) {
        // TODO 5 — your code here
    }
}
