package com.jonathan.Hemovit3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Buzon extends AppCompatActivity {
    float x1 , x2, y1 , y2;
    public Api api;
    Button send;
    EditText coment;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_buzon);
        coment = (EditText)findViewById(R.id.coment);
        send = (Button)findViewById(R.id.send);


        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://9f121845.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api =  retrofit.create(Api.class);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!coment.getText().toString().isEmpty())
                {
                    String usuario =mAuth.getCurrentUser().getEmail().toString();
                    //RealizarPost(coment.getText().toString(),usuario);
                    coment.setText("");


                    Map<String,String> com2 = new HashMap<>();
                    com2.put("correo",usuario);

                    Map<String,String> coments = new HashMap<>();
                    coments.put("correo",usuario);
                    coments.put("comentario",coment);

                    Map<String,Object> obj2 = new HashMap<>();
                    obj2.put("query",com2);
                    obj2.put("comments",coments);


                    Call<ResponseBody> registroUsusario = api.registrarUsuario(obj2);
                    registroUsusario.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                            switch (response.code())
                            {
                                case 200:
                                    Toast.makeText(getApplicationContext(),"Ususario Registrado",Toast.LENGTH_SHORT).show();
                                    break;
                                case 201:
                                    Toast.makeText(getApplicationContext(),"ERR",Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                }
            }
        });
    }




}