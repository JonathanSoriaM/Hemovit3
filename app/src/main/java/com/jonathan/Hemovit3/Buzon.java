package com.jonathan.Hemovit3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class Buzon extends AppCompatActivity {
    float x1 , x2, y1 , y2;
    Button send;
    EditText coment;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_buzon);
        coment = (EditText)findViewById(R.id.coment);
        send = (Button)findViewById(R.id.send);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!coment.getText().toString().isEmpty())
                {
                    String usuario =mAuth.getCurrentUser().getEmail().toString();
                    RealizarPost(coment.getText().toString(),usuario);
                    coment.setText("");
                }
            }
        });
    }


    public void RealizarPost(final String comentario, final String usuario) {
        String url = "http://ec2-52-15-254-137.us-east-2.compute.amazonaws.com:3000/comentarios";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   ((TextView)findViewById(R.id.TextResult)).setText(response);
                        Log.v("Respuesta", response);
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
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("comentario", comentario);
                params.put("usuario",usuario);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

}