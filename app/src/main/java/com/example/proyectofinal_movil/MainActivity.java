package com.example.proyectofinal_movil;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAbrirSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Nuevo layout simple

        btnAbrirSensor = findViewById(R.id.btnAbrirSensor);

        btnAbrirSensor.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SensorLuz.class);
            startActivity(intent);
        });
    }
}
