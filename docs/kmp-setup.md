# KMP Setup Guide

Setup guide for InkLink contributors. Covers the toolchain, how KMP works in this project, and common gotchas. Assumes you know Android or iOS but haven't worked with KMP before.

---

## What Is KMP?

Kotlin Multiplatform lets you write shared Kotlin code that compiles to both the JVM (Android) and native binaries (iOS). InkLink uses it for everything that isn't UI: networking, WebDAV, device models, sync logic, and anything else that shouldn't be written twice.

The shared code lives in `sharedLogic/`. Platform-specific UI lives in `androidApp/` (Kotlin + Compose) and `iosApp/` (Swift + SwiftUI). The iOS app consumes the shared module as an XCFramework built by the Kotlin compiler.

---

## Environment Setup

### 1. JDK

KMP requires JDK 17 or later. Check what you have:

```bash
java -version
```

If you need to install or switch versions, [SDKMAN](https://sdkman.io) is the easiest way:

```bash
sdk install java 17.0.11-tem
sdk use java 17.0.11-tem
```

### 2. Android Studio

Install [Android Studio](https://developer.android.com/studio) (latest stable).

Then install the **Kotlin Multiplatform plugin**:
- Open Android Studio → Settings → Plugins → Marketplace
- Search for "Kotlin Multiplatform" and install it
- Restart Android Studio

### 3. Xcode

Install Xcode 16 or later from the Mac App Store. After installing, accept the license and install command-line tools:

```bash
sudo xcodebuild -license accept
xcode-select --install
```

### 4. CocoaPods

InkLink uses CocoaPods to integrate the KMP framework into the iOS project:

```bash
sudo gem install cocoapods
```

If you're on an Apple Silicon Mac and encounter issues, try:

```bash
sudo arch -x86_64 gem install cocoapods
```

### 5. Verify with kdoctor

`kdoctor` checks your entire KMP environment and tells you what's missing or misconfigured:

```bash
brew install kdoctor
kdoctor
```

Fix any errors `kdoctor` reports before continuing. Warnings can usually be ignored.

---

## Project Modules

```
InkLink/
├── sharedLogic/          # Shared Kotlin code (your starting point for most contributions)
│   └── src/
│       ├── commonMain/   # Runs on all platforms
│       ├── androidMain/  # Android-specific implementations
│       ├── iosMain/      # iOS-specific implementations
│       ├── commonTest/   # Tests that run on all platforms
│       └── androidHostTest/  # Tests that run on a connected Android device/emulator
├── sharedUI/             # Shared Compose Multiplatform resources and theme
├── androidApp/           # Android host application
└── iosApp/               # iOS host application (Xcode project)
```

When you write code in `commonMain`, it compiles to both platforms. When you need to use a platform API that doesn't exist in common Kotlin, you use the **expect/actual** mechanism (see below).

---

## Expect / Actual

This is the core KMP pattern for platform-specific code. Here's how it works:

In `commonMain`, you declare what you need:

```kotlin
// commonMain
expect fun getPlatform(): Platform

expect class PlatformLogger {
    fun log(message: String)
}
```

In `androidMain` and `iosMain`, you provide the implementation:

```kotlin
// androidMain
actual fun getPlatform(): Platform = AndroidPlatform()

actual class PlatformLogger {
    actual fun log(message: String) = android.util.Log.d("InkLink", message)
}
```

```kotlin
// iosMain
actual fun getPlatform(): Platform = IOSPlatform()

actual class PlatformLogger {
    actual fun log(message: String) = println(message)  // or NSLog
}
```

If you add an `expect` declaration in `commonMain`, the build will fail until both `androidMain` and `iosMain` have matching `actual` implementations.

---

## Building the Project

### Shared logic only

```bash
./gradlew :sharedLogic:build
```

### Android app

Open the project root in Android Studio and run the `androidApp` run configuration, or:

```bash
./gradlew :androidApp:assembleDebug
```

### iOS app

The iOS build requires the shared framework to be built first. CocoaPods handles this, but you need to run `pod install` after cloning or after any changes to `sharedLogic/build.gradle.kts`:

```bash
cd iosApp
pod install
open InkLink.xcworkspace  # Always open .xcworkspace, not .xcodeproj
```

Then build and run from Xcode as normal. The KMP framework is rebuilt automatically as part of the Xcode build phase.

> **Important:** Always open `InkLink.xcworkspace`, not `InkLink.xcodeproj`. The workspace includes the CocoaPods dependencies.

---

## Running Tests

```bash
# Common tests (run on JVM, fast)
./gradlew :sharedLogic:testDebugUnitTest

# Android host tests (requires emulator or device)
./gradlew :sharedLogic:connectedAndroidTest

# iOS tests
xcodebuild test \
  -workspace iosApp/InkLink.xcworkspace \
  -scheme InkLink \
  -destination 'platform=iOS Simulator,name=iPhone 16'
```

Write tests in `commonTest` whenever possible. They run on both platforms and catch platform-specific divergence.

---

## Common Gotchas

**"Unresolved reference" errors in commonMain**

You can only use APIs that exist in the Kotlin standard library or explicitly declared multiplatform dependencies. If you try to use an Android or iOS API directly in `commonMain`, you'll get an unresolved reference. Move that code to `androidMain`/`iosMain` and wire it up via expect/actual.

**CocoaPods sync after Gradle changes**

If you modify `sharedLogic/build.gradle.kts` (e.g., add a dependency), re-run `pod install` in `iosApp/` before building from Xcode. Android Studio handles this automatically; Xcode does not.

**Kotlin version mismatches**

All KMP dependencies must use the same Kotlin version. Check `gradle/libs.versions.toml` for the current version. If you add a dependency that pulls in a different Kotlin version, the build will warn or fail.

**`actual` implementations in the wrong source set**

`androidMain` implementations are JVM-based. `iosMain` implementations compile to native Kotlin. Some JVM APIs (`java.util.*`, `java.io.*`) are not available in `iosMain`. Use the Kotlin standard library equivalents instead.

**Android emulator vs. real device**

mDNS discovery (how InkLink finds CrossPoint devices) may not work reliably on Android emulators. Test device discovery on a real Android device.

---

## Useful Resources

- [Kotlin Multiplatform docs](https://kotlinlang.org/docs/multiplatform.html)
- [KMP samples](https://github.com/Kotlin/multiplatform-samples)
- [Ktor (HTTP client for KMP)](https://ktor.io/docs/client-create-multiplatform-application.html)
- [Kotlin Coroutines on iOS](https://kotlinlang.org/docs/multiplatform-mobile-concurrency-and-coroutines.html)
- [kdoctor](https://github.com/Kotlin/kdoctor)
