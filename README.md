# EqualizerTube V2 — READY TO BUILD

Ini adalah struktur proyek Android yang sudah diperbaiki.

## Struktur root
- `.github/workflows/build.yml`
- `app/`
- `build.gradle`
- `settings.gradle`
- `gradle.properties`

## Build GitHub Actions
1. Upload **isi ZIP ini**, bukan file ZIP-nya, ke root repository.
2. Pastikan folder `app/` terlihat di root.
3. Buka **Actions**.
4. Pilih **Build APK**.
5. Jalankan **Run workflow**.
6. APK berada di **Artifacts → EqualizerTubeV2-debug**.

Proyek sengaja tidak memakai library pihak ketiga agar proses build lebih sederhana dan stabil.
