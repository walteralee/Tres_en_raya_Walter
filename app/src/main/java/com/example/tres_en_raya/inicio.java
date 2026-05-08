package com.example.tres_en_raya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class inicio extends AppCompatActivity {

    private Button dos_jugadores;
    private Button contra_maquina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dos_jugadores = findViewById(R.id.btt_dos_jugadores);
        contra_maquina = findViewById(R.id.btt_contra_maquina);

        dos_jugadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inicio.this, contra_amigo.class);
                startActivity(intent);
            }
        });

        contra_maquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(inicio.this, contra_maquina.class);
               startActivity(intent);
            }
        });

    }
}