package com.example.supergaleriafoto.gallery;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supergaleriafoto.R;
import com.example.supergaleriafoto.models.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.PhotoViewHolder> {

    private final List<Photo> photoList = new ArrayList<>();

    public void submitList(List<Photo> newPhotoList) {
        photoList.clear();
        if(newPhotoList != null) {
            photoList.addAll(newPhotoList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photoList.get(position);

        Log.d("PhotoGridAdapter", "onBindViewHolder position=" + position + " uri=" + photo.getUri());

        Glide.with(holder.itemView.getContext())
                .load(photo.getUri())
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_photo);
        }
    }
}
