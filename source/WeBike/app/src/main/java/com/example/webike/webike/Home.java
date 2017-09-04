package com.example.webike.webike;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {
    ImageButton ruta;
    ImageButton panic;
    ImageButton help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ruta = (ImageButton)findViewById(R.id.getroute_button);
        ruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(getBaseContext(),CalcularRuta.class);
                startActivity(r);
            }
        });

        panic = (ImageButton)findViewById(R.id.panic_button);
        panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getBaseContext(),Panic.class);
                startActivity(p);
            }
        });

        help = (ImageButton)findViewById(R.id.help_button);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h = new Intent(getBaseContext(),Help.class);
                startActivity(h);
            }
        });
    }
}
