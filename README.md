# AD 1 NG Digital Mixer — Android source

Native Android Java project, min Android 8.0 (API 26), no third-party UI library.

## Implemented in source
- Dark UI with orange accents.
- Pick and play an audio file from Android's document picker; play/pause/stop.
- Attempts to use Android Equalizer, BassBoost and PresetReverb effects on the built-in player audio session.
- Music/vocal preset buttons and five EQ sliders.
- Uses Android's selected output (speaker/headset/Bluetooth/USB when supported).

## Limitations (important)
- This is source code, **not a tested APK**. Build has not been verified on a physical phone.
- Audio effects depend on the Android device's audio-effect implementation; they may be unavailable on some phones.
- Delay, compressor, system-wide audio processing, and direct OBS/TikTok streaming are not implemented.
- Android generally does not allow a normal app to process all audio from TikTok/YouTube/other apps. This player processes audio played inside this app.

## Build from phone using GitHub Actions
1. Extract this ZIP, then upload the contents of the folder into a GitHub repository (the files should be at repository root).
2. Make sure `.github/workflows/android.yml` is uploaded.
3. Open **Actions** and select **Build Android APK**. Run workflow if it has not started automatically.
4. When successful, open the workflow run and download artifact `AD1NG-Digital-Mixer-debug-apk`.
5. Extract the artifact ZIP and install `app-debug.apk` on the Android phone. Allow installation from that source if Android asks.

GitHub Actions downloads Gradle and Android Gradle Plugin from the internet. This workflow has been provided but has not been run/verified in this environment.
