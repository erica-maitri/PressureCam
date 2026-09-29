# 📱 PressureCam

> **Measure the pressure. Capture the moment.**

PressureCam is an Android application developed in **Java** that combines **real-time atmospheric pressure monitoring** with **camera functionality** in one simple and user-friendly application.

The application uses the device's **barometer/pressure sensor** to continuously monitor atmospheric pressure and displays the value in **mbar**. It also allows the user to capture an image using the device camera and display the captured image directly inside the application.

---

## ✨ Features

### 🌡️ Atmospheric Pressure Monitoring
- Reads atmospheric pressure using the device's pressure sensor.
- Uses `Sensor.TYPE_PRESSURE`.
- Displays pressure in **mbar**.
- Continuously updates the value whenever a new sensor reading is received.

### 📊 Real-Time Sensor Updates
Pressure monitoring is implemented using:

    SensorManager
          ↓
    SensorEventListener
          ↓
    onSensorChanged()
          ↓
    Update Pressure TextView

The sensor listener is registered in `onResume()` and unregistered in `onPause()`.

### 📷 Camera Capture
The application provides a:

    📷 Capture Image

button.

When pressed, it launches the device camera using:

    MediaStore.ACTION_IMAGE_CAPTURE

After the picture is taken, the captured image is displayed in an `ImageView`.

### 🔍 Camera Availability Check
Before launching the camera, the application checks whether a suitable camera application is available.

If available:

    Camera → Opens

If unavailable:

    Camera is not available on this device.

### 🖼️ Captured Image Display
The captured image appears below the atmospheric pressure reading.

---

# 🏗️ Application Architecture

The application handles two completely independent hardware streams under a single `MainActivity` interface:

```
                  ┌─────────────────────────────────┐
                  │           PressureCam           │
                  │        (MainActivity)           │
                  └─────────────────────────────────┘
                                   │
         ┌─────────────────────────┴─────────────────────────┐
         ▼                                                   ▼
 ┌───────────────┐                                   ┌───────────────┐
 │Pressure Sensor│                                   │ Device Camera │
 └───────┬───────┘                                   └───────┬───────┘
         │                                                   │
         ▼                                                   ▼
 ┌───────────────┐                                   ┌───────────────┐
 │ SensorManager │                                   │ MediaStore    │
 └───────┬───────┘                                   │ ACTION_IMAGE_ │
         │                                           │ CAPTURE Intent│
         ▼                                           └───────┬───────┘
 ┌───────────────┐                                           │
 │SensorEvent    │                                           ▼
 │Listener       │                                   ┌───────────────┐
 └───────┬───────┘                                   │  Camera App   │
         │                                           └───────┬───────┘
         ▼                                                   │
 ┌───────────────┐                                           ▼
 │onSensorChanged│                                   ┌───────────────┐
 └───────┬───────┘                                   │onActivityResult│
         │                                           └───────┬───────┘
         ▼                                                   │
 ┌───────────────┐                                           ▼
 │  txtPressure  │                                   ┌───────────────┐
 │  (TextView)   │                                   │   imageView   │
 └───────────────┘                                   │  (ImageView)  │
                                                     └───────────────┘
```

---

# 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application development |
| Android SDK | Android application framework |
| XML | User interface |
| `SensorManager` | Sensor management |
| `SensorEventListener` | Receiving sensor readings |
| `Sensor.TYPE_PRESSURE` | Atmospheric pressure |
| `MediaStore.ACTION_IMAGE_CAPTURE` | Launching camera |
| `ImageView` | Displaying captured image |
| AppCompat | Activity and theme compatibility |

---

# 📂 Project Structure

    PressureCam/
    │
    ├── app/
    │   └── src/main/
    │       │
    │       ├── java/
    │       │   └── com/example/pressurecam/
    │       │       └── MainActivity.java
    │       │
    │       ├── res/
    │       │   │
    │       │   ├── layout/
    │       │   │   └── activity_main.xml
    │       │   │
    │       │   ├── values/
    │       │   │   ├── colors.xml
    │       │   │   └── themes.xml
    │       │   │
    │       │   └── values-night/
    │       │       └── themes.xml
    │       │
    │       └── AndroidManifest.xml
    │
    └── README.md

---

# 🧠 How Pressure Monitoring Works

Android provides atmospheric pressure through:

    Sensor.TYPE_PRESSURE

The application obtains the pressure sensor:

    pressureSensor =
        sensorManager.getDefaultSensor(
            Sensor.TYPE_PRESSURE
        );

The sensor is then registered:

    sensorManager.registerListener(
        this,
        pressureSensor,
        SensorManager.SENSOR_DELAY_NORMAL
    );

Whenever a new pressure reading arrives, Android calls:

    onSensorChanged()

The new value is displayed on the screen.

---

# 📐 Pressure Unit

Android's `TYPE_PRESSURE` sensor reports pressure in **hPa (hectopascals)**.

For this application:

    1 hPa = 1 mbar

