package com.jonathan.Hemovit3;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomDialogClass extends Dialog implements View.OnClickListener {
    CircleImageView check;
    EditText cantidad;
    RelativeLayout relativeLayout;
    TextView tipo;
    String unidad;


    public CustomDialogClass(@NonNull Context context, String unidad) {
        super(context);
        this.unidad = unidad;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogblood1);

        check = (CircleImageView)findViewById(R.id.check);
        cantidad = (EditText) findViewById(R.id.comentario);
        relativeLayout = (RelativeLayout)findViewById(R.id.rel);
        tipo = (TextView)findViewById(R.id.tipo);
        tipo.setText(unidad);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        check.setOnClickListener(this);
        unidad = cantidad.getText().toString();

    }

    @Override
    public void onClick(View view) {
        if (cantidad.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "COLOCA LA CANTIDAD ", Toast.LENGTH_LONG).show();
        }
        else {
            int cantid = Integer.parseInt(cantidad.getText().toString());
            Custom2DialogClass customDialogClass = new Custom2DialogClass(getContext(),cantidad.getText().toString(), tipo.getText().toString());
            customDialogClass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dismiss();
            customDialogClass.show();

        }
    }
}
