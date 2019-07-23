package com.jonathan.Hemovit3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jonathan.Hemovit3.Modelos.Hemo;

public class DialogComent extends Dialog implements View.OnClickListener {

    public String codigo;
    ConstraintLayout relativeLayout;
    EditText coment;
    Button send;
    FirebaseDatabase database;
    public DatabaseReference hemovig;
    public DialogComent(@NonNull Context context, String codigo) {
        super(context);
        this.codigo = codigo;
    }

    @Override
    public void onClick(View view) {

        String comentario = coment.getText().toString();
        String code = codigo;
        Hemo hemo = new Hemo();
        hemo.setCodigo(code);
        hemo.setComentario(comentario);
        hemovig.push().setValue(hemo);
        dismiss();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        database = FirebaseDatabase.getInstance();
        hemovig = database.getReference().child("hemovigilancia");

        setContentView(R.layout.dialogcoment);
        relativeLayout = (ConstraintLayout)findViewById(R.id.diagcom);
        coment = (EditText)findViewById(R.id.coment);
        send = (Button)findViewById(R.id.send);

        send.setOnClickListener(this);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
