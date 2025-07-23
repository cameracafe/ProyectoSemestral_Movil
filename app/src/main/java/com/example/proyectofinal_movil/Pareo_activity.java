package com.example.proyectofinal_movil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pareo_activity extends AppCompatActivity {

    ImageView sol_img, luna_img, nube_img;
    ImageView amarillo_img, gris_img, celeste_img;
    ImageView cuerda_sol, cuerda_luna, cuerda_nube;
    TextView celebracion_tv;
    Button volverInicio_btn;

    ImageView seleccionActual = null;
    int aciertos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pareo);

        sol_img = findViewById(R.id.sol_img);
        luna_img = findViewById(R.id.luna_img);
        nube_img = findViewById(R.id.nube_img);

        amarillo_img = findViewById(R.id.amarillo_img);
        gris_img = findViewById(R.id.gris_img);
        celeste_img = findViewById(R.id.celeste_img);

        cuerda_sol = findViewById(R.id.cuerda_sol);
        cuerda_luna = findViewById(R.id.cuerda_luna);
        cuerda_nube = findViewById(R.id.cuerda_nube);

        celebracion_tv = findViewById(R.id.celebración_tv);
        volverInicio_btn = findViewById(R.id.volverinicio_btn);

        sol_img.setOnClickListener(v -> seleccionActual = sol_img);
        luna_img.setOnClickListener(v -> seleccionActual = luna_img);
        nube_img.setOnClickListener(v -> seleccionActual = nube_img);

        amarillo_img.setOnClickListener(v -> verificarMatch(sol_img, R.id.cuerda_sol));
        gris_img.setOnClickListener(v -> verificarMatch(luna_img, R.id.cuerda_luna));
        celeste_img.setOnClickListener(v -> verificarMatch(nube_img, R.id.cuerda_nube));

        volverInicio_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Pareo_activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void verificarMatch(ImageView objetoEsperado, int idCuerda) {
        if (seleccionActual == null) {
            Toast.makeText(this, "Selecciona primero un objeto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (seleccionActual == objetoEsperado) {
            ImageView cuerda = findViewById(idCuerda);
            if (cuerda.getVisibility() != View.VISIBLE) {
                // Animación de la cuerda (fade-in)
                Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
                cuerda.startAnimation(fadeIn);
                cuerda.setVisibility(View.VISIBLE);

                // Animación lateral para el objeto seleccionado (shake)
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                seleccionActual.startAnimation(shake);

                aciertos++;

                if (aciertos == 3) {
                    celebracion_tv.setText("¡FELICIDADES!");
                    Animation bounce = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
                    celebracion_tv.startAnimation(bounce);
                }
            }
        } else {
            Toast.makeText(this, "¡No es el color correcto!", Toast.LENGTH_SHORT).show();
        }

        seleccionActual = null;
    }
}
