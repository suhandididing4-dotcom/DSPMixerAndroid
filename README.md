# EqualizerTube V2 — READY TO BUILD

Paket ini sudah disiapkan khusus untuk GitHub Actions.

## PENTING
Upload **isi folder ZIP ini**, bukan file ZIP sebagai satu file.

Struktur root repository harus terlihat seperti:
- `.github/`
- `app/`
- `build.gradle`
- `settings.gradle`
- `gradle.properties`

Jangan memakai file proyek lama seperti `main.yml`, `gradlew`, atau file AndroidManifest/MainActivity lama.

## Build
1. Upload isi ZIP ke repository.
2. Commit ke branch `main`.
3. Buka Actions.
4. Pilih `Build EqualizerTube V2 APK`.
5. Tekan `Run workflow`.
6. Jika hijau, buka hasil job dan download `EqualizerTube-V2-debug`.

Workflow memasang JDK 17, Android SDK 35, Gradle 8.9, lalu memeriksa bahwa APK benar-benar ada sebelum artifact diunggah.
