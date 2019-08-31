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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    float x1 , x2, y1 , y2;
    public Api api;
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

                                            Gson gson = new GsonBuilder()
                                                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                                    .create();

                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl("https://91527622.ngrok.io/")
                                                    .addConverterFactory(GsonConverterFactory.create(gson))
                                                    .build();

                                            api = retrofit.create(Api.class);

                                            Map<String,String> com8 = new HashMap<>();
                                            com8.put("correo",email);





                                            Map<String,Object> obj45 = new HashMap<>();
                                            obj45.put("query",com8);

                                            Call<ResponseBody> newComent = api.verificarUsuario(obj45);
                                            newComent.enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    switch (response.code())
                                                    {
                                                        case 200:
                                                            Toast.makeText(getApplicationContext(),"Tiene Acceso",Toast.LENGTH_SHORT).show();
                                                            Intent pasele = new Intent(MainActivity.this,Main2Activity.class);
                                                            startActivity(pasele);
                                                            break;
                                                        case 201:
                                                            Toast.makeText(getApplicationContext(),"Error en el envio",Toast.LENGTH_SHORT).show();
                                                            break;
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
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