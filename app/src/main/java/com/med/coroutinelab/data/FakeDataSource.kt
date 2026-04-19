package com.med.coroutinelab.data

import kotlinx.coroutines.delay

/**
 * Simulates a real data source with network-like delays.
 * Used across all lab exercises so you focus on coroutine patterns,
 * not on wiring up real APIs.
 */
object FakeDataSource {

    // Simulates a single network fetch
    suspend fun fetchUser(id: Int): String {
        delay(500)
        return "User_$id"
    }

    // Simulates fetching a page of items
    suspend fun fetchItems(page: Int): List<String> {
        delay(300)
        val start = page * 5
        return (start until start + 5).map { "Item_$it" }
    }

    // Simulates a stock price ticker
    suspend fun fetchStockPrice(ticker: String): Double {
        delay(200)
        return (50..500).random().toDouble() + Math.random()
    }

    // Simulates a search query
    suspend fun search(query: String): List<String> {
        delay(400)
        val all = listOf("Apple", "Apricot", "Banana", "Blueberry", "Cherry",
            "Coconut", "Date", "Elderberry", "Fig", "Grape")
        return all.filter { it.contains(query, ignoreCase = true) }
    }

    // Simulates a login call
    suspend fun login(username: String, password: String): Result<String> {
        delay(800)
        return if (username == "med" && password == "1234") {
            Result.success("Welcome, $username!")
        } else {
            Result.failure(Exception("Invalid credentials"))
        }
    }

    // Simulates a sensor/stream that emits values over time
    suspend fun emitTemperature(onEmit: suspend (Double) -> Unit) {
        var temp = 20.0
        while (true) {
            delay(1000)
            temp += (-1..1).random()
            onEmit(temp)
        }
    }
}
