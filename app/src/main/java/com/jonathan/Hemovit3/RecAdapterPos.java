package com.jonathan.Hemovit3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecAdapterPos extends RecyclerView.Adapter<RecAdapterPos.ViewHolder> {
        public sangreSelected sangreSelected;
        public Context context;

    public List<Listado> modeloList;

    public RecAdapterPos(List<Listado>modeloList, Context context)
    {
        this.modeloList = modeloList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecAdapterPos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemblood,parent,false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdapterPos.ViewHolder holder, int position) {
        final Listado lista = modeloList.get(position);
        holder.tipe.setText(lista.getNombre() +" " + lista.getClasificacion());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass ventana = new CustomDialogClass(context,lista.getNombre());
                ventana.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ventana.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modeloList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipe = (TextView)itemView.findViewById(R.id.sangre);
        }


    }
}
