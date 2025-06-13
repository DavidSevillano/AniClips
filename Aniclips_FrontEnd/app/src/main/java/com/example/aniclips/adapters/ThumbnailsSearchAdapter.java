package com.example.aniclips.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.models.Miniatura;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ThumbnailsSearchAdapter extends RecyclerView.Adapter<ThumbnailsSearchAdapter.ThumbnailViewHolder> {

    private List<Miniatura> miniaturaList;

    public ThumbnailsSearchAdapter(List<Miniatura> miniaturaList) {
        this.miniaturaList = miniaturaList;
    }

    public static class ThumbnailViewHolder extends RecyclerView.ViewHolder {
        ImageView ibThumbnail;
        TextView tvTitle;
        TextView tvDuration;


        public ThumbnailViewHolder(@NonNull View itemView) {
            super(itemView);
            ibThumbnail = itemView.findViewById(R.id.ibThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }

    @NonNull
    @Override
    public ThumbnailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_thumbnails, parent, false);
        return new ThumbnailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThumbnailViewHolder holder, int position) {
        Miniatura miniatura = miniaturaList.get(position);

        holder.tvTitle.setText(miniatura.getNombreAnime());
        String avatarUrl = miniatura.getUrlMiniatura();
        Glide.with(holder.itemView.getContext())
                .load(avatarUrl)
                .into(holder.ibThumbnail);

    }

    @Override
    public int getItemCount() {
        return miniaturaList.size();
    }

    public void addThumbnail(List<Miniatura> nuevasThubnails) {
        int startPos = miniaturaList.size();
        miniaturaList.addAll(nuevasThubnails);
        notifyItemRangeInserted(startPos, nuevasThubnails.size());
    }
}
