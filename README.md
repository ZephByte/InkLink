# InkLink

A native companion app for e-readers running [CrossPoint](https://github.com/crosspoint-reader/crosspoint-reader). Manage your library, fonts, and files from your phone, then get straight to reading.

> **Unofficial community project.** InkLink is not made by the CrossPoint team, but is built to work with CrossPoint firmware.

---

## What It Does

Doing things on an e-reader is hard. InkLink puts the work on your phone:

- **Send books** - Upload EPUBs directly to your device
- **Manage files** - Browse, move, rename, and delete files on your reader
- **Custom fonts** - Upload `.inx` font packs without touching the web UI
- **Wallpapers** - Design and push wallpapers dithered for e-ink displays
- **Device info** - See your device name, firmware version, storage usage, and whether an update is available
- **Multiple devices** - Manage more than one CrossPoint device from a single app

Handle it on your phone, it ends up on your reader.

---

## Platforms

| Platform | Language | UI |
|----------|----------|----|
| Android | Kotlin | Jetpack Compose |
| iOS | Swift | SwiftUI |

Shared business logic (networking, sync, WebDAV, image processing) is written once in Kotlin via [Kotlin Multiplatform (KMP)](https://kotlinlang.org/docs/multiplatform.html). UI is fully native on each platform, no shared UI layer.

Both platforms support **light and dark mode**, system-adaptive.

---

## Connecting to Your Device

InkLink connects over your local WiFi network. The CrossPoint device needs to be in **Sync mode** (accessible from the network menu on the reader) for the app to discover it.

1. On your reader: open the network menu and start Sync mode
2. On your phone: open InkLink, it discovers the device automatically via mDNS
3. Done. The app handles everything from there

> **Future:** BLE-wake support is planned, which will allow the app to trigger the connection without touching the reader first.

---

## Design Principles

- **Ease of use over feature count.** A small number of things that work well beats a large number of things that don't.
- **Native feel.** Each platform uses its own UI conventions. Compose on Android, SwiftUI on iOS.
- **Light and dark mode are first-class**, not an afterthought.
- **The phone does the work.** Heavy lifting (image dithering, file prep) happens on the phone so the reader just receives a ready file.

---

## Supported Devices

InkLink targets any device running CrossPoint firmware. Currently tested on:

- Xteink X4
- Xteink X3

More devices will be added as they become available for testing.

---

## Building Locally

### Prerequisites

- Android Studio (latest stable) with the [Kotlin Multiplatform plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform)
- Xcode 16+ (iOS builds)
- CocoaPods (`sudo gem install cocoapods`)
- Run `kdoctor` to verify your KMP environment: `brew install kdoctor && kdoctor`

### Android

Open the project root in Android Studio and run the `androidApp` configuration.

### iOS

```bash
cd iosApp
pod install
open InkLink.xcworkspace
```

Then build and run from Xcode.

### Shared module only

```bash
./gradlew :shared:build
```

---

## Running Tests

```bash
# Shared KMP tests
./gradlew :shared:test

# Android tests
./gradlew :androidApp:test

# iOS tests (or run from Xcode):
xcodebuild test -workspace iosApp/InkLink.xcworkspace -scheme InkLink -destination 'platform=iOS Simulator,name=iPhone 16'
```

All tests run automatically on every pull request via GitHub Actions. PRs cannot be merged unless all checks pass.

---

## Contributing

Contributions are welcome. InkLink is a community project. The `sharedLogic` module is plain Kotlin, so no mobile experience is required to contribute there.

See [CONTRIBUTING.md](CONTRIBUTING.md) for the full guide: branching conventions, commit style, test requirements, and PR guidelines.

For larger changes, open an issue first to discuss the approach before building it.

### Developer docs

| Document | What it covers |
|----------|---------------|
| [`docs/kmp-setup.md`](docs/kmp-setup.md) | KMP toolchain setup, expect/actual, common gotchas |
| [`docs/architecture.md`](docs/architecture.md) | Module structure and how everything fits together |
| [`docs/crosspoint-protocol.md`](docs/crosspoint-protocol.md) | CrossPoint mDNS, WebDAV, and API notes |

### User guide

The [`wiki/`](wiki/Home.md) folder contains user-facing documentation for all app features. It mirrors the [GitHub Wiki](https://github.com/ZephByte/InkLink/wiki) and is updated as features ship.

---

## Roadmap

- [x] Project setup and architecture
- [ ] Device discovery (mDNS)
- [ ] Book upload
- [ ] File browser (WebDAV)
- [ ] Font management
- [ ] Wallpaper generation and push
- [ ] Device info + firmware version check
- [ ] Multiple device support
- [ ] In-app EPUB reader with position sync
- [ ] BLE-wake (connect without touching the reader)
- [ ] OTA firmware updates from app

---

## License

[MIT](LICENSE)

---

## Related

- [CrossPoint Firmware](https://github.com/crosspoint-reader/crosspoint-reader) - the open-source firmware InkLink connects to
- [Xteink](https://xteink.com) - the e-reader hardware
