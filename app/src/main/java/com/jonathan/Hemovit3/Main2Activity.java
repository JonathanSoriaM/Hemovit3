package com.jonathan.Hemovit3;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Main2Activity extends AppCompatActivity {
    float x1 , x2, y1 , y2;
    RelativeLayout hemov,relativeLayout3,cont,relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        hemov = (RelativeLayout)findViewById(R.id.hemov);
        relativeLayout3 = (RelativeLayout)findViewById(R.id.relativeLayout3);
        cont = (RelativeLayout)findViewById(R.id.cont);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this,Inventario.class));
            }
        });
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this,Contacto.class));
            }
        });
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this,Buzon.class));
            }
        });
        hemov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this,Hemovigilancia.class));
            }
        });

    }


}