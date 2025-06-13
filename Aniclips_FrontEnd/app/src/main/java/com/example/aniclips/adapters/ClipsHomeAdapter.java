package com.example.aniclips.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.controllers.FollowController;
import com.example.aniclips.controllers.LikeController;
import com.example.aniclips.controllers.RateController;
import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.interfaces.FollowCallback;
import com.example.aniclips.interfaces.MeGustaCallback;
import com.example.aniclips.interfaces.RateCallback;
import com.example.aniclips.utils.Constantes;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ClipsHomeAdapter extends RecyclerView.Adapter<ClipsHomeAdapter.ClipViewHolder> {

    private List<ClipDto> clipList;
    private final Map<Long, Integer> ratingsMap = new HashMap<>();

    public ClipsHomeAdapter(List<ClipDto> clipList) {
        this.clipList = clipList;
    }

    public static class ClipViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPerfil;
        TextView textViewUsername;
        TextView tvLikeCount;
        TextView tvCommentCount;
        TextView tvRatingCount;
        TextView tvDescription;
        MaterialButton followButton;
        MaterialButton followedButton;
        ImageButton ibPlayVideo;
        ImageButton ibLike;
        ImageButton ibLikeFilled;
        ImageButton ibRating;
        ImageButton ibRatingFilled;
        ImageView ivMiniatura;
        PlayerView playerView;
        ExoPlayer exoPlayer;

        public ClipViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPerfil = itemView.findViewById(R.id.imageViewPerfil);
            textViewUsername = itemView.findViewById(R.id.tvUsername);
            followButton = itemView.findViewById(R.id.btnFollow);
            playerView = itemView.findViewById(R.id.playerView);
            ibPlayVideo = itemView.findViewById(R.id.ibPlayVideo);
            ivMiniatura = itemView.findViewById(R.id.ivMiniatura);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibLikeFilled = itemView.findViewById(R.id.ibLikeFilled);
            ibRating = itemView.findViewById(R.id.ibRating);
            ibRatingFilled = itemView.findViewById(R.id.ibRatingFilled);
            followedButton = itemView.findViewById(R.id.btnFollowed);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvRatingCount = itemView.findViewById(R.id.tvRatingCount);
            tvDescription = itemView.findViewById(R.id.tvDescription);
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

        holder.playerView.setVisibility(View.GONE);
        holder.ibPlayVideo.setVisibility(View.VISIBLE);
        holder.ivMiniatura.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(clip.getUrlMiniatura())
                .centerCrop()
                .into(holder.ivMiniatura);

        holder.tvLikeCount.setText(String.valueOf(clip.getCantidadMeGusta()));
        holder.tvCommentCount.setText(String.valueOf(clip.getCantidadComentarios()));
        holder.tvRatingCount.setText(String.valueOf(clip.getMediaValoraciones()));

        holder.ibPlayVideo.setOnClickListener(v -> {
            holder.ibPlayVideo.setVisibility(View.GONE);
            holder.ivMiniatura.setVisibility(View.GONE);
            holder.playerView.setVisibility(View.VISIBLE);

            if (holder.exoPlayer == null) {
                holder.exoPlayer = new ExoPlayer.Builder(context).build();
                holder.exoPlayer.setVolume(1f);
                holder.playerView.setPlayer(holder.exoPlayer);
                MediaItem mediaItem = MediaItem.fromUri(clip.getUrlVideo());
                holder.exoPlayer.setMediaItem(mediaItem);
                holder.exoPlayer.prepare();
                holder.exoPlayer.addListener(new Player.Listener() {
                    @Override
                    public void onPlaybackStateChanged(int state) {
                        if (state == Player.STATE_ENDED) {
                            holder.ibPlayVideo.setVisibility(View.VISIBLE);
                            holder.playerView.setVisibility(View.VISIBLE);
                            holder.ivMiniatura.setVisibility(View.GONE);
                            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                                holder.ivMiniatura.setAlpha(0f);
                                holder.ivMiniatura.setVisibility(View.VISIBLE);
                                holder.ivMiniatura.animate()
                                        .alpha(1f)
                                        .setDuration(600)
                                        .start();
                            }, 2000);
                        }
                    }
                });
            }
            if (holder.exoPlayer.getPlaybackState() == Player.STATE_ENDED) {
                holder.exoPlayer.seekTo(0);
            }
            holder.exoPlayer.setPlayWhenReady(true);
        });

        holder.playerView.setOnClickListener(v -> {
            if (holder.exoPlayer != null && holder.exoPlayer.isPlaying()) {
                holder.exoPlayer.pause();
                holder.ibPlayVideo.setVisibility(View.VISIBLE);
            }
        });

        String avatarUrl = clip.getGetUsuarioClipDto().getGetPerfilAvatarDto().getAvatar();
        Glide.with(context)
                .load(avatarUrl)
                .centerCrop()
                .into(holder.imageViewPerfil);

        holder.tvDescription.setText(clip.getDescripcion());

        setupLike(holder, clip, context);
        setupRating(holder, clip, context);
        setupFollow(holder, clip, context);
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
    }

    private void setupRating(ClipViewHolder holder, ClipDto clip, Context context) {
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

    private void setupFollow(ClipViewHolder holder, ClipDto clip, Context context) {

        SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
        String myUserId = prefs.getString(Constantes.PREF_MY_USER_ID, null);

        String clipUserId = clip.getGetUsuarioClipDto().getIdUser().toString();

        if (myUserId != null && myUserId.equals(clipUserId)) {
            holder.followButton.setVisibility(View.GONE);
            holder.followedButton.setVisibility(View.GONE);
            return;
        }

        if (clip.isLoSigue()) {
            holder.followButton.setVisibility(View.GONE);
            holder.followedButton.setVisibility(View.VISIBLE);
        } else {
            holder.followButton.setVisibility(View.VISIBLE);
            holder.followedButton.setVisibility(View.GONE);
        }

        holder.followButton.setOnClickListener(v -> {
            UUID seguidoId = clip.getGetUsuarioClipDto().getIdUser();
            new FollowController(context, seguidoId, new FollowCallback() {
                @Override
                public void onFollowSuccess(JSONObject response) {
                    holder.followButton.setVisibility(View.GONE);
                    holder.followedButton.setVisibility(View.VISIBLE);
                }
                @Override
                public void onError(String errorMsg) {
                    Log.e("Follow", "Error: " + errorMsg);
                }
            }).execute();
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

    @Override
    public void onViewRecycled(@NonNull ClipViewHolder holder) {
        if (holder.exoPlayer != null) {
            holder.exoPlayer.release();
            holder.exoPlayer = null;
        }
        super.onViewRecycled(holder);
    }
}