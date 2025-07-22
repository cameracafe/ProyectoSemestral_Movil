package com.example.proyectofinal_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SensorLuz extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private RelativeLayout backgroundLayout;
    private TextView luxTextView;
    private ImageView iconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_luz);

        backgroundLayout = findViewById(R.id.backgroundLayout);
        luxTextView = findViewById(R.id.luxTextView);
        iconImageView = findViewById(R.id.iconImageView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        if (lightSensor == null) {
            luxTextView.setText("Sensor de luz no disponible en este dispositivo.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lux = event.values[0];
        luxTextView.setText("Nivel de luz: " + lux + " lux");

        if (lux < 50) {
            // Noche
            backgroundLayout.setBackgroundColor(Color.parseColor("#121212"));
            iconImageView.setImageResource(R.drawable.moon_icon);
            luxTextView.setTextColor(Color.WHITE);
        } else if (lux < 500) {
            // Atardecer
            backgroundLayout.setBackgroundColor(Color.parseColor("#FF8C42"));
            iconImageView.setImageResource(R.drawable.sunset_icon);
            luxTextView.setTextColor(Color.BLACK);
        } else {
            // Día
            backgroundLayout.setBackgroundColor(Color.parseColor("#B3E5FC"));
            iconImageView.setImageResource(R.drawable.sun_icon);
            luxTextView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se utiliza
    }
}
