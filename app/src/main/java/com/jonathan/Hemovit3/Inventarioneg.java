package com.jonathan.Hemovit3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inventarioneg extends AppCompatActivity {
    FirebaseDatabase database;
    public Api api;
    public DatabaseReference inventario;
    List<ModeloInventario> lista;
    final int ITEM_LOAD_COUNT = 21;
    int total_item = 0, last_visible_item;
    boolean isLoading = false, isMaxData = false;
    String last_node = "",last_key = "";
    RecyclerView recyclerView;
    RecAdapterNeg adapter;
    float x1 , x2, y1 , y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        recyclerView = (RecyclerView) findViewById(R.id.listado);

        //comienza comunicacion con api
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hemo-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(Api.class);
        Call<List<Listado>> call = api.getInventarioNeg();
        call.enqueue(new Callback<List<Listado>>() {
            @Override
            public void onResponse(Call<List<Listado>> call, Response<List<Listado>> response) {
                switch (response.code()){
                    case 200:
                        loadlist(response.body());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Listado>> call, Throwable t) {

            }
        });

        recyclerView.addOnItemTouchListener(new MyTouchListener(this,
                recyclerView,
                new MyTouchListener.OnTouchActionListener() {
                    @Override
                    public void onLeftSwipe(View view, int position)
                    {
                        Intent intent = new Intent(Inventarioneg.this, Inventario.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onRightSwipe(View view, int position)
                    {
                        Intent intent = new Intent(Inventarioneg.this, Inventario.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onClick(View view, int position) {

                    }

                }));


    }
    private void loadlist(List<Listado> body) {
        recyclerView = findViewById(R.id.listado);
        adapter = new RecAdapterNeg(body,Inventarioneg.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Inventarioneg.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
