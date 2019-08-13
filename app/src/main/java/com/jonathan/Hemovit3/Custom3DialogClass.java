package com.jonathan.Hemovit3;

import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

// para la localizacion
/*import android.content.Intent;
import android.content.pm.PackageManager;*/
//aqui termina

public class Custom3DialogClass extends Dialog implements View.OnClickListener {
    CircleImageView check58;
    TextView direccion;
    RelativeLayout relativeLayout;

    String usuario;
    String unidad;
    String tipo;
    double latitude;
    double longitude;
    String address;

    public Api api;

    FirebaseAuth firebaseAuth;
    Map<String, String> params;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;





    public Custom3DialogClass(@NonNull Context context, String unidad, String s, double latitude, double longitude, String usuario) {
        super(context);
        this.unidad = unidad;
        this.tipo = s;
        this.latitude = latitude;
        this.longitude = longitude;
        this.usuario = usuario;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.direccion);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        check58 = (CircleImageView) findViewById(R.id.check58);
        direccion = (TextView) findViewById(R.id.direccion);
        relativeLayout = (RelativeLayout) findViewById(R.id.rel);

        // codigo de localizacion
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            address = addresses.get(0).getAddressLine(0); //
            direccion.setText(address);
            Log.v("direccion",address); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        //aqui termina codigo de localizacion



       relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        check58.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://d0a45a24.ngrok.io/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                api = retrofit.create(Api.class);
                if (!direccion .getText().toString().isEmpty()) {
                    String usuario = mAuth.getCurrentUser().getEmail().toString();


                    Map<String, String> com4 = new HashMap<>();
                    com4.put("correo", usuario);

                    Map<String, String> coment4 = new HashMap<>();
                    coment4.put("correo", usuario);
                    coment4.put("direccion",address);
                    coment4.put("unidades",unidad);
                    coment4.put("tipo",tipo);

                    Map<String, Object> obj4 = new HashMap<>();
                    obj4.put("query", com4);
                    obj4.put("pedido2", coment4);

                    Log.v("clasificacion", String.valueOf(obj4));

                    Call<ResponseBody> newComent = api.Pedidos(obj4);
                    newComent.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            switch (response.code())
                            {
                                case 200:
                                    Toast.makeText(getApplicationContext(),"Mensaje enviado",Toast.LENGTH_SHORT).show();
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
                }
            }
        });




    }

    @Override
    public void onClick(View view) {

        params = new HashMap<>();
        // the POST parameters:
        params.put("direccion",address);
        params.put("usuario",usuario);
        params.put("unidades",unidad);
        params.put("tipo",tipo);
        //api de firebase
        //RealizarPost( usuario,tipo, address,unidad);


        usuario = mAuth.getCurrentUser().getEmail();
        String usuario = mAuth.getCurrentUser().getEmail();
        // realizar post a api
        FirebaseUser currentUser = mAuth.getCurrentUser();







    }



    //comienza el api con firebase
       /* public void RealizarPost(final String address, final String usuario, final String tipo, final String unidad) {
            String url = "http://ec2-52-15-254-137.us-east-2.compute.amazonaws.com:3000/compras";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //   ((TextView)findViewById(R.id.TextResult)).setText(response);
                            if(!response.isEmpty()){
                                Log.v("Respuesta", response);
                                Custom4DialogClass customDialogClass = new Custom4DialogClass(getContext());
                                customDialogClass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                customDialogClass.show();
                                dismiss();
                            }

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
                protected Map<String, String> getParams() {
 Log.v("param",params.toString());
                    return params;

                }

            };

            Volley.newRequestQueue(Custom3DialogClass.this.getContext()).add(postRequest);
        }
    //termina el post
*/


}


