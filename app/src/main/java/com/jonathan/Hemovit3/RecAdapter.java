package com.jonathan.Hemovit3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyHolder> {
    //public FirebaseDatabase database;
    public DatabaseReference inventario;
    public List<ModeloInventario> list;
    public Context context;

    public void addAll(List<ModeloInventario> fireModels)
    {
        int initsize = list.size();
        list.addAll(fireModels);
        notifyItemRangeChanged(initsize,fireModels.size());
    }

    public RecAdapter(Context context)
    {
        this.list = new ArrayList<>();
        this.context = context;
    }



    @NonNull
    @Override
    public RecAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemblood,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecAdapter.MyHolder myHolder, int i) {
            final ModeloInventario modeloInventario = list.get(i);
            Log.v("SOL",modeloInventario.getSolucion());
            myHolder.sangre.setText(modeloInventario.getSolucion().toUpperCase()+" POSITIVO");
            myHolder.sangre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        String unidad = myHolder.sangre.getText().toString();
                        CustomDialogClass customDialogClass = new CustomDialogClass(context,unidad);
                        customDialogClass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        customDialogClass.show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView factor;
        TextView sangre;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            sangre = (TextView)itemView.findViewById(R.id.sangre);
        }
    }


}
