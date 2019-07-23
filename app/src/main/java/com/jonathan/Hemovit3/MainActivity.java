package com.jonathan.Hemovit3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    float x1 , x2, y1 , y2;
    public ImageView login;
    public TextView registro;
    EditText email,password;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    public DatabaseReference pendientes;
    //aqui se coloco algo para probar
    ImageButton listo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        pendientes = database.getReference().child("pendientes");
        login = (ImageView)findViewById(R.id.login);
        registro = (TextView)findViewById(R.id.registro);
        email = (EditText)findViewById(R.id.whats);
        password = (EditText)findViewById(R.id.contraseña);


        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Registro.class);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()  ){
                    Toast.makeText(MainActivity.this,"completa todos los campos ",Toast.LENGTH_LONG).show();
                }
                else {
                    if (password.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "completa todos los campos ", Toast.LENGTH_LONG).show();
                    } else {
                        String mail = email.getText().toString();
                        String pass = password.getText().toString();
                        mAuth.signInWithEmailAndPassword(mail, pass)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            //Log.d(TAG, "signInWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Log.v("USUARIO", user.getEmail());
                                            final String email = user.getEmail();
                                            pendientes.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.hasChildren()) {
                                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                            Usuario usuario = dataSnapshot1.getValue(Usuario.class);
                                                            if (usuario.acceso.equals("no")) {
                                                                /*Intent intent = new Intent(MainActivity.this, Registro.class);
                                                                startActivity(intent);*/
                                                            } else {

                                                                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                });
                    }
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if(mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }
        else{

        }

    }




}