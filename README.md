# 🚀 Android Coroutines Labs – Channels & Flows

This repository is a hands-on practice project designed to master **Kotlin Coroutines**, specifically **StateFlow, SharedFlow, and Channels** in an Android environment.

It contains structured lab activities with pre-built UI and ViewModels, allowing you to focus on implementing reactive patterns and understanding asynchronous data streams.

---

## 📚 Project Overview

This project simulates real-world async scenarios using a **FakeDataSource**, helping you practice:

- State management with **StateFlow**
- Event handling with **SharedFlow**
- One-time events with **Channels**
- Lifecycle-aware collection in Android
- Separation of concerns with **ViewModel**

---

## 🧪 Lab Activities

The project includes:

- ✅ 7 Lab Activities
- ✅ Dedicated ViewModel per lab
- 📝 TODOs inside ViewModels for guided implementation
- 🧩 XML layouts already prepared
- 📱 A `MainActivity` acting as a navigation menu between labs

Each lab focuses on a specific concept:

| Lab | Concept |
|-----|--------|
| 1 | Basic Flow collection |
| 2 | StateFlow for UI state |
| 3 | SharedFlow for events |
| 4 | Channels for one-time events |
| 5 | Combining Flows |
| 6 | Error handling in flows |
| 7 | Advanced patterns (buffer, debounce, etc.) |

---

## 🏗️ Architecture

- **MVVM (Model-View-ViewModel)**
- UI: XML + Activities
- Async: Kotlin Coroutines + Flow APIs
- Data Layer: `FakeDataSource`

---

## 🧠 Learning Goals

By completing this project, you will:

- Understand when to use **StateFlow vs SharedFlow vs Channel**
- Handle UI state and events correctly
- Avoid common pitfalls (replay issues, lost events, etc.)
- Build reactive Android UIs

---

## ⚙️ Tech Stack

- Kotlin
- Android SDK
- Coroutines
- Flow / StateFlow / SharedFlow / Channel

---

## 🧪 Labs Structure

### 🏠 Core Components
```text
MainActivity        → Lab menu (7 buttons)
FakeDataSource      → Shared async simulator (login, search, prices, etc.)

├─ Rendezvous / Buffered / Conflated / Unlimited
├─ close() + for loop iteration
└─ Bonus: send to closed channel

├─ produce{} builder (auto-close)
├─ Chained pipeline (square numbers)
├─ Fan-Out: 3 workers sharing one channel
└─ Fan-In: 3 producers → 1 consumer

├─ Sealed UiEvent class (Toast, Dialog, Navigate, Error)
├─ receiveAsFlow() exposure pattern
├─ Async login → navigation event
└─ Rotation test (proves no double-fire)

├─ Cold flow (collected twice to prove re-execution)
├─ flowOf() and asFlow()
├─ Lazy execution (nothing runs before collect)
└─ Cancellation (auto-cleanup)

├─ StateFlow counter with update{}
├─ StateFlow with data class (loading/error/success)
├─ SharedFlow broadcast (two collectors, both receive)
└─ replay=1 rotation bug (the reason Channel exists)

├─ map + filter
├─ combine (latest from each, timeline exercise)
├─ zip (strict 1-to-1 pairing)
├─ debounce + distinctUntilChanged (live search box)
└─ flatMapLatest (cancel stale requests)

├─ The memory leak (wrong way, visible in Logcat)
├─ repeatOnLifecycle(STARTED) — the right way
├─ stateIn() + WhileSubscribed(5_000)
└─ SharingStarted options comparison


## ▶️ Getting Started

1. Clone the repository
2. Open in Android Studio
3. Run the app
4. Start from the **MainActivity**
5. Pick a lab and implement the TODOs in the ViewModel

---
