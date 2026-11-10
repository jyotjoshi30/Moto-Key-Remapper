# Moto Key Remapper

An Android application project written in Kotlin. The app receives an action through its launcher activity and forwards that action to a background service, which can trigger vibration feedback or restore normal ringing mode with a notification sound.

## Features

- **Vibrate action:** triggers a short vibration and sets the phone's ringer mode to vibrate.
- **Ring action:** switches the ringer mode to normal and plays the default notification sound.
- Uses Android's `AudioManager`, `Vibrator`, and `RingtoneManager` APIs.
- Built as a lightweight Android application using Kotlin and Gradle.

## Action strings

The service currently handles these intent actions:

| Action | Behaviour |
|---|---|
| `com.example.motokeyremapper.ACTION_VIBRATE` | Vibrates the device and switches to vibrate mode |
| `com.example.motokeyremapper.ACTION_RING` | Switches to normal ringer mode and plays the default notification sound |

The app expects another component, shortcut, automation tool, or supported button-remapping mechanism to launch the app with one of these actions. The project code in this repository does not itself detect or remap physical hardware buttons.

## Requirements

- Android Studio
- Android SDK Platform 37 (as configured in this project)
- JDK compatible with the installed Android Gradle Plugin
- Android device or emulator running Android 10 (API 29) or newer

## Build and run

1. Clone or download this repository.
2. Open the project root (the folder containing `settings.gradle.kts`) in Android Studio.
3. Allow Gradle sync to complete and install the required Android SDK components if prompted.
4. Build and run the `app` configuration on a compatible Android device.

## Permissions

The manifest declares:

- `MODIFY_AUDIO_SETTINGS` — to change the device ringer mode.
- `VIBRATE` — to trigger vibration feedback.

Behaviour can vary by Android version, device manufacturer, background-service restrictions, and device settings. Test on the target device.

## Project structure

```text
.
├── app/
│   └── src/main/
│       ├── java/com/example/motokeyremapper/
│       │   ├── MainActivity.kt
│       │   ├── FeedbackService.kt
│       │   └── ui/theme/
│       ├── res/
│       └── AndroidManifest.xml
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
└── gradlew.bat
```

## Notes

- `MainActivity` forwards the incoming intent action to `FeedbackService` and then closes.
- `FeedbackService` handles the two action strings listed above.
- Generated build files, local machine settings, and IDE caches are excluded through `.gitignore`.

## License

No license has been selected yet. Unless a license is added, the repository remains under the default copyright restrictions; others do not automatically receive permission to reuse or distribute the code.
