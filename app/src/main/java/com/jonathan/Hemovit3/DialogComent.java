package com.jonathan.Hemovit3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jonathan.Hemovit3.Modelos.Hemo;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class DialogComent extends Dialog {
    public Api api;
    private FirebaseAuth mAuth;
    public String codigo;
    ConstraintLayout relativeLayout;
    EditText coment;
    Button send;
    FirebaseDatabase database;
    //public DatabaseReference hemovig;
    public DialogComent(@NonNull Context context, String codigo) {
        super(context);
        this.codigo = codigo;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        database = FirebaseDatabase.getInstance();
        //hemovig = database.getReference().child("hemovigilancia");

        setContentView(R.layout.dialogcoment);
        relativeLayout = (ConstraintLayout)findViewById(R.id.diagcom);
        mAuth = FirebaseAuth.getInstance();
        coment = (EditText)findViewById(R.id.coment);
        send = (Button)findViewById(R.id.send);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comentario = coment.getText().toString();
                String code = codigo;
                Hemo hemo = new Hemo();
                hemo.setCodigo(code);
                hemo.setComentario(comentario);


                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://d0a45a24.ngrok.io/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                api = retrofit.create(Api.class);
                Log.v("texto", comentario);
                Log.v("hemo258", hemo.getComentario());
                if (!coment.getText().toString().isEmpty()) {
                    String usuario = mAuth.getCurrentUser().getEmail().toString();


                    Map<String, String> com3 = new HashMap<>();
                    com3.put("correo", usuario);

                    Map<String, String> coment3 = new HashMap<>();
                    coment3.put("correo", usuario);
                    coment3.put("codigo", code);
                    coment3.put("comentario", coment.getText().toString());

                    Map<String, Object> obj3 = new HashMap<>();
                    obj3.put("query", com3);
                    obj3.put("hemov", coment3);

                    Log.v("clasificacion", String.valueOf(obj3));

                    Call<ResponseBody> newComent = api.Hemovigilancia(obj3);
                    newComent.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(getApplicationContext(), "Mensaje enviado", Toast.LENGTH_SHORT).show();
                                    break;
                                case 201:
                                    Toast.makeText(getApplicationContext(), "Error en el envio", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dismiss();
                }
            }

        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });
    }
}
