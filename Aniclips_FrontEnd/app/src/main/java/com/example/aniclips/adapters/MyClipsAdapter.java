package com.example.aniclips.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.dto.ClipDtoMiniatura;
import com.example.aniclips.fragments.ClipDetailFragment;

import java.util.List;

public class MyClipsAdapter extends RecyclerView.Adapter<MyClipsAdapter.ClipViewHolder> {
    private final List<ClipDtoMiniatura> clips;
    private final Context context;

    public MyClipsAdapter(Context context, List<ClipDtoMiniatura> clips) {
        this.context = context;
        this.clips = clips;
    }

    @NonNull
    @Override
    public ClipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_thumbnails, parent, false);
        return new ClipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClipViewHolder holder, int position) {
        ClipDtoMiniatura clip = clips.get(position);
        holder.tvTitle.setText(clip.getNombreAnime());
        Glide.with(context).load(clip.getMiniatura()).into(holder.ibThumbnail);

        holder.ibThumbnail.setOnClickListener(v -> {
            FragmentActivity activity = (FragmentActivity) context;
            ProgressBar progressBar = activity.findViewById(R.id.progressBar);
            if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

            Fragment fragment = ClipDetailFragment.newInstance(clip.getId());
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return clips.size();
    }

    static class ClipViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageButton ibThumbnail;

        ClipViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ibThumbnail = itemView.findViewById(R.id.ibThumbnail);
        }
    }
}