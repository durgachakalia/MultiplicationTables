# Chakalia Tables – Android Learning App

A simple, colourful multiplication-table learning app for students. Built with native Android Java and standard Android views; no third-party libraries are required.

## Features
- Multiplication tables from 1 to 20, with each table running from ×1 through ×20.
- Tap any table card to open its table.
- Tap any equation (×1 through ×20) to hear it spoken using Android Text-to-Speech.
- Read Full Table and Stop controls.
- Custom launcher icon.
- Offline-friendly; speech uses the device's installed Android TTS engine.

## Open and run
1. Install **Android Studio**.
2. Open this folder (`MultiplicationTables`) in Android Studio.
3. Allow Gradle sync. Use JDK 17 (Android Studio's bundled JDK is recommended).
4. Connect an Android phone with USB debugging enabled, or create an emulator.
5. Press **Run**.

The project uses Android Gradle Plugin 8.6.1, Gradle 8.7, compile SDK 35, and minimum Android 6.0 (API 23).

## Build an APK
In Android Studio choose **Build → Build Bundle(s) / APK(s) → Build APK(s)**.
The debug APK is normally created at:
`app/build/outputs/apk/debug/app-debug.apk`

You can also build from a terminal with a compatible Gradle 8.7 installation:
```bash
gradle assembleDebug
```

## Upload to GitHub
Create a new empty GitHub repository, then from this folder run:
```bash
git init
git add .
git commit -m "Initial release: Tables 1 to 20 Android app"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/YOUR-REPOSITORY.git
git push -u origin main
```

## Notes
- Speech language is set to English (India), with English (US) as a fallback.
- If speech is silent, check that a Text-to-Speech engine is installed and enabled in Android Settings → Accessibility / Text-to-speech output. The exact menu name varies by phone.
- Change `versionCode` and `versionName` in `app/build.gradle` for future releases.


## Spoken multiplication style
Individual equations and Read Full Table use school-style recitation, for example: “Two ones are two”, “Two twos are four”, and “Five tens are fifty”. The table of 1 uses natural singular phrasing such as “One times two is two”.


## Version 1.1 – Recitation voice fix
All tapped equations and full-table playback now use traditional spoken recitation, such as “Two twos are four” and “Five tens are fifty”. App versionCode is 2 so Android can recognize it as an update.


## Version 1.2 update
- App display name: Chakalia Tables.
- Tables 1–20, each from ×1 through ×20.
- Recitation now pluralizes the multiplier consistently: “One twos are two”, “Two ones are two”, “Two threes are six”.
- Full-table reading and individual equation reading share the same speech function.
- Updated launcher icon assets for adaptive and legacy Android launchers.
