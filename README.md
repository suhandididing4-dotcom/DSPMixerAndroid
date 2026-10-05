# EqualizerTube V2 — DAW Mixer

Paket proyek Android siap di-upload ke GitHub.

## Fitur V2
- Tampilan landscape mixer
- Channel MUSIC, MIC, AUX, FX
- Gain/fader channel
- MUTE / SOLO UI
- EQ LOW / MID / HIGH per channel
- Master fader dan meter UI
- Pilih file audio dari HP
- Play / Pause / Stop
- GitHub Actions otomatis membuat APK debug

## Cara build di GitHub
1. Upload seluruh isi folder ini ke repository.
2. Buka **Actions**.
3. Pilih **Build EqualizerTube V2 APK**.
4. Tekan **Run workflow**.
5. Setelah selesai, buka hasil workflow dan download artifact **EqualizerTube-V2-debug**.

Catatan: versi ini adalah fondasi UI mixer dan pemutar audio. EQ/fader belum merupakan DSP multi-channel real-time penuh; tahap DSP real-time dapat ditambahkan setelah APK dasar berhasil dibuild.
