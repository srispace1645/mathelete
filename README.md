# Mathelete: Grade 4 Fall math contest practice

An Android app that helps kids practice for the Noetic Learning Math Contest
(Grade 4, Fall 2026, contest window Nov 12–25). It runs on Android phones,
tablets and Fire TV. It works fully offline, has no ads or accounts, and keeps
progress on the device only.

## What's inside
- **10 practice tests** (200 original questions). Each has 20 questions, a
  45-minute timer and no feedback until the end, like the real contest. You
  get a score out of 100, a topic breakdown and a solution for every question.
- **Shuffle Test**: a new 20-question mix from all the tests.
- **Practice by Topic**: one question at a time, with a hint and an instant solution.
- **Quick Drill**: endless auto-made warm-up questions with a streak counter.
- **Review Mistakes**: missed questions come back until they're answered right twice in a row.
- **My Progress**: test history and how you're doing in each topic.

Every question is **tap-and-pick**: big A/B/C/D buttons, no typing and no
keyboard. The wrong choices are common mistakes, not random numbers. Everything
works with a TV remote: arrow keys to move, the center button to select, Back
to go back.

## Get the app file (APK)
Download the newest version here. No GitHub account needed:

**https://github.com/srispace1645/mathelete/releases/latest/download/mathelete.apk**

Every version is listed on the [Releases page](https://github.com/srispace1645/mathelete/releases).
The app needs Android 5.1 or newer.

Had the older "Mathlete Prep" test app? Mathelete installs as a separate app,
so uninstall Mathlete Prep to avoid two icons. Its progress doesn't carry over.

## Install on an Android phone or tablet
Open the link above on the phone, then open the downloaded `mathelete.apk`.
Android will ask you to allow installs from that app (Chrome or Files). Allow
it, then tap **Install**. Newer versions install right over the old one and
keep your progress.

## Install on a Fire TV from a Windows laptop
This works on Android-based Fire TVs (Fire TV Stick, Lite, 4K, 4K Max, Cube and
Fire TV smart TVs). The 2025–26 Fire TV Stick 4K Select and Stick HD run Vega OS
and can't install outside apps.

The laptop and the Fire TV must be on the same Wi-Fi.

**On the Fire TV (one time only)**
1. Go to **Settings → My Fire TV → About**. Select the device name (for example "Fire TV Stick Lite") **7 times** until it says you're a developer.
2. Press Back. Open **Developer Options** and turn on **ADB debugging**. If you see **Apps from Unknown Sources**, turn that on too.
3. Go to **Settings → My Fire TV → About → Network** and write down the **IP address** (it looks like `192.168.1.23`).

**On the laptop**
1. Download **SDK Platform-Tools for Windows** from Google:
   https://developer.android.com/tools/releases/platform-tools
2. Unzip it. You'll get a folder named `platform-tools`. Move it to `C:\platform-tools`.
3. Download `mathelete.apk` (link above) and copy it into `C:\platform-tools`.
4. Open the Start menu, type `cmd` and open **Command Prompt**. Type these, pressing Enter after each one (use your TV's IP address):
   ```
   cd C:\platform-tools
   adb connect 192.168.1.23:5555
   ```
5. The TV will ask **"Allow USB debugging?"**. Tick **Always allow from this computer** and choose **OK**.
   (If the laptop says "failed to authenticate", run the `adb connect` line again.)
6. Install the app:
   ```
   adb install -r mathelete.apk
   ```
   Wait for the word `Success`.
7. On the TV, open **Apps** (the three-squares icon) and choose **Mathelete**. You can move it to the front row with the remote's Menu button.

To update later, download the newest APK and repeat steps 3, 4 and 6.

## Printable tests
Each practice test can also be printed, laid out like the paper contest:
a cover page, the 20 questions with answer lines and room to work, and an
answer key with solutions on separate pages at the end.
```
pip install reportlab
python tools/make_paper.py 1      # writes build/Mathelete_Practice_Test_1.pdf
```
Change `1` to any number from 1 to 10.

## For developers
- `core/` is plain Kotlin: question model, answer checker, test grading,
  drills, progress. Run `./gradlew :core:test` with any JDK 17+ (no Android SDK needed).
- `core/src/main/resources/banks/` holds the question bank as JSON. Add a new
  grade or season by adding a folder and listing it in `manifest.json`.
- Every bank answer is re-solved independently in
  `core/src/test/kotlin/com/mathelete/core/verify/`.
- `ChoiceMaker` (`core/.../Choices.kt`) builds the tap-and-pick options:
  hand-written traps from each question's `choices` first, then near-misses in
  the same format as the answer. `ChoicesTest` checks every question has exactly
  one right option.
- `app/` is the Jetpack Compose UI. It's only included in the build when an
  Android SDK is installed (`ANDROID_HOME` or `local.properties`).
- `app/src/test/` has Robolectric tests that drive the app with remote-control
  keys and take TV and phone screenshots. CI publishes the screenshots to the
  `ci-screenshots` branch.
- `tools/make_art.py` redraws the launcher icons and the Fire TV banner.
- `docs/STYLE_NOTES.md` sums up the contest's style. The source papers aren't
  in this repo, and `reference/` is git-ignored so they can't be added by mistake.

## Publishing a new version
Every push to `main` builds the app. To put out a new version:
1. In `app/build.gradle.kts`, raise `versionCode` by 1 and set a new `versionName` (for example `1.1`).
2. Push to `main`. CI builds `mathelete.apk`, signs it with the owner's key and
   creates release `v1.1` on the Releases page. A version that's already
   released is skipped.

Signing uses 4 repository secrets (**Settings → Secrets and variables → Actions**):
`MATHELETE_KEYSTORE` (the keystore file as base64 text), `MATHELETE_KEYSTORE_PASSWORD`,
`MATHELETE_KEY_ALIAS` and `MATHELETE_KEY_PASSWORD`. Without them, CI still builds
and tests the app but doesn't publish. Always sign with the same key, or phones
won't accept the update.
