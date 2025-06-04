package com.example.aniclips.adapters;

import android.content.Context;
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
import com.example.aniclips.dto.ClipDto;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class ClipsHomeAdapter extends RecyclerView.Adapter<ClipsHomeAdapter.ClipViewHolder> {

    private List<ClipDto> clipList;

    public ClipsHomeAdapter(List<ClipDto> clipList) {
        this.clipList = clipList;
    }

    public static class ClipViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPerfil;
        TextView textViewUsername;
        MaterialButton followButton;
        VideoView videoViewClip;

        public ClipViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPerfil = itemView.findViewById(R.id.imageViewPerfil);
            textViewUsername = itemView.findViewById(R.id.tvUsername);
            followButton = itemView.findViewById(R.id.btnFollow);
            videoViewClip = itemView.findViewById(R.id.videoViewClip);
        }
    }

    @NonNull
    @Override
    public ClipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_clip, parent, false);
        return new ClipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClipViewHolder holder, int position) {
        ClipDto clip = clipList.get(position);

        holder.textViewUsername.setText(clip.getGetUsuarioClipDto().getUsername());
        holder.videoViewClip.setVideoPath(clip.getUrlVideo());
        String avatarUrl = clip.getGetUsuarioClipDto().getGetPerfilAvatarDto().getAvatar();
        Glide.with(holder.itemView.getContext())
                .load(avatarUrl)
                .into(holder.imageViewPerfil);

    }

    @Override
    public int getItemCount() {
        return clipList.size();
    }

    public void addClips(List<ClipDto> nuevosClips) {
        int startPos = clipList.size();
        clipList.addAll(nuevosClips);
        notifyItemRangeInserted(startPos, nuevosClips.size());
    }
}
