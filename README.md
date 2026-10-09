# AD 1 NG Digital Mixer — Android source

Native Android Java project for an Android debug APK.

## Features in this source
- Dark mixer-style interface with orange accents.
- Pick and play an audio file from Android's document picker; play/pause/stop.
- Attempts to use Android Equalizer, BassBoost and PresetReverb effects on the built-in player audio session.
- Music/vocal preset buttons and five EQ sliders.
- Uses the Android-selected speaker/headset/Bluetooth/USB output when supported by the phone.

## Important limitations
- This ZIP is source code, not an APK. An actual GitHub Actions run is still required to prove compilation.
- Audio effects depend on the Android device's implementation and may not be supported on every phone.
- Delay, compressor, system-wide audio processing, and direct OBS/TikTok streaming are not implemented.
- Android generally does not let a normal app process audio from other apps such as TikTok or YouTube. This player processes audio played inside this app.

## Build from an Android phone using GitHub Actions
1. Extract this ZIP using your phone's file manager.
2. Open the extracted `AD1NG_Digital_Mixer_Android` folder.
3. Upload the **contents inside that folder** to the top level of a GitHub repository. The `.github` folder must also be uploaded; do not upload only the ZIP file.
4. In GitHub, open the repository's **Actions** tab and select **Build Android APK**. If it has not started automatically, press **Run workflow**.
5. Wait for the run to finish. A green check means the build succeeded; a red X means it failed and the run's error log is needed to fix the exact cause.
6. For a successful run, open the run and download the artifact named `AD1NG-Digital-Mixer-debug-apk`. Extract that artifact ZIP and install `app-debug.apk` on the phone.

The workflow explicitly installs Gradle 8.9 and uses Java 17, matching the requirements of Android Gradle Plugin 8.7.3. This environment does not have the Android SDK/Gradle installed, so this package has been structurally checked but has **not** been compiled here.
