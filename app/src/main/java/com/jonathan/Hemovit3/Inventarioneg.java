package com.jonathan.Hemovit3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Inventarioneg extends AppCompatActivity {
    FirebaseDatabase database;
    public DatabaseReference inventario;
    List<ModeloInventario> lista;
    final int ITEM_LOAD_COUNT = 21;
    int total_item = 0, last_visible_item;
    boolean isLoading = false, isMaxData = false;
    String last_node = "",last_key = "";
    RecyclerView recyclerView;
    RecNegAdapter recAdapter;
    float x1 , x2, y1 , y2;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
                x1 = event.getX();
                y1 = event.getY();
                break;

            case MotionEvent.ACTION_DOWN:
                x2 = event.getX();
                y2 = event.getY();
                //Derecha a Izquierda
                double a = y2 * y2;
                double b = x2 * x2;
                double c = Math.sqrt((a+b));
                //Der Izq
                if(c >= 1600)
                {
                    Log.v("Direccion","DER IZQ "+String.valueOf(c));
                }
                else if((c > 1100 && c < 1600))
                {
                    Intent intent = new Intent(Inventarioneg.this,Inventario.class);
                    startActivity(intent);

                }



                break;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventarioneg);
        recyclerView = (RecyclerView)findViewById(R.id.listado);
        getLastKey();
        database = FirebaseDatabase.getInstance();
        inventario = database.getReference("inventario/positivos");
        final RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recAdapter = new RecNegAdapter(this);
        recyclerView.setAdapter(recAdapter);
        recyclerView.addOnItemTouchListener(new MyTouchListener(this,
                recyclerView,
                new MyTouchListener.OnTouchActionListener() {
                    @Override
                    public void onLeftSwipe(View view, int position)
                    {

                    }

                    @Override
                    public void onRightSwipe(View view, int position)
                    {
                        Intent intent = new Intent(Inventarioneg.this, Inventario.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onClick(View view, int position)
                    {

                    }
                }));
        getDatos();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                total_item = manager.getItemCount();
                last_visible_item = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
                if(!isLoading && total_item <= ((last_visible_item + ITEM_LOAD_COUNT)))
                {
                    getDatos();
                    isLoading = true;
                }
            }
        });


    }

    public void getLastKey()
    {
        Query getLastK = FirebaseDatabase.getInstance().getReference().child("inventario").child("negativos").orderByKey().limitToLast(1);
        getLastK.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot last : dataSnapshot.getChildren())
                {
                    last_key = last.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDatos()
    {
        if(!isMaxData)
        {
            Query query;
            if(TextUtils.isEmpty(last_node))
            {
                query = FirebaseDatabase.getInstance().getReference().child("inventario").child("negativos").orderByKey().limitToFirst(ITEM_LOAD_COUNT);
            }
            else {
                query = FirebaseDatabase.getInstance().getReference().child("inventario").child("negativos").orderByKey().startAt(last_node).limitToFirst(ITEM_LOAD_COUNT);

            }
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChildren())
                    {
                        lista = new ArrayList<ModeloInventario>();
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                        {
                            ModeloInventario modeloInventario = dataSnapshot1.getValue(ModeloInventario.class);

                            ModeloInventario modeloInventario1 = new ModeloInventario();

                            String solucion = modeloInventario.getSolucion();
                            String factor = modeloInventario.getFactor();

                            modeloInventario1.setSolucion(solucion);
                            modeloInventario1.setFactor(factor);

                            lista.add(modeloInventario1);
                        }
                        last_node = lista.get(lista.size() - 1).getSolucion();
                        //CHECAR
                        if(last_node.equals(last_key))
                        {
                            lista.remove(lista.size() - 1);
                        }
                        else {
                            last_node = "end";
                        }
                        recAdapter.addAll(lista);
                        isLoading = false;

                    }
                    else {
                        isLoading = false;
                        isMaxData = true;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
