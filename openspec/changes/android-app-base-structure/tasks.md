# Tasks

## 1. Gradle project scaffolding

- [x] 1.1 Create `settings.gradle.kts` (root project `NoteTrainer`, `:app` module, plugin repositories) and `build.gradle.kts` (root, applying plugins with `apply false`); verify both files exist and reference the same plugin aliases as the catalog
- [x] 1.2 Create `gradle/libs.versions.toml` with versions for AGP (supporting compileSdk 36), Kotlin, Compose BOM, Material 3, Activity Compose, Core KTX, Lifecycle, and JUnit; verify every alias used in later tasks is defined there
- [x] 1.3 Create `gradle.properties` (AndroidX enabled, non-transitive R classes, JVM args); verify the file contains `android.useAndroidX=true`
- [x] 1.4 Generate the Gradle wrapper (`gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.properties`, `gradle-wrapper.jar`) with a standalone Gradle installation and commit it; verify `gradle/wrapper/gradle-wrapper.properties` pins a Gradle version compatible with the chosen AGP
- [x] 1.5 Create the standard Android `.gitignore` covering `build/`, `.gradle/`, `local.properties`, `.idea/`, and `*.iml`; verify `git check-ignore app/build` reports the path as ignored

## 2. Application module

- [x] 2.1 Create `app/build.gradle.kts` with namespace and application ID `com.example.notetrainer`, `compileSdk = 36`, `minSdk = 26`, `targetSdk = 36`, Compose enabled, and Java/Kotlin target 17; verify the values match the platform requirement in the spec
- [x] 2.2 Create `app/src/main/AndroidManifest.xml` with `MainActivity` as the launcher activity and the app label set to `@string/app_name`; verify the manifest declares no permissions
- [x] 2.3 Create the package layout `ui/`, `domain/`, `data/` under `com.example.notetrainer` with a short placeholder file in each so the empty packages are tracked; verify the directories exist in the repository tree
- [ ] 2.4 Create `MainActivity` that calls `enableEdgeToEdge()` and sets the Compose content to the app theme and the launch screen; verify it compiles in CI (see task 5.2)

## 3. Theme and launch screen

- [ ] 3.1 Create the Material 3 theme in `ui/theme/` with a `ThemeMode` enum (`WHITE`, `DARK`, `SYSTEM`) and a theme composable that resolves dark or light colors per mode, using `isSystemInDarkTheme()` for `SYSTEM`; verify by a JUnit test of the mode-to-dark-flag resolution function that covers all three modes and both system settings
- [ ] 3.2 Create the launch screen composable in `ui/` using `Scaffold` with a top bar titled with `@string/app_name` and body content that wraps and does not clip; verify with a Compose UI test that the title node is displayed (run via CI in task 5.3)
- [ ] 3.3 Add a `Modifier`-based layout that stays within window insets in portrait and landscape and does not stretch content across tablet width (max content width); verify with a Compose UI test that asserts the content width is capped on a wide test surface

## 4. Localized resources

- [x] 4.1 Create `app/src/main/res/values/strings.xml` in English with `app_name` and the placeholder body text; verify no hard-coded user-visible strings remain in the Kotlin sources (grep for string literals in `ui/`)
- [x] 4.2 Create `app/src/main/res/values-de/strings.xml` with German translations of every key in `values/strings.xml`; verify both files define the same set of keys using a CI-run check or a short grep comparison documented in the commit message

## 5. Debug APK workflow

- [x] 5.1 Create `.github/workflows/debug-apk.yml` with triggers `push` (all branches) and `pull_request`, `permissions: contents: read`, and a concurrency group that cancels superseded runs of the same ref; verify the file parses as YAML and the trigger block matches the spec
- [ ] 5.2 Add the build job to the workflow: checkout, set up Temurin JDK 17, set up Gradle with `gradle/actions/setup-gradle`, and run `./gradlew testDebugUnitTest assembleDebug`; verify the first run compiles the app and the unit tests from tasks 3.1 pass in GitHub Actions
- [ ] 5.3 Add the APK upload step with `actions/upload-artifact`, running only on success, uploading `app/build/outputs/apk/debug/*.apk` as `debug-apk-<short-sha>`; verify the workflow run shows the artifact and the artifact name contains the commit SHA
- [ ] 5.4 Verify the failure path: push a deliberately failing unit test on a throwaway branch, confirm the run is red and no artifact is uploaded, then remove the test and confirm the run is green again

## 6. Documentation

- [x] 6.1 Add a short `README.md` describing the project, the rule that builds run only in GitHub Actions, where to download the debug APK from workflow artifacts, and how to install it on a device; verify the README links to the workflow file path
