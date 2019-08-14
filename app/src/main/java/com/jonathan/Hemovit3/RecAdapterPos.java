package com.jonathan.Hemovit3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecAdapterPos extends RecyclerView.Adapter<RecAdapterPos.ViewHolder> {

    public List<Listado> modeloList;

    public RecAdapterPos(List<Listado>modeloList)
    {
        this.modeloList = modeloList;
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
        holder.tipe.setText(lista.getClasificacion());
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
