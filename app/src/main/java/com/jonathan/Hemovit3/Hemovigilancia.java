package com.jonathan.Hemovit3;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Hemovigilancia extends AppCompatActivity {
    float x1 , x2, y1 , y2;
    ImageButton scan_button, good, regular, bad;
    TextView scan_format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hemovigilancia);
        scan_button = (ImageButton)findViewById(R.id.scan_button);
        scan_format = (TextView)findViewById(R.id.scan_format);
        good = (ImageButton)findViewById(R.id.good);
        regular = (ImageButton)findViewById(R.id.regular);
        bad = (ImageButton)findViewById(R.id.bad);

        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogComent customDialogClass = new DialogComent(Hemovigilancia.this,scan_format.getText().toString());
                customDialogClass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                if(!scan_format.getText().toString().isEmpty() && !scan_format.getText().toString().equals("CODIGO DE UNIDAD"))
                {
                    customDialogClass.show();

                }
                else {
                    Toast.makeText(Hemovigilancia.this,"Escanea el codigo de la unidad",Toast.LENGTH_SHORT).show();
                }

            }
        });

        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogComent customDialogClass = new DialogComent(Hemovigilancia.this,scan_format.getText().toString());
                customDialogClass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                if(!scan_format.getText().toString().isEmpty() && !scan_format.getText().toString().equals("CODIGO DE UNIDAD"))
                {
                    customDialogClass.show();
                }
                else {
                    Toast.makeText(Hemovigilancia.this,"Escanea el codigo de la unidad",Toast.LENGTH_SHORT).show();
                }
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Custom4DialogClass customDialogClass = new Custom4DialogClass(Hemovigilancia.this);
                customDialogClass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialogClass.show();
            }
        });

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQRScanner();
            }
        });

    }
    private void startQRScanner() {
        new IntentIntegrator(this).initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result =   IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this,    "Cancelled",Toast.LENGTH_LONG).show();
            } else {

                updateText(result.getContents());                }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void updateText(String scanCode) {
        scan_format.setText(scanCode);
    }

}