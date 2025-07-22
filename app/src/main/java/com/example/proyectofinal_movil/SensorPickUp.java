package com.example.proyectofinal_movil;
/*prueba de push2*/
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.hardware.SensorManager;
import android.view.WindowManager;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SensorPickUp extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ImageView imageView;
    private LinearLayout layout;  // Para cambiar el color de fondo
    private static final float VERTICAL_THRESHOLD = 9.0f;  // Umbral para detectar el levantamiento del teléfono en el eje Y
    private static final int DISPLAY_TIME = 3000; // Duración de la imagen y fondo (en milisegundos)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el layout y el ImageView
        layout = findViewById(R.id.layout);
        imageView = findViewById(R.id.imageView);
        imageView.setVisibility(ImageView.GONE);  // Imagen oculta al principio

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
                // Cambiar el color de fondo a un color brillante
                layout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));

                // Mostrar la imagen
                imageView.setVisibility(ImageView.VISIBLE);

                // Ejecutar un retraso para mantener la imagen y el fondo por un tiempo antes de restaurar el estado
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Restaurar el color de fondo a blanco y ocultar la imagen después del retraso
                        layout.setBackgroundColor(getResources().getColor(android.R.color.white));
                        imageView.setVisibility(ImageView.GONE);
                    }
                }, DISPLAY_TIME);  // Duración del retraso en milisegundos (3000ms = 3 segundos)
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Este método se puede dejar vacío si no se necesita hacer nada
    }
}

