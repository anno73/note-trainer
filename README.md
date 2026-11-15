# Note Trainer

An Android app foundation for practicing music reading, built with Kotlin and
Jetpack Compose. The app supports Android 8.0 (API 26) and newer.
The launch screen lets you choose a light, dark, or system theme. This
selection is kept for the current app session and is not persisted as a setting.

## Debug APK

Builds and tests run only in GitHub Actions; do not build the project locally.
The [Debug APK workflow](.github/workflows/debug-apk.yml) runs on pushes and
pull requests. Open a successful workflow run in the repository's **Actions**
tab and download its `debug-apk-<commit>` artifact. Extract the APK from the
downloaded archive.

To install it on a device, transfer the APK to the device and open it, allowing
installation from that source if Android asks. Alternatively, with Android
Debug Bridge configured and a device connected, run `adb install -r app-debug.apk`.
Debug APKs are not release-signed and are intended for testing only.
