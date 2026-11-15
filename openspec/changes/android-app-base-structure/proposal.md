# Proposal

## Why

The repository contains only OpenSpec configuration and a Copilot setup workflow. There is no Android project yet, so no feature work (audio capture, pitch detection, notation rendering) can start. A minimal, buildable Jetpack Compose app skeleton plus a GitHub Actions workflow that produces a debug APK establishes the foundation every later change builds on. Because local builds are not allowed by the project constraints, the CI pipeline is the only way to verify that the project compiles.

## What Changes

- Add a single-module Android project (Kotlin DSL Gradle build) using Jetpack Compose with Compile SDK 36, Target SDK 36, and Min SDK 26.
- Add an application shell: one entry activity hosting a placeholder Compose screen, with a package layout prepared for MVVM / Clean Architecture (`ui`, `domain`, `data` packages) without implementing any feature logic.
- Add a Compose Material theme supporting White, Dark, and System Default modes.
- Add German and English string resources (German as a translation of the default English strings) so that UI text is localizable from the start.
- Add a GitHub Actions workflow that builds the debug APK on push and pull request, runs the unit tests, and uploads the APK as a workflow artifact.
- Add a Gradle wrapper and a `.gitignore` for Android build outputs.
- Out of scope for this change: Room, Oboe/Aubio/NDK integration, MusicXML rendering, Play Store / release signing, adjustable font size, and any exercise UI.

## Capabilities

### New Capabilities

- `android-app-shell`: The buildable Android project structure, Compose entry point, theme modes, supported SDK levels, and localized resource baseline that all later features build on.
- `debug-apk-build`: The GitHub Actions pipeline that builds, tests, and publishes a debug APK for every push and pull request, since local builds are not permitted.

### Modified Capabilities

- None. No existing specs exist in `openspec/specs/`.

## Impact

- **New code**: `app/` Android module, root Gradle files (`settings.gradle.kts`, `build.gradle.kts`, `gradle/libs.versions.toml`, `gradle.properties`), Gradle wrapper, `.gitignore`.
- **New CI**: `.github/workflows/` debug build workflow, separate from the existing `copilot-setup-steps.yml`, which remains unchanged.
- **Dependencies**: AndroidX Core KTX, Lifecycle, Activity Compose, Compose BOM / Material 3, JUnit, and the Android Gradle Plugin with Kotlin. Exact versions are chosen in the design.
- **Systems**: GitHub Actions minutes and artifact storage for each debug build.
- **Constraints carried from project context**: Min SDK 26, Target SDK 36, Gradle Kotlin DSL, no local builds, and MVVM / Clean Architecture layering.
