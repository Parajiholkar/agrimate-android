package com.example.agrimate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    Context context ;
    ArrayList<trending_Disease_model> array ;
    public  Adapter(Context context,ArrayList<trending_Disease_model> array){
        this.context = context ;
        this.array = array ;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(context).inflate(R.layout.recycleview_layout,parent,false);
        viewHolder VH = new viewHolder(v1);
        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.TD_title.setText(array.get(position).Title);
        holder.TD_img.setImageResource(array.get(position).img);
        holder.TD_discription.setText(array.get(position).discription);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView TD_title, TD_discription;
        ImageView TD_img ;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            TD_title = itemView.findViewById(R.id.TD_title);
            TD_img = itemView.findViewById(R.id.TD_img);
            TD_discription = itemView.findViewById(R.id.TD_discription);
        }
    }
}
