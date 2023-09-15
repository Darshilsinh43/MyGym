package com.example.mygym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {
       Context context;
       String [] itemArray;
       int[] imageArray;
    public ProductAdapter(Context context, String[] itemArray, int[] imageArray) {
        this.context = context;
        this.imageArray = imageArray;
        this.itemArray = itemArray;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product,parent,false);
        return new MyHolder(view);
    }
    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product);
            name = itemView.findViewById(R.id.product_name);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.image.setImageResource(imageArray[position]);
        holder.name.setText(itemArray[position]);

    }

    @Override
    public int getItemCount() {
        return itemArray.length;
    }


}
