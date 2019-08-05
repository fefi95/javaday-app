# JavaDay App - Calculator

A simple calculator on Android for the talk **Create a testable Android App and don't fail trying: the MVP pattern** on the [JavaDay](https://www.javaday.ec/) Ecuador conference.

The project has two implementations of the same `Activity`: One not using the MVP pattern (`CalculatorActivity`) and another using the patten (`CalculaterActivityMVP`)

## Run the App
### Set up an emulator 

1. Download the android system image. For example, if you want the android-22 image, do:
```bash
sdkmanager "system-images;android-22;default;armeabi-v7a"
```
2. Set up the emulator with the same image of the previous step.

```bash
avdmanager create avd -n <name_of_device> -k "system-images;android-22;default;armeabi-v7a"
```
> To know the complete list of android system images run `sdkmanager --list`

### Build
This project uses `Gradle` for package managing. To build it, use the following command on the root folder of your project:

```bash
./gradlew androidDependencies
```

To build and install the app on a running emulator: 

```bash
emulator -avd <name_of_device> & # Run emulator on the background
./gradlew installDebug 
```

To install on a running emulator and existing APK:

```bash
emulator -avd <name_of_device> &
adb install path/to/your_app.apk
```

## Test
Once you build the project, you can run the tests:

### Unit test

```bash
./gradlew test
```

Note: Keep in mind that this command will also run the linter

### Instrumentation tests
Once you have done an emulator set up, launch it on the background and run the tests:

```bash
emulator -avd <name_of_device> &
./gradlew connectedAndroidTest
```

> Recomendation: Use Android Studio to ease the process of Running and testing the app. 

### Some dependencies

For development:
* [Butterknife](https://jakewharton.github.io/butterknife/)
* [Retrofit](https://square.github.io/retrofit/)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [RxJava](https://github.com/ReactiveX/RxJava)
* Uses [newton's](https://github.com/aunyks/newton-api) API to calculate math results.

For testing:
* [Mockito](https://site.mockito.org/)
* [Mockwebserver](https://github.com/square/okhttp/tree/master/mockwebserver)

See `Gradle` file for more information

