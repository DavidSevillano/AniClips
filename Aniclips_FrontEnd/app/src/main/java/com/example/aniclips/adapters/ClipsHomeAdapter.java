package com.example.aniclips.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.controllers.LikeController;
import com.example.aniclips.controllers.RateController;
import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.interfaces.MeGustaCallback;
import com.example.aniclips.interfaces.RateCallback;
import com.google.android.material.button.MaterialButton;

import java.util.Map;
import java.util.HashMap;
import org.json.JSONObject;

import java.util.List;

public class ClipsHomeAdapter extends RecyclerView.Adapter<ClipsHomeAdapter.ClipViewHolder> {

    private List<ClipDto> clipList;
    private final Map<Long, Integer> ratingsMap = new HashMap<>();

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
        ImageButton ibRating;
        ImageButton ibRatingFilled;
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
            ibRating = itemView.findViewById(R.id.ibRating);
            ibRatingFilled = itemView.findViewById(R.id.ibRatingFilled);
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
        Context context = holder.itemView.getContext();

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

        setupLike(holder, clip, context);
        setupRating(holder, clip, context);
    }

    private void setupLike(ClipViewHolder holder, ClipDto clip, Context context) {
        Long clipIdObj = clip.getId();

        if (clip.isLedioLike()) {
            holder.ibLike.setVisibility(View.GONE);
            holder.ibLikeFilled.setVisibility(View.VISIBLE);
            holder.ibLikeFilled.setColorFilter(context.getColor(R.color.btn_focused));
        } else {
            holder.ibLike.setVisibility(View.VISIBLE);
            holder.ibLikeFilled.setVisibility(View.GONE);
            holder.ibLike.setColorFilter(context.getColor(R.color.bottom_nav_icon_color));
        }

        holder.ibLike.setOnClickListener(v -> {
            holder.ibLike.setVisibility(View.GONE);
            holder.ibLikeFilled.setVisibility(View.VISIBLE);

            LikeController controller = new LikeController(context, clipIdObj, "POST", new MeGustaCallback() {
                @Override
                public void onMegustaSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) {
                    Log.e("Megusta", "Error al enviar like: " + errorMsg);
                }
            });
            controller.execute();
        });

        holder.ibLikeFilled.setOnClickListener(v -> {
            holder.ibLike.setVisibility(View.VISIBLE);
            holder.ibLikeFilled.setVisibility(View.GONE);

            LikeController controller = new LikeController(context, clipIdObj, "DELETE", new MeGustaCallback() {
                @Override
                public void onMegustaSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) {
                    Log.e("Megusta", "Error al quitar like: " + errorMsg);
                }
            });
            controller.execute();
        });
    }

    private void showRatingPopup(ClipViewHolder holder, Context context, Long clipIdObj, View anchorView) {
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_rating, null);
        RatingBar ratingBar = popupView.findViewById(R.id.ratingBar);

        Integer prevRating = ratingsMap.get(clipIdObj);
        ratingBar.setRating(prevRating != null ? prevRating : 0);
        ratingBar.setProgressTintList(ColorStateList.valueOf(context.getColor(R.color.btn_focused)));

        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight = popupView.getMeasuredHeight();

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );
        popupWindow.setElevation(8f);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1] - popupHeight);

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            if (fromUser) {
                ratingsMap.put(clipIdObj, (int) rating);

                if (rating > 0) {
                    holder.ibRating.setVisibility(View.GONE);
                    holder.ibRatingFilled.setVisibility(View.VISIBLE);
                    holder.ibRatingFilled.setColorFilter(context.getColor(R.color.btn_focused));
                } else {
                    holder.ibRating.setVisibility(View.VISIBLE);
                    holder.ibRatingFilled.setVisibility(View.GONE);
                    holder.ibRating.setColorFilter(context.getColor(R.color.bottom_nav_icon_color));
                }
                new RateController(context, clipIdObj, (int) rating, new RateCallback() {
                    @Override
                    public void onRateSuccess(JSONObject response) { }
                    @Override
                    public void onError(String errorMsg) {
                        Log.e("Valoracion", "Error al enviar valoración: " + errorMsg);
                    }
                }).execute();
            }
        });
    }    private void setupRating(ClipViewHolder holder, ClipDto clip, Context context) {
        Long clipIdObj = clip.getId();

        if (clip.isLoRateo()) {
            holder.ibRating.setVisibility(View.GONE);
            holder.ibRatingFilled.setVisibility(View.VISIBLE);
            holder.ibRatingFilled.setColorFilter(context.getColor(R.color.btn_focused));
        } else {
            holder.ibRating.setVisibility(View.VISIBLE);
            holder.ibRatingFilled.setVisibility(View.GONE);
            holder.ibRating.setColorFilter(context.getColor(R.color.bottom_nav_icon_color));
        }

        View.OnClickListener ratingClickListener = v -> showRatingPopup(holder, context, clipIdObj, v);
        holder.ibRating.setOnClickListener(ratingClickListener);
        holder.ibRatingFilled.setOnClickListener(ratingClickListener);
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
