# Architecture

Update this document as the architecture evolves.

---

## Module Overview

```
InkLink/
├── sharedLogic/   # KMP: business logic, networking, models (no UI)
├── sharedUI/      # KMP: shared Compose resources, theme tokens
├── androidApp/    # Android app: Jetpack Compose UI
└── iosApp/        # iOS app: SwiftUI UI
```

### Dependency graph

```
androidApp  ──depends on──▶  sharedLogic
androidApp  ──depends on──▶  sharedUI

iosApp (Swift)  ──imports XCFramework──▶  sharedLogic
```

Neither `sharedLogic` nor `sharedUI` depends on the app modules. Dependencies only flow upward.

---

## sharedLogic

Everything that isn't UI lives here: network communication, WebDAV operations, mDNS discovery, device models, and sync logic.

**Source sets:**

| Source set | Compiles to | Use for |
|------------|-------------|---------|
| `commonMain` | Both platforms | Models, business logic, interfaces |
| `androidMain` | Android JVM | Android-specific platform implementations |
| `iosMain` | iOS native | iOS-specific platform implementations |
| `commonTest` | Both | Unit tests for shared logic |
| `androidHostTest` | Android device/emulator | Tests that need real Android APIs |
| `iosTest` | iOS simulator | iOS-specific tests |

**Exported to iOS as:** a static XCFramework named `SharedLogic.xcframework`, consumed via CocoaPods.

**Key areas (to be built):**

- `discovery/`: mDNS-based device discovery
- `network/`: HTTP client (Ktor), request/response models
- `webdav/`: WebDAV client for file operations
- `device/`: Device model, firmware version, storage info
- `sync/`: Book upload, file transfer coordination
- `fonts/`: `.inx` font pack handling
- `wallpaper/`: Image dithering for e-ink (Floyd-Steinberg or similar)

---

## sharedUI

Shared Compose Multiplatform resources and theme definitions. This module exists to:

- Provide a single source of truth for colors, typography, and spacing
- Host drawables, string resources, or other assets shared across both app targets

The UI itself is **not** shared. `androidApp` uses Jetpack Compose, `iosApp` uses SwiftUI. `sharedUI` provides the raw tokens and assets that each platform's UI consumes in its own way.

---

## androidApp

The Android host application. It imports `sharedLogic` and `sharedUI` as Gradle library modules. All Android UI is written in Kotlin with Jetpack Compose.

Structure (to be established):

```
androidApp/src/main/kotlin/dev/zephbyte/inklink/
├── MainActivity.kt          # Entry point
├── ui/
│   ├── theme/               # Material3 theme using sharedUI tokens
│   ├── screens/             # One folder per screen
│   └── components/          # Reusable composables
└── di/                      # Dependency injection (if used)
```

---

## iosApp

The iOS host application. It imports `sharedLogic` as an XCFramework via CocoaPods. All iOS UI is written in Swift with SwiftUI.

Structure (to be established):

```
iosApp/iosApp/
├── iOSApp.swift             # App entry point (@main)
├── ContentView.swift        # Root view
├── Views/                   # SwiftUI views by feature
├── ViewModels/              # ObservableObjects bridging SharedLogic to SwiftUI
└── Services/                # Swift wrappers for SharedLogic types
```

iOS cannot call Kotlin suspend functions directly. The pattern is to expose a callback or flow-based API from `sharedLogic` and wrap it in a Swift `async`/`await` adapter or a `StateObject` on the iOS side. Document this per-feature as it's built.

---

## Data Flow

A typical user action flows through the stack like this:

```
User action (Compose / SwiftUI)
    │
    ▼
ViewModel / StateObject
    │
    ▼
sharedLogic domain layer (suspend fun / Flow)
    │
    ├──▶ Network layer (Ktor HTTP client)
    │         │
    │         ▼
    │    CrossPoint device (WebDAV / HTTP API)
    │
    └──▶ Local state update → UI recompose
```

---

## Key Design Decisions

**Native UI, shared logic.** Jetpack Compose on Android and SwiftUI on iOS give each platform a native feel. The shared KMP module handles everything below the UI layer, so both platforms stay in sync on business logic without duplicating it.

**Static XCFramework.** The iOS module is built as a static framework (`isStatic = true`) for simpler distribution and no dynamic linker overhead.

**No dependency injection framework (for now).** The project starts with manual wiring. Add a DI framework (Koin is the most KMP-friendly option) if the dependency graph becomes unmanageable.

**Coroutines throughout.** All async work uses Kotlin coroutines. On Android, this is natural. On iOS, use `kotlinx-coroutines-core` and expose the API via callbacks or via [SKIE](https://skie.touchlab.co) to get Swift async/await interop.

---

## Adding a New Feature

1. Define the model and interface in `sharedLogic/commonMain`
2. Implement any platform-specific behavior in `androidMain` / `iosMain` via expect/actual
3. Write tests in `commonTest`
4. Build the Android UI in `androidApp` calling the shared logic
5. Build the iOS UI in `iosApp`, wrapping the Kotlin API in a Swift ViewModel
6. Update this document if the architecture changes
