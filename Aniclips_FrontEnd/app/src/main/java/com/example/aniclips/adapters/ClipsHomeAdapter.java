package com.example.aniclips.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.os.Looper;
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
import com.example.aniclips.controllers.DeleteClipController;
import com.example.aniclips.controllers.FollowController;
import com.example.aniclips.controllers.LikeController;
import com.example.aniclips.controllers.RateController;
import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.interfaces.DeleteCallback;
import com.example.aniclips.interfaces.FollowCallback;
import com.example.aniclips.interfaces.MeGustaCallback;
import com.example.aniclips.interfaces.OnUserClickListener;
import com.example.aniclips.interfaces.RateCallback;
import com.example.aniclips.utils.Constantes;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ClipsHomeAdapter extends RecyclerView.Adapter<ClipsHomeAdapter.ClipViewHolder> {

    private List<ClipDto> clipList;
    private final Map<Long, Integer> ratingsMap = new HashMap<>();
    private OnUserClickListener userClickListener;

    public interface OnRequireLoginListener {
        boolean onRequireLogin();
    }
    private OnRequireLoginListener requireLoginListener;

    public void setOnRequireLoginListener(OnRequireLoginListener listener) {
        this.requireLoginListener = listener;
    }

    public ClipsHomeAdapter(List<ClipDto> clipList) {
        this.clipList = clipList;
    }

    public void setOnUserClickListener(OnUserClickListener listener) {
        this.userClickListener = listener;
    }
    public interface OnClipsEmptyListener {
        void onClipsEmpty();
    }
    private OnClipsEmptyListener onClipsEmptyListener;
    public void setOnClipsEmptyListener(OnClipsEmptyListener listener) {
        this.onClipsEmptyListener = listener;
    }

    public static class ClipViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPerfil;
        TextView textViewUsername;
        TextView tvLikeCount;
        TextView tvCommentCount;
        TextView tvRatingCount;
        TextView tvDescription;
        TextView tvDate;
        MaterialButton followButton;
        MaterialButton followedButton;
        ImageButton ibPlayVideo;
        ImageButton ibLike;
        ImageButton ibLikeFilled;
        ImageButton ibRating;
        ImageButton ibRatingFilled;
        ImageButton ibDelete;
        ImageView ivMiniatura;
        PlayerView playerView;
        ExoPlayer exoPlayer;

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable showMiniaturaRunnable;

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
            tvDate = itemView.findViewById(R.id.tvDate);
            ibDelete = itemView.findViewById(R.id.ibDelete);
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

        SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
        String userRole = prefs.getString(Constantes.PREF_MY_USER_ROLE, null);

        if ("ADMIN".equals(userRole)) {
            holder.ibDelete.setVisibility(View.VISIBLE);
            setupDelete(holder, clip, context);
        } else {
            holder.ibDelete.setVisibility(View.GONE);
        }

        holder.textViewUsername.setText(clip.getGetUsuarioClipDto().getUsername());

        holder.playerView.setVisibility(View.GONE);
        holder.ibPlayVideo.setVisibility(View.VISIBLE);
        holder.ivMiniatura.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(clip.getUrlMiniatura())
                .centerCrop()
                .into(holder.ivMiniatura);

        holder.textViewUsername.setOnClickListener(v -> {
            if (requireLoginListener != null && requireLoginListener.onRequireLogin()) return;
            if (userClickListener != null) {
                userClickListener.onUserClick(clip.getGetUsuarioClipDto().getIdUser());
            }
        });
        holder.imageViewPerfil.setOnClickListener(v -> {
            if (requireLoginListener != null && requireLoginListener.onRequireLogin()) return;
            if (userClickListener != null) {
                userClickListener.onUserClick(clip.getGetUsuarioClipDto().getIdUser());
            }
        });

        holder.tvLikeCount.setText(String.valueOf(clip.getCantidadMeGusta()));
        holder.tvCommentCount.setText(String.valueOf(clip.getCantidadComentarios()));
        holder.tvRatingCount.setText(String.format(Locale.US, "%.2f", clip.getMediaValoraciones()));

        holder.ibPlayVideo.setOnClickListener(v -> {
            if (holder.showMiniaturaRunnable != null) {
                holder.handler.removeCallbacks(holder.showMiniaturaRunnable);
            }
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

                            if (holder.showMiniaturaRunnable != null) {
                                holder.handler.removeCallbacks(holder.showMiniaturaRunnable);
                            }
                            holder.showMiniaturaRunnable = () -> {
                                holder.ivMiniatura.setAlpha(0f);
                                holder.ivMiniatura.setVisibility(View.VISIBLE);
                                holder.ivMiniatura.animate()
                                        .alpha(1f)
                                        .setDuration(600)
                                        .start();
                            };
                            holder.handler.postDelayed(holder.showMiniaturaRunnable, 2000);
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

        String fechaStr = clip.getFecha();
        try {
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdfInput.parse(fechaStr);
            SimpleDateFormat sdfOutput = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new java.util.Locale("es", "ES"));
            String fechaFormateada = sdfOutput.format(date);
            holder.tvDate.setText(fechaFormateada);
        } catch (Exception e) {
            holder.tvDate.setText(fechaStr);
        }

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
            if (requireLoginListener != null && requireLoginListener.onRequireLogin()) return;

            clip.setLedioLike(true);
            clip.setCantidadMeGusta(clip.getCantidadMeGusta() + 1);
            notifyItemChanged(holder.getAdapterPosition());

            new LikeController(context, clipIdObj, "POST", new MeGustaCallback() {
                @Override
                public void onMegustaSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });

        holder.ibLikeFilled.setOnClickListener(v -> {
            if (requireLoginListener != null && requireLoginListener.onRequireLogin()) return;

            clip.setLedioLike(false);
            clip.setCantidadMeGusta(Math.max(0, clip.getCantidadMeGusta() - 1));
            notifyItemChanged(holder.getAdapterPosition());

            new LikeController(context, clipIdObj, "DELETE", new MeGustaCallback() {
                @Override
                public void onMegustaSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });
    }

    private void showRatingPopup(ClipViewHolder holder, Context context, Long clipIdObj, View anchorView, ClipDto clip, TextView tvRatingCount) {
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

                int cantidadValoraciones = clip.getCantidadValoraciones();
                double mediaActual = clip.getMediaValoraciones();
                double sumaTotal = mediaActual * cantidadValoraciones;
                sumaTotal += rating;
                int nuevoTotal = cantidadValoraciones + 1;
                double nuevaMedia = sumaTotal / nuevoTotal;

                clip.setMediaValoraciones(nuevaMedia);
                clip.setCantidadValoraciones(nuevoTotal);
                clip.setLoRateo(true);
                tvRatingCount.setText(String.format(Locale.US, "%.2f", nuevaMedia));
                notifyItemChanged(holder.getAdapterPosition());

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
                    public void onError(String errorMsg) { }
                }).execute();

                popupWindow.dismiss();
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

        View.OnClickListener ratingClickListener = v -> showRatingPopup(holder, context, clipIdObj, v, clip, holder.tvRatingCount);
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
            new FollowController(context, seguidoId, "POST", new FollowCallback() {
                @Override
                public void onFollowSuccess(JSONObject response) {
                    holder.followButton.setVisibility(View.GONE);
                    holder.followedButton.setVisibility(View.VISIBLE);
                }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });

        holder.followedButton.setOnClickListener(v -> {
            UUID seguidoId = clip.getGetUsuarioClipDto().getIdUser();
            new FollowController(context, seguidoId, "DELETE", new FollowCallback() {
                @Override
                public void onFollowSuccess(JSONObject response) {
                    holder.followButton.setVisibility(View.VISIBLE);
                    holder.followedButton.setVisibility(View.GONE);
                }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });
    }

    public void updateFollowStateForUser(UUID userId, boolean isFollowed) {
        for (int i = 0; i < clipList.size(); i++) {
            ClipDto clip = clipList.get(i);
            if (clip.getGetUsuarioClipDto().getIdUser().equals(userId)) {
                clip.setLoSigue(isFollowed);
                notifyItemChanged(i);
            }
        }
    }

    private void setupDelete(ClipViewHolder holder, ClipDto clip, Context context) {
        holder.ibDelete.setOnClickListener(v -> {
            androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("ATENCIÓN")
                    .setMessage("¿Estás seguro de que quieres eliminar este clip?")
                    .setPositiveButton("Sí", (dialogInterface, which) -> {
                        new DeleteClipController(context, clip.getId(), new DeleteCallback() {
                            @Override
                            public void onDeleteSuccess(JSONObject response) {
                                int pos = holder.getAdapterPosition();
                                if (pos != RecyclerView.NO_POSITION) {
                                    clipList.remove(pos);
                                    notifyItemRemoved(pos);
                                    if (clipList.isEmpty() && onClipsEmptyListener != null) {
                                        onClipsEmptyListener.onClipsEmpty();
                                    }
                                }
                            }
                            @Override
                            public void onDeleteError(JSONObject error) { }
                        }).execute();
                    })
                    .setNegativeButton("No", null)
                    .create();

            dialog.show();

            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(context.getColor(android.R.color.white));
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(context.getColor(android.R.color.white));
        });
    }

    @Override
    public int getItemCount() {
        return clipList.size();
    }

    public void addClips(List<ClipDto> nuevosClips) {
        int start = clipList.size();
        clipList.addAll(nuevosClips);
        notifyItemRangeInserted(start, nuevosClips.size());
    }

    @Override
    public void onViewRecycled(@NonNull ClipViewHolder holder) {
        if (holder.exoPlayer != null) {
            holder.exoPlayer.release();
            holder.exoPlayer = null;
        }
        if (holder.showMiniaturaRunnable != null) {
            holder.handler.removeCallbacks(holder.showMiniaturaRunnable);
        }
        super.onViewRecycled(holder);
    }
}