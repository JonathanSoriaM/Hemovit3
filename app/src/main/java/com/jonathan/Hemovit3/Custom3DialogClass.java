package com.jonathan.Hemovit3;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

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
        check58.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {


Log.v("usuario",unidad);



        params = new HashMap<>();
        // the POST parameters:
        params.put("direccion",address);
        params.put("usuario",usuario);
        params.put("unidades",unidad);
        params.put("tipo",tipo);
        RealizarPost( usuario,tipo, address,unidad);


        usuario = mAuth.getCurrentUser().getEmail();
        String usuario = mAuth.getCurrentUser().getEmail();
        // realizar post a api
        FirebaseUser currentUser = mAuth.getCurrentUser();







    }



    //comienza el api
        public void RealizarPost(final String address, final String usuario, final String tipo, final String unidad) {
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



}


