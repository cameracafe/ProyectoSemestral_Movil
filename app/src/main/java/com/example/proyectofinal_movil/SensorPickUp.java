package com.example.proyectofinal_movil;


import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.view.WindowManager;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SensorPickUp extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ImageView imageView, explanationImage;
    private LinearLayout layout;  // Para cambiar el color de fondo
    private TextView title, description;  // Para el título y la descripción
    private static final float VERTICAL_THRESHOLD = 9.0f;  // Umbral para detectar el levantamiento del teléfono en el eje Y
    private static final int DISPLAY_TIME = 2500; // Duración de la imagen y fondo (en milisegundos)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el layout, ImageViews y TextViews
        layout = findViewById(R.id.layout);
        imageView = findViewById(R.id.imageView);
        explanationImage = findViewById(R.id.explanationImage);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);

        imageView.setVisibility(ImageView.GONE);  // Imagen oculta al principio
        explanationImage.setVisibility(ImageView.VISIBLE);  // Imagen explicativa visible al principio

        // Obtener el SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Obtener el Sensor Acelerómetro
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Mantener la pantalla encendida mientras la app esté activa
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar el sensor
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Detener el sensor cuando la actividad está pausada
        if (accelerometer != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Medir la aceleración en los ejes X, Y y Z
            float x = event.values[0];
            float y = event.values[1];  // Aceleración en el eje Y (vertical)
            float z = event.values[2];

            // Si el valor de Y es mayor que el umbral, consideramos que el teléfono está en posición vertical
            if (y > VERTICAL_THRESHOLD) {
                // Cambiar el fondo a una imagen de fondo diferente
                layout.setBackgroundResource(R.drawable.background1);

                // Ocultar la imagen explicativa
                explanationImage.setVisibility(ImageView.GONE);

                // Mostrar la imagen de cuando el teléfono se levanta
                imageView.setVisibility(ImageView.VISIBLE);

                // Ejecutar un retraso para mantener la imagen y el fondo por un tiempo antes de restaurar el estado
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Restaurar el color de fondo a blanco y ocultar la imagen después del retraso
                        layout.setBackgroundResource(R.drawable.background2);  // Fondo normal
                        imageView.setVisibility(ImageView.GONE);
                        explanationImage.setVisibility(ImageView.VISIBLE);  // Imagen explicativa visible
                    }
                }, DISPLAY_TIME);  // Duración del retraso en milisegundos (3000ms = 3 segundos)
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
