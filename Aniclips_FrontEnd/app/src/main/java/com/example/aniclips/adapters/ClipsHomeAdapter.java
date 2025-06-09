package com.example.aniclips.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.controllers.LikeController;
import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.interfaces.MeGustaCallback;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

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
        ImageButton ibPlayVideo;
        ImageButton ibLike;
        ImageButton ibLikeFilled;
        ImageView ivMiniatura;

        public ClipViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPerfil = itemView.findViewById(R.id.imageViewPerfil);
            textViewUsername = itemView.findViewById(R.id.tvUsername);
            followButton = itemView.findViewById(R.id.btnFollow);
            videoViewClip = itemView.findViewById(R.id.videoViewClip);
            ibPlayVideo = itemView.findViewById(R.id.ibPlayVideo);
            ivMiniatura = itemView.findViewById(R.id.ivMiniatura);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibLikeFilled = itemView.findViewById(R.id.ibLikeFilled);
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

        holder.videoViewClip.stopPlayback();
        holder.videoViewClip.setVisibility(View.GONE);
        holder.ibPlayVideo.setVisibility(View.VISIBLE);
        holder.ivMiniatura.setVisibility(View.VISIBLE);

        Glide.with(holder.itemView.getContext())
                .load(clip.getUrlMiniatura())
                .centerCrop()
                .into(holder.ivMiniatura);

        holder.ibPlayVideo.setOnClickListener(v -> {
            holder.ibPlayVideo.setVisibility(View.GONE);
            holder.ivMiniatura.setVisibility(View.GONE);
            holder.videoViewClip.setVisibility(View.VISIBLE);

            if (!holder.videoViewClip.isPlaying()) {
                holder.videoViewClip.start();
            }
        });

        holder.videoViewClip.setOnClickListener(v -> {
            if (holder.videoViewClip.isPlaying()) {
                holder.videoViewClip.pause();
                holder.ibPlayVideo.setVisibility(View.VISIBLE);
            }
        });

        String avatarUrl = clip.getGetUsuarioClipDto().getGetPerfilAvatarDto().getAvatar();
        Glide.with(holder.itemView.getContext())
                .load(avatarUrl)
                .centerCrop()
                .into(holder.imageViewPerfil);

        holder.ibLike.setOnClickListener(v -> {
            Long clipIdObj = clip.getId();

            long clipId = clipIdObj;
            Context context = holder.itemView.getContext();

            LikeController controller = new LikeController(context, clipId, new MeGustaCallback() {
                @Override
                public void onMegustaSuccess(JSONObject response) {
                    holder.ibLike.setVisibility(View.GONE);
                    holder.ibLikeFilled.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(String errorMsg) {
                    Log.e("Megusta", "Error al enviar like: " + errorMsg);
                }
            });
            controller.execute();
        });
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
