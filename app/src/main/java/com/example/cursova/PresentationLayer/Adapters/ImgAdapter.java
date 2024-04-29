package com.example.cursova.PresentationLayer.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cursova.R;

import java.util.ArrayList;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHolder> {

    Context context;

    ArrayList<String> imgPaths;

    public ImgAdapter(Context context, ArrayList<String> imgPaths)
    {
        this.context = context;
        this.imgPaths = imgPaths;
    }

    @NonNull
    @Override
    public ImgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImgAdapter.ViewHolder holder, int position) {
        holder.image.setImageBitmap(BitmapFactory.decodeFile(imgPaths.get(position)));

    }

    @Override
    public int getItemCount() {
        return imgPaths.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
