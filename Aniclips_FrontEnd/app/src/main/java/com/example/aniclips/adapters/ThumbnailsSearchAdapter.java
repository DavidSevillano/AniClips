package com.example.aniclips.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.fragments.ClipDetailFragment;
import com.example.aniclips.models.Miniatura;

import java.util.List;

public class ThumbnailsSearchAdapter extends RecyclerView.Adapter<ThumbnailsSearchAdapter.ThumbnailViewHolder> {

    private List<Miniatura> miniaturaList;

    public ThumbnailsSearchAdapter(List<Miniatura> miniaturaList) {
        this.miniaturaList = miniaturaList;
    }

    public static class ThumbnailViewHolder extends RecyclerView.ViewHolder {
        ImageView ibThumbnail;
        TextView tvTitle;

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

        holder.ibThumbnail.setOnClickListener(v -> {
            FragmentActivity activity = (FragmentActivity) holder.itemView.getContext();
            ProgressBar progressBar = activity.findViewById(R.id.progressBar);
            if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

            Fragment fragment = ClipDetailFragment.newInstance(miniatura.getId());
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });
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

    public void clearThumbnails() {
        miniaturaList.clear();
        notifyDataSetChanged();
    }
}