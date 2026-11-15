# Design

## Context

The repository has no Android project. Project constraints (see `openspec/config.yaml`) fix the platform range (Min SDK 26, Target/Compile SDK 36), the language (Kotlin), the UI toolkit (Jetpack Compose), the architecture (MVVM / Clean Architecture with Coroutines and Flow), the build system (Gradle Kotlin DSL), and the rule that builds run only in GitHub Actions. See [proposal.md](./proposal.md) for motivation and [specs/android-app-shell/spec.md](./specs/android-app-shell/spec.md) and [specs/debug-apk-build/spec.md](./specs/debug-apk-build/spec.md) for requirements.

The existing `.github/workflows/copilot-setup-steps.yml` is owned by OpenSpec tooling and is left untouched.

## Goals / Non-Goals

**Goals:**
- A project that compiles, runs unit tests, and produces a debug APK from a clean checkout in CI.
- A package layout that gives later changes a clear place for `ui`, `domain`, and `data` code without implementing any of it now.
- A single place for dependency versions so later changes do not re-decide them.

**Non-Goals:**
- Feature code, Room, audio, NDK/CMake, notation rendering, release signing, Play Store upload, and font-size settings.
- Instrumented (device) tests and the Huawei P30 validation. Compose UI tests run on the JVM with Robolectric; physical-device validation is deferred to a later change.
- Emulator-based CI jobs.

## Decisions

**Single `app` module, no multi-module split yet.** A single module keeps the skeleton small. Layers are separated by packages (`ui`, `domain`, `data`). Splitting into Gradle modules is deferred until there is enough code to justify the build-time cost. *Alternative considered:* a multi-module layout from day one (`core`, `feature-*`). Rejected as premature for an empty app.

**Version catalog in `gradle/libs.versions.toml`.** All plugin and library versions live in one catalog, referenced from the Kotlin DSL build scripts. *Alternative:* inline version strings in `build.gradle.kts`. Rejected because it spreads version decisions across files.

**Compose via the Compose BOM and Material 3.** The BOM pins the Compose library versions consistently. Material 3 provides the color schemes needed for White, Dark, and System Default modes. The AGP version must support compileSdk 36; the exact versions are chosen when the catalog is written and verified by the first CI run.

**Theme mode handled in the app, not the OS.** An in-app selector changes the Compose theme parameter between White, Dark, and System Default. System Default maps to `isSystemInDarkTheme()`. The mode is held as in-memory state in this change. Persistence is deferred until a settings change exists.

**Localization via `values/` (English) and `values-de/` (German).** English is the default resource, so unsupported locales fall back to English automatically. This satisfies the fallback scenario in the spec without extra code.

**Responsive layout by using window-size-independent Compose layouts.** The placeholder screen uses `Scaffold`, padding from system insets, and content that can wrap. Adaptive navigation and tablet-specific layouts are deferred. Rotation is handled by Compose; the activity does not set a fixed orientation.

**Workflow triggers: `push` (all branches) and `pull_request`.** Covers the "push to any branch" and "pull request" scenarios in the spec. Concurrency cancels superseded runs for the same ref.

**CI steps:** checkout → set up JDK 17 (Temurin) → set up Gradle with `gradle/actions/setup-gradle` (caching) → run `./gradlew testDebugUnitTest assembleDebug` → upload the APK with `actions/upload-artifact` under the name `debug-apk-<short-sha>`. Running the tests and the assemble in one Gradle invocation means a test failure stops the run before the upload step. The upload step only runs on success, which satisfies the "no artifact on failure" scenario.

**Permissions: `contents: read` at workflow level.** Matches the read-only requirement. No secrets are referenced, so debug builds also work for forked pull requests where secrets are withheld.

**Gradle wrapper committed.** The wrapper is required so CI runs a pinned Gradle version. It is generated once with a standalone Gradle installation (this is wrapper generation, not a project build), and the wrapper jar and scripts are committed.

**Build outputs and local files ignored.** A standard Android `.gitignore` covers `build/`, `.gradle/`, `local.properties`, and IDE files.

**Application ID placeholder.** The namespace and application ID use a placeholder package `com.example.notetrainer`, since no organization domain is defined yet. Renaming is a mechanical change made before the first release, not before feature work.

## Risks / Trade-offs

- **Unverified build** → Local builds are not allowed, so the first CI run is the only compile check. Mitigation: keep the skeleton minimal so the first failure is easy to diagnose, and make the task list end with a confirmed green CI run.
- **Version drift between AGP, Kotlin, and Gradle** → A mismatched trio fails at configuration time. Mitigation: pin versions in the catalog and use the Kotlin/AGP combination documented for compileSdk 36 at implementation time.
- **Placeholder package name** → Renaming later touches every file. Mitigation: rename before any release; the rename is mechanical.
- **Wrapper generation outside the project build** → The wrapper must be created with a Gradle installation. Mitigation: this is a one-time generation step recorded in the tasks.
- **Huawei P30 runs without Google Play services** → Not relevant to a debug APK installed directly, but the first device test must not depend on Play-delivered features. Mitigation: no Play services dependencies in this change.

## Migration Plan

Not applicable: the repository has no existing app to migrate. Rollback is removing the added files and the workflow. The existing Copilot setup workflow is unaffected.

## Open Questions

- The final application ID and organization package. Deferred: the placeholder is used until the owner decides; changing it does not alter any requirement in the specs.
