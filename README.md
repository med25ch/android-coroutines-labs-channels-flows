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

## ▶️ Getting Started

1. Clone the repository
2. Open in Android Studio
3. Run the app
4. Start from the **MainActivity**
5. Pick a lab and implement the TODOs in the ViewModel

---
