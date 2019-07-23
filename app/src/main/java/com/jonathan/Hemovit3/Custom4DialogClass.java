package com.jonathan.Hemovit3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class Custom4DialogClass extends Dialog implements View.OnClickListener {
    CircleImageView check46;

    RelativeLayout relativeLayout;

    String unidad;
    String tipo;



    public Custom4DialogClass(@NonNull Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.visita);

        check46 = (CircleImageView) findViewById(R.id.check46);

        relativeLayout = (RelativeLayout) findViewById(R.id.rel);


        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        check46.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        dismiss();

    }
}