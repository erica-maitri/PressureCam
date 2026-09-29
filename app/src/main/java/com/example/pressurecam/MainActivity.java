package com.example.pressurecam;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor pressureSensor;

    private TextView txtPressure;
    private TextView txtStatus;
    private ImageView imageView;
    private Button btnCapture;

    private static final int CAMERA_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        txtPressure = findViewById(R.id.txtPressure);
        txtStatus = findViewById(R.id.txtStatus);
        imageView = findViewById(R.id.imageView);
        btnCapture = findViewById(R.id.btnCapture);

        // Get SensorManager
        sensorManager =
                (SensorManager) getSystemService(
                        SENSOR_SERVICE
                );

        // Get pressure sensor
        pressureSensor =
                sensorManager.getDefaultSensor(
                        Sensor.TYPE_PRESSURE
                );

        // Check pressure sensor
        if (pressureSensor == null) {

            txtPressure.setText("-- mbar");

            txtStatus.setText(
                    "Barometer sensor is not available."
            );

        } else {

            txtStatus.setText(
                    "✓ Pressure sensor is active"
            );
        }

        // Camera button
        btnCapture.setOnClickListener(v ->
                openCamera()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Start continuous pressure monitoring
        if (pressureSensor != null) {

            sensorManager.registerListener(
                    this,
                    pressureSensor,
                    SensorManager.SENSOR_DELAY_NORMAL
            );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Stop sensor when activity is paused
        sensorManager.unregisterListener(this);
    }

    // Called whenever a new pressure reading is received
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType()
                == Sensor.TYPE_PRESSURE) {

            float pressure = event.values[0];

            // hPa and mbar have the same numerical value
            txtPressure.setText(
                    String.format(
                            "%.2f mbar",
                            pressure
                    )
            );
        }
    }

    @Override
    public void onAccuracyChanged(
            Sensor sensor,
            int accuracy) {
    }

    private void openCamera() {

        // Check whether camera is available
        Intent cameraIntent =
                new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE
                );

        if (cameraIntent.resolveActivity(
                getPackageManager()) != null) {

            startActivityForResult(
                    cameraIntent,
                    CAMERA_REQUEST
            );

        } else {

            txtStatus.setText(
                    "Camera is not available on this device."
            );
        }
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (requestCode == CAMERA_REQUEST &&
                resultCode == RESULT_OK &&
                data != null) {

            Bitmap image =
                    (Bitmap) data.getExtras()
                            .get("data");

            if (image != null) {

                imageView.setImageBitmap(image);

                txtStatus.setText(
                        "✓ Image captured successfully"
                );
            }
        }
    }
}