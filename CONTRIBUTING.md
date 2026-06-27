# Contributing to InkLink

InkLink is a community project. Bug reports, documentation, feature work, and design feedback are all welcome.

---

## Before You Start

For anything larger than a typo fix, **open an issue first** to discuss the approach. This keeps effort from being duplicated and makes sure a change fits the project's direction before you build it.

For small fixes and clearly-scoped improvements, a PR without a prior issue is fine.

---

## Project Structure

```
InkLink/
├── sharedLogic/   # KMP module: networking, WebDAV, sync logic, models (Kotlin only)
├── sharedUI/      # KMP module: shared Compose resources and theme tokens
├── androidApp/    # Android host app (Kotlin + Jetpack Compose)
├── iosApp/        # iOS host app (Swift + SwiftUI)
└── docs/          # Developer guides
```

If you're unfamiliar with Kotlin Multiplatform, start with [`docs/kmp-setup.md`](docs/kmp-setup.md). The shared logic module is plain Kotlin, so no mobile experience is required to contribute there.

See [`docs/architecture.md`](docs/architecture.md) for a full breakdown of how the modules fit together.

---

## Getting Set Up

### Prerequisites

- Android Studio (latest stable) with the [Kotlin Multiplatform plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform)
- Xcode 16+ (for iOS builds and simulator)
- CocoaPods: `sudo gem install cocoapods`
- Run `kdoctor` to verify everything is wired up:

```bash
brew install kdoctor && kdoctor
```

### Clone and build

```bash
git clone https://github.com/ZephByte/InkLink.git
cd InkLink
./gradlew :sharedLogic:build
```

iOS setup:

```bash
cd iosApp
pod install
open InkLink.xcworkspace
```

---

## Making a Change

1. Fork the repo
2. Create a branch from `main`:
   ```bash
   git checkout -b feat/your-feature
   ```
3. Make your changes in the relevant module
4. Add or update tests for anything you changed
5. Run the test suite locally (see below) and confirm it passes
6. Open a PR against `main`

### Branch naming

| Prefix | Use for |
|--------|---------|
| `feat/` | New feature |
| `fix/` | Bug fix |
| `docs/` | Documentation only |
| `refactor/` | Code restructuring, no behavior change |
| `chore/` | Tooling, dependencies, CI |

### Commit style

```
feat: add mDNS device discovery
fix: crash on empty file list response
docs: expand kmp-setup guide
```

One line, present tense, lowercase, no period. Keep the scope tight. A commit should do one thing.

---

## Running Tests

```bash
# Shared KMP tests
./gradlew :sharedLogic:test

# Android tests
./gradlew :androidApp:test

# iOS tests (simulator required)
xcodebuild test \
  -workspace iosApp/InkLink.xcworkspace \
  -scheme InkLink \
  -destination 'platform=iOS Simulator,name=iPhone 16'
```

All tests run automatically in CI on every PR. A PR cannot be merged unless all checks pass.

---

## Pull Request Guidelines

- **One PR, one concern.** If you're fixing a bug and spot a refactor opportunity, open two PRs.
- **Write a clear description.** Explain what the change does and why. Link the related issue if there is one.
- **Don't break existing tests.** If your change requires updating a test, that's fine; explain why in the PR description.
- **Platform changes need screenshots.** UI changes on Android or iOS should include before/after screenshots in the PR description.
- **Keep the diff small.** A focused PR is easier to review than a sweeping change.

---

## Where Things Live

| Work area | Module | Language |
|-----------|--------|----------|
| Network / WebDAV / mDNS | `sharedLogic/` | Kotlin (common) |
| Device models, business logic | `sharedLogic/` | Kotlin (common) |
| Platform-specific Android logic | `sharedLogic/androidMain/` | Kotlin |
| Platform-specific iOS logic | `sharedLogic/iosMain/` | Kotlin |
| Android UI | `androidApp/` | Kotlin + Compose |
| iOS UI | `iosApp/` | Swift + SwiftUI |
| Shared theme / resources | `sharedUI/` | Kotlin (Compose Multiplatform) |

When in doubt, business logic and anything that touches the CrossPoint API goes in `sharedLogic`. UI stays in the platform-specific app module.

---

## Code Style

- Kotlin: follow standard [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html). The project uses `ktlint`; run `./gradlew ktlintCheck` before submitting.
- Swift: follow the [Swift API Design Guidelines](https://www.swift.org/documentation/api-design-guidelines/). Code is formatted with `swiftformat`.
- No commented-out code in PRs.
- Keep functions short. If a function is getting long, it probably has multiple responsibilities.

---

## Reporting Bugs

Open an issue with:
- Device and OS version
- App version (if installed from a release)
- Steps to reproduce
- What you expected vs. what happened
- Logs or crash output if available

---

## Questions

Open a GitHub Discussion or comment on the relevant issue. The project is small enough that informal discussion is fine.

---

## License

By contributing, you agree that your contribution will be licensed under the [MIT License](LICENSE).
