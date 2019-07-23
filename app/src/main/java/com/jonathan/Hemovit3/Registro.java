package com.jonathan.Hemovit3;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    EditText nombre;
    EditText correo;
    EditText contraseña;
    EditText clinica;
    EditText cedula;
    FloatingActionButton btnRegistrar;
    FirebaseAuth firebaseAuth;

    private DatabaseReference mDatabase;
    public void RealizarPost(final String email, final String password, final String user, final String hospital, final String fcedula) {
        String url = "http://ec2-52-15-254-137.us-east-2.compute.amazonaws.com:3000/recibir";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   ((TextView)findViewById(R.id.TextResult)).setText(response);
                        Log.v("Respuesta",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("usuario", user);
                params.put("password", password);
                params.put("cedula", fcedula);
                params.put("email", email);
                params.put("clinica", hospital);
                params.put("acceso","no");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }


    /*private void writeNewUser(String nombre, String correo, String contraseña ,String clinica, String cedula,String acceso) {
        //nombre,correo,clinica,contraseña,cedula,acceso
        Usuario user = new Usuario();
        user.setNombre(nombre);
        user.setCorreo(correo);
        user.setClinica(clinica);
        user.setClinica(clinica);
        mDatabase.child("pendientes").push().setValue(user);
        Intent intent = new Intent(Registro.this, MainActivity.class);
        Registro.this.startActivity(intent);
        Registro.this.finish();
    }*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        nombre = (EditText)findViewById(R.id.nombre);
        correo = (EditText)findViewById(R.id.whats);
        contraseña = (EditText)findViewById(R.id.contraseña);
        clinica = (EditText)findViewById(R.id.clinica);
        cedula = (EditText)findViewById(R.id.cedula);
        btnRegistrar = (FloatingActionButton)findViewById(R.id.registrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombre.getText().toString().isEmpty())
                {
                    Toast.makeText(Registro.this, "completa todos los campos ", Toast.LENGTH_LONG).show();
                }
                else {
                    if (correo.getText().toString().isEmpty()) {
                        Toast.makeText(Registro.this, "completa todos los campos ", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (correo.getText().toString().isEmpty()) {
                            Toast.makeText(Registro.this, "completa todos los campos ", Toast.LENGTH_LONG).show();
                        }
                        else {
                            if (contraseña.getText().toString().isEmpty()) {
                                Toast.makeText(Registro.this, "completa todos los campos ", Toast.LENGTH_LONG).show();
                            } else {
                                if (clinica.getText().toString().isEmpty()) {
                                    Toast.makeText(Registro.this, "completa todos los campos ", Toast.LENGTH_LONG).show();
                                } else {
                                    if (cedula.getText().toString().isEmpty()) {
                                        Toast.makeText(Registro.this, "completa todos los campos ", Toast.LENGTH_LONG).show();
                                    } else {
                                        final String email = correo.getText().toString().trim();
                                        final String password = contraseña.getText().toString().trim();
                                        final String user = nombre.getText().toString();
                                        final String hospital = clinica.getText().toString();
                                        final String fcedula = cedula.getText().toString();



                                        // aqui se toma el correo y contraseña para poder crear la cuenta en firebase
                                        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override

                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                //Aquí se hace lo que quieras
                                                if (task.isSuccessful()) {

                                                    //writeNewUser(nombre.getText().toString(),correo.getText().toString(),contraseña.getText().toString(),clinica.getText().toString(),cedula.getText().toString(),"no");
                                                    RealizarPost(email, password, user, hospital, fcedula);
                                                    Intent intent = new Intent(Registro.this, MainActivity.class);
                                                    startActivity(intent);
                                                    Log.v("datos",fcedula);
                                                } else {
                                                    Toast.makeText(Registro.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            }

        });

    }

}