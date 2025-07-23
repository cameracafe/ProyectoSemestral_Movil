package com.example.proyectofinal_movil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnPantalla1, btnPantalla2, btnPantalla3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPantalla1 = findViewById(R.id.btnPantalla1);
        btnPantalla2 = findViewById(R.id.btnPantalla2);
        btnPantalla3 = findViewById(R.id.btnPantalla3);

        btnPantalla1.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SensorLuz.class)));

        btnPantalla2.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SensorPickUp.class)));

        btnPantalla3.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, Pareo_activity.class)));
    }
}