Therefore:

    1013.25 hPa
          ↓
    1013.25 mbar

No numerical conversion is required.

---

# 📷 How Camera Capture Works

The camera flow is:

    Capture Image
          ↓
    Create Camera Intent
          ↓
    ACTION_IMAGE_CAPTURE
          ↓
    Check Camera Availability
          ↓
    Open Camera
          ↓
    User Takes Picture
          ↓
    Bitmap Returned
          ↓
    ImageView

The application creates the camera Intent using:

    Intent cameraIntent =
        new Intent(
            MediaStore.ACTION_IMAGE_CAPTURE
        );

Camera availability is checked using:

    cameraIntent.resolveActivity(
        getPackageManager()
    );

---

# 🎨 User Interface

The application follows a simple dashboard-style design:

    PressureCam

    ATMOSPHERIC PRESSURE

          1013.25 mbar

    ✓ Pressure sensor is active


        📷 Capture Image


    ┌─────────────────────────┐
    │                         │
    │     Captured Image      │
    │                         │
    └─────────────────────────┘

    Captured image will appear here

---

# 🎨 Theme Configuration

Since `MainActivity` extends:

    AppCompatActivity

the application uses AppCompat-compatible themes.

### Light Theme

    Theme.AppCompat.Light.NoActionBar

### Night Theme

    Theme.AppCompat.DayNight.NoActionBar

This ensures compatibility with `AppCompatActivity`.

---

# 📱 Sensor Compatibility

The pressure feature requires a device with a **barometer/pressure sensor**.

Not every Android device contains one.

If the pressure sensor is unavailable, the application displays:

    Barometer sensor is not available.

The camera feature depends on whether the device has an available camera application.

---

# 🚀 How to Run

### 1. Clone the repository

    git clone <your-repository-url>

### 2. Open the project

Open the `PressureCam` project using **Android Studio**.

### 3. Sync Gradle

Allow Android Studio to complete Gradle synchronization.

### 4. Run the application

Connect an Android device or start an emulator and press:

    ▶ Run

---

# 🧪 Testing

## Test 1 — Pressure Sensor

Open PressureCam.

A pressure value should appear, for example:

    1013.25 mbar

If the device supports a pressure sensor, the value will continuously update.

---

## Test 2 — Camera

Press:

    📷 Capture Image

Take a picture.

After returning to the application, the captured image should appear in the `ImageView`.

---

## Test 3 — No Camera

On a device/emulator without an available camera application, pressing the button should display:

    Camera is not available on this device.

---

# 🔄 Activity Lifecycle

Pressure sensor monitoring follows the Android Activity lifecycle:

    onResume()
        ↓
    Register Sensor Listener
        ↓
    Receive Readings
        ↓
    Update UI
        ↓
    onPause()
        ↓
    Unregister Sensor Listener

This prevents unnecessary sensor monitoring when the Activity is not visible.

---

# 🎓 Learning Outcomes

This project demonstrates:

- Android Activity development
- XML-based UI design
- Java programming
- Android Sensor Framework
- `SensorManager`
- `SensorEventListener`
- `Sensor.TYPE_PRESSURE`
- Real-time sensor updates
- Android Activity lifecycle
- Camera Intent
- `MediaStore.ACTION_IMAGE_CAPTURE`
- Camera availability checking
- Bitmap handling
- `ImageView`
- AppCompat themes

---

# 🔮 Future Improvements

Possible future enhancements:

- 📈 Pressure history graph
- 💾 Save pressure readings
- 🕒 Timestamp every pressure reading
- 📷 Save full-resolution photographs
- 🌡️ Combine pressure with temperature
- 📊 Display minimum, maximum and average pressure
- ☁️ Store readings in the cloud
- 🗺️ Add location information to captured images
- 🌙 Improve dark-mode interface
- 📤 Export sensor data as CSV/PDF

---

# ⚠️ Important Notes

- Atmospheric pressure sensor availability depends on the physical device.
- Emulator sensor support depends on the emulator configuration.
- Camera availability depends on the device/emulator.
- Pressure values may vary between different devices.
- The current camera implementation receives the returned thumbnail `Bitmap`, keeping the educational implementation simple.

---

# 📌 Project Information

| Property | Details |
|---|---|
| Project Name | PressureCam |
| Platform | Android |
| Language | Java |
| UI | XML |
| IDE | Android Studio |
| Minimum SDK | 24 |
| Sensor | Barometer / Pressure Sensor |
| Camera API | `MediaStore.ACTION_IMAGE_CAPTURE` |
| Architecture | Single Activity |
| Theme | AppCompat |

---

# ⭐ Project Summary

**PressureCam** is a compact Android application that demonstrates how smartphone hardware can be used to build a practical sensor-and-camera application.

It continuously monitors **atmospheric pressure**, displays it in **mbar**, checks **camera availability**, launches the device camera, and displays the **captured image** directly below the pressure reading.

> **Measure the pressure. Capture the moment.** 📊📷

---

## 📄 License

This project is created for **educational and academic purposes**.
