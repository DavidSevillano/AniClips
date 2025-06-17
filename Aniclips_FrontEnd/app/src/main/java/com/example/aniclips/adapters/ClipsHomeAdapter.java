package com.example.aniclips.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.PopupWindow;
import android.widget.LinearLayout;
import android.content.res.ColorStateList;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aniclips.R;
import com.example.aniclips.controllers.CommentsController;
import com.example.aniclips.controllers.CreateCommentController;
import com.example.aniclips.controllers.DeleteClipController;
import com.example.aniclips.controllers.FollowController;
import com.example.aniclips.controllers.LikeController;
import com.example.aniclips.controllers.RateController;
import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.dto.ComentarioDto;
import com.example.aniclips.dto.UsuarioClipDto;
import com.example.aniclips.interfaces.DeleteCallback;
import com.example.aniclips.interfaces.FollowCallback;
import com.example.aniclips.interfaces.MeGustaCallback;
import com.example.aniclips.interfaces.OnUserClickListener;
import com.example.aniclips.interfaces.RateCallback;
import com.example.aniclips.utils.Constantes;
import com.example.aniclips.utils.HideNavigationBar;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
    private RecyclerView recyclerView;

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

    public interface OnCommentSheetListener {
        void onOpenCommentSheet(ClipDto clip);
    }
    private OnCommentSheetListener commentSheetListener;
    public void setOnCommentSheetListener(OnCommentSheetListener listener) {
        this.commentSheetListener = listener;
    }
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
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
        ImageButton ibComment;
        ImageButton ibCommentFilled;
        ImageButton ibSendComment;
        ImageButton ibUser;
        ImageButton ibSoundOn;
        ImageButton ibSoundOff;
        EditText etNuevoComentario;
        ImageView ivMiniatura;
        PlayerView playerView;
        ExoPlayer exoPlayer;

        boolean isMuted = false;

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
            ibComment = itemView.findViewById(R.id.ibComment);
            ibCommentFilled = itemView.findViewById(R.id.ibCommentFilled);
            ibSendComment = itemView.findViewById(R.id.btnEnviarComentario);
            etNuevoComentario = itemView.findViewById(R.id.etNuevoComentario);
            ibUser = itemView.findViewById(R.id.ibUser);
            ibSoundOn = itemView.findViewById(R.id.ibSoundOn);
            ibSoundOff = itemView.findViewById(R.id.ibSoundOff);
        }
    }

    private void showSoundButtons(View soundOn, View soundOff, boolean isMuted) {
        ObjectAnimator alphaOn = ObjectAnimator.ofFloat(soundOn, "alpha", isMuted ? 0f : 1f);
        ObjectAnimator alphaOff = ObjectAnimator.ofFloat(soundOff, "alpha", isMuted ? 1f : 0f);
        ObjectAnimator translateOn = ObjectAnimator.ofFloat(soundOn, "translationY", 20f, 0f);
        ObjectAnimator translateOff = ObjectAnimator.ofFloat(soundOff, "translationY", 20f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaOn, alphaOff, translateOn, translateOff);
        animatorSet.setDuration(250);
        animatorSet.start();
    }

    private void hideSoundButtons(View soundOn, View soundOff) {
        ObjectAnimator alphaOn = ObjectAnimator.ofFloat(soundOn, "alpha", 0f);
        ObjectAnimator alphaOff = ObjectAnimator.ofFloat(soundOff, "alpha", 0f);
        ObjectAnimator translateOn = ObjectAnimator.ofFloat(soundOn, "translationY", 0f, 20f);
        ObjectAnimator translateOff = ObjectAnimator.ofFloat(soundOff, "translationY", 0f, 20f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaOn, alphaOff, translateOn, translateOff);
        animatorSet.setDuration(250);
        animatorSet.start();
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
        String myUserId = prefs.getString(Constantes.PREF_MY_USER_ID, null);
        String clipUserId = clip.getGetUsuarioClipDto().getIdUser().toString();

        boolean isAdmin = "ADMIN".equals(userRole);
        boolean isOwner = myUserId != null && myUserId.equals(clipUserId);

        if (isAdmin || isOwner) {
            holder.ibDelete.setVisibility(View.VISIBLE);
            setupDelete(holder, clip, context);
        } else {
            holder.ibDelete.setVisibility(View.GONE);
        }

        if (clip.isLoComento()) {
            holder.ibComment.setVisibility(View.GONE);
            holder.ibCommentFilled.setVisibility(View.VISIBLE);
        } else {
            holder.ibComment.setVisibility(View.VISIBLE);
            holder.ibCommentFilled.setVisibility(View.GONE);
        }

        holder.ibComment.setOnClickListener(v -> {
            if (commentSheetListener != null) commentSheetListener.onOpenCommentSheet(clip);
        });
        holder.ibCommentFilled.setOnClickListener(v -> {
            if (commentSheetListener != null) commentSheetListener.onOpenCommentSheet(clip);
        });

        View.OnClickListener openCommentsSheet = v -> {
            LayoutInflater inflater = LayoutInflater.from(context);
            View sheetView = inflater.inflate(R.layout.bottom_sheet_comentarios, null);

            int maxHeight = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.55);
            sheetView.post(() -> {
                ViewGroup.LayoutParams params = sheetView.getLayoutParams();
                if (params == null) {
                    params = new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                }
                params.height = maxHeight;
                sheetView.setLayoutParams(params);
            });

            RecyclerView rv = sheetView.findViewById(R.id.rvComentarios);
            rv.setLayoutManager(new LinearLayoutManager(context));
            CommentsAdapter adapter = new CommentsAdapter(myUserId, isAdmin);            rv.setAdapter(adapter);
            adapter.setOnCommentsChangedListener((newCount, comentarios) -> {
                holder.tvCommentCount.setText(String.valueOf(newCount));
                boolean tengoComentario = false;
                for (ComentarioDto c : comentarios) {
                    if (myUserId != null && c.getGetUsuarioClipDto() != null &&
                            myUserId.equals(String.valueOf(c.getGetUsuarioClipDto().getIdUser()))) {
                        tengoComentario = true;
                        break;
                    }
                }
                if (tengoComentario) {
                    holder.ibComment.setVisibility(View.GONE);
                    holder.ibCommentFilled.setVisibility(View.VISIBLE);
                } else {
                    holder.ibComment.setVisibility(View.VISIBLE);
                    holder.ibCommentFilled.setVisibility(View.GONE);
                }
            });            EditText etNuevoComentario = sheetView.findViewById(R.id.etNuevoComentario);
            ImageButton btnEnviarComentario = sheetView.findViewById(R.id.btnEnviarComentario);

            BottomSheetDialog dialog = new BottomSheetDialog(context);
            dialog.setContentView(sheetView);

            HideNavigationBar.hideNavigationBar(sheetView);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);

            dialog.setOnShowListener(d -> {
                View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    com.google.android.material.bottomsheet.BottomSheetBehavior<?> behavior =
                            com.google.android.material.bottomsheet.BottomSheetBehavior.from(bottomSheet);
                    behavior.setDraggable(false);
                    behavior.setHideable(true);
                }
            });
            dialog.show();

            new CommentsController(context, clip.getId(), new CommentsController.CommentsCallback() {
                @Override
                public void onCommentsSuccess(List<ComentarioDto> comentarios) {
                    adapter.setComentarios(comentarios);
                }
                @Override
                public void onError(String errorMsg) { }
            }).execute();

            btnEnviarComentario.setOnClickListener(view -> {
                String texto = etNuevoComentario.getText().toString().trim();
                if (texto.isEmpty()) return;
                btnEnviarComentario.setEnabled(false);
                etNuevoComentario.setText("");
                etNuevoComentario.clearFocus();

                clip.setLoComento(true);
                holder.ibComment.setVisibility(View.GONE);
                holder.ibCommentFilled.setVisibility(View.VISIBLE);

                int currentCount = 0;
                try {
                    currentCount = Integer.parseInt(holder.tvCommentCount.getText().toString());
                } catch (NumberFormatException ignored) {}
                holder.tvCommentCount.setText(String.valueOf(currentCount + 1));

                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(etNuevoComentario.getWindowToken(), 0);
                }

                String username = prefs.getString(Constantes.PREF_USER_USERNAME, "Usuario");
                String avatarUrl = prefs.getString(Constantes.PREF_USER_AVATAR, null);
                Log.i("avatar:", avatarUrl);

                UsuarioClipDto usuario = new UsuarioClipDto();
                usuario.setUsername(username);
                if (usuario.getGetPerfilAvatarDto() == null) {
                    usuario.setGetPerfilAvatarDto(new com.example.aniclips.dto.PerfilAvatarDto());
                }
                usuario.getGetPerfilAvatarDto().setAvatar(avatarUrl);

                ComentarioDto nuevoComentario = new ComentarioDto();
                nuevoComentario.setTexto(texto);
                nuevoComentario.setFecha(java.time.LocalDate.now());
                nuevoComentario.setGetUsuarioClipDto(usuario);

                adapter.setComentarios(
                        new java.util.ArrayList<ComentarioDto>() {{
                            add(nuevoComentario);
                            addAll(adapter.getComentarios());
                        }}
                );
                rv.scrollToPosition(0);

                new CreateCommentController(context, clip.getId(), texto, new CreateCommentController.CreateCommentCallback() {
                    @Override
                    public void onSuccess() {
                        btnEnviarComentario.setEnabled(true);
                        new CommentsController(context, clip.getId(), new CommentsController.CommentsCallback() {
                            @Override
                            public void onCommentsSuccess(List<ComentarioDto> comentarios) {
                                adapter.setComentarios(comentarios);
                            }
                            @Override
                            public void onError(String errorMsg) { }
                        }).execute();
                    }
                    @Override
                    public void onError(String errorMsg) {
                        btnEnviarComentario.setEnabled(true);
                    }
                }).execute();
            });
        };

        holder.ibComment.setOnClickListener(openCommentsSheet);
        holder.ibCommentFilled.setOnClickListener(openCommentsSheet);

        holder.textViewUsername.setText(clip.getGetUsuarioClipDto().getUsername());
        holder.textViewUsername.setOnClickListener(v -> {
            if (userClickListener != null) {
                userClickListener.onUserClick(clip.getGetUsuarioClipDto().getIdUser());
            }
        });
        holder.imageViewPerfil.setOnClickListener(v -> {
            if (userClickListener != null) {
                userClickListener.onUserClick(clip.getGetUsuarioClipDto().getIdUser());
            }
        });

        Glide.with(context)
                .load(clip.getUrlMiniatura())
                .centerCrop()
                .into(holder.ivMiniatura);

        holder.tvLikeCount.setText(String.valueOf(clip.getCantidadMeGusta()));
        holder.tvCommentCount.setText(String.valueOf(clip.getCantidadComentarios()));
        holder.tvRatingCount.setText(String.format(Locale.US, "%.2f", clip.getMediaValoraciones()));
        holder.tvDescription.setText(clip.getDescripcion());

        String imagePerfilUrl = clip.getGetUsuarioClipDto().getGetPerfilAvatarDto().getAvatar();

        Log.i("avatarVideo", imagePerfilUrl);
        if (imagePerfilUrl != null && !imagePerfilUrl.isEmpty()) {
            Glide.with(context)
                    .load(imagePerfilUrl)
                    .placeholder(R.drawable.ic_profile)
                    .circleCrop()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.imageViewPerfil);
        } else {
            holder.imageViewPerfil.setImageResource(R.drawable.ic_profile);
        }

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

        holder.isMuted = false;
        holder.ibSoundOn.setAlpha(0f);
        holder.ibSoundOff.setAlpha(0f);
        holder.ibSoundOn.setTranslationY(20f);
        holder.ibSoundOff.setTranslationY(20f);

        Runnable hideSoundButtons = () -> hideSoundButtons(holder.ibSoundOn, holder.ibSoundOff);

        View.OnClickListener soundToggleListener = v -> {
            if (holder.exoPlayer != null) {
                holder.isMuted = !holder.isMuted;
                holder.exoPlayer.setVolume(holder.isMuted ? 0f : 1f);
                showSoundButtons(holder.ibSoundOn, holder.ibSoundOff, holder.isMuted);
                holder.handler.removeCallbacks(hideSoundButtons);
                holder.handler.postDelayed(hideSoundButtons, 2000);
            }
        };
        holder.ibSoundOn.setOnClickListener(soundToggleListener);
        holder.ibSoundOff.setOnClickListener(soundToggleListener);

        holder.ibPlayVideo.setOnClickListener(v -> {
            if (holder.showMiniaturaRunnable != null) {
                holder.handler.removeCallbacks(holder.showMiniaturaRunnable);
            }
            holder.ibPlayVideo.setVisibility(View.GONE);
            holder.ivMiniatura.setVisibility(View.GONE);
            holder.playerView.setVisibility(View.VISIBLE);

            if (holder.exoPlayer == null) {
                holder.exoPlayer = new ExoPlayer.Builder(context).build();
                holder.exoPlayer.setVolume(holder.isMuted ? 0f : 1f);
                holder.playerView.setPlayer(holder.exoPlayer);
                MediaItem mediaItem = MediaItem.fromUri(clip.getUrlVideo());
                holder.exoPlayer.setMediaItem(mediaItem);
                holder.exoPlayer.prepare();
                holder.exoPlayer.addListener(new Player.Listener() {
                    @Override
                    public void onIsPlayingChanged(boolean isPlaying) {
                        if (isPlaying) {
                            showSoundButtons(holder.ibSoundOn, holder.ibSoundOff, holder.isMuted);
                            holder.handler.removeCallbacks(hideSoundButtons);
                            holder.handler.postDelayed(hideSoundButtons, 2000);
                        }
                    }
                    @Override
                    public void onPlaybackStateChanged(int state) {
                        if (state == Player.STATE_ENDED) {
                            holder.ibPlayVideo.setVisibility(View.VISIBLE);
                            holder.ivMiniatura.setVisibility(View.VISIBLE);
                            holder.playerView.setVisibility(View.GONE);
                            hideSoundButtons(holder.ibSoundOn, holder.ibSoundOff);
                        }
                    }
                });
            }
            if (holder.exoPlayer.getPlaybackState() == Player.STATE_ENDED) {
                holder.exoPlayer.seekTo(0);
            }
            holder.exoPlayer.setVolume(holder.isMuted ? 0f : 1f);
            holder.exoPlayer.setPlayWhenReady(true);
            showSoundButtons(holder.ibSoundOn, holder.ibSoundOff, holder.isMuted);
            holder.handler.removeCallbacks(hideSoundButtons);
            holder.handler.postDelayed(hideSoundButtons, 2000);
        });

        holder.playerView.setOnClickListener(v -> {
            if (holder.exoPlayer != null && holder.exoPlayer.isPlaying()) {
                holder.exoPlayer.pause();
                holder.ibPlayVideo.setVisibility(View.VISIBLE);
                hideSoundButtons(holder.ibSoundOn, holder.ibSoundOff);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clipList != null ? clipList.size() : 0;
    }

    public void addClips(List<ClipDto> newClips) {
        int start = clipList.size();
        clipList.addAll(newClips);
        notifyItemRangeInserted(start, newClips.size());
        if (clipList.isEmpty() && onClipsEmptyListener != null) {
            onClipsEmptyListener.onClipsEmpty();
        }
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
            clip.setLedioLike(true);
            clip.setCantidadMeGusta(clip.getCantidadMeGusta() + 1);
            holder.tvLikeCount.setText(String.valueOf(clip.getCantidadMeGusta()));
            holder.ibLike.setVisibility(View.GONE);
            holder.ibLikeFilled.setVisibility(View.VISIBLE);
            holder.ibLikeFilled.setColorFilter(context.getColor(R.color.btn_focused));

            new LikeController(context, clipIdObj, "POST", new MeGustaCallback() {
                @Override
                public void onMegustaSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });

        holder.ibLikeFilled.setOnClickListener(v -> {
            clip.setLedioLike(false);
            clip.setCantidadMeGusta(Math.max(0, clip.getCantidadMeGusta() - 1));
            holder.tvLikeCount.setText(String.valueOf(clip.getCantidadMeGusta()));
            holder.ibLike.setVisibility(View.VISIBLE);
            holder.ibLikeFilled.setVisibility(View.GONE);
            holder.ibLike.setColorFilter(context.getColor(R.color.bottom_nav_icon_color));

            new LikeController(context, clipIdObj, "DELETE", new MeGustaCallback() {
                @Override
                public void onMegustaSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });
    }

    private void showRatingPopup(Context context, Long clipIdObj, View anchorView, ClipDto clip, TextView tvRatingCount, ClipViewHolder holder) {
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

                if (rating > 0) {
                    holder.ibRating.setVisibility(View.GONE);
                    holder.ibRatingFilled.setVisibility(View.VISIBLE);
                    holder.ibRatingFilled.setColorFilter(context.getColor(R.color.btn_focused));
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

        View.OnClickListener ratingClickListener = v -> {
            showRatingPopup(context, clipIdObj, v, clip, holder.tvRatingCount, holder);
        };
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
            clip.setLoSigue(true);
            holder.followButton.setVisibility(View.GONE);
            holder.followedButton.setVisibility(View.VISIBLE);

            new FollowController(context, seguidoId, "POST", new FollowCallback() {
                @Override
                public void onFollowSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });

        holder.followedButton.setOnClickListener(v -> {
            UUID seguidoId = clip.getGetUsuarioClipDto().getIdUser();
            clip.setLoSigue(false);
            holder.followButton.setVisibility(View.VISIBLE);
            holder.followedButton.setVisibility(View.GONE);

            new FollowController(context, seguidoId, "DELETE", new FollowCallback() {
                @Override
                public void onFollowSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });
    }

    private void setupDelete(ClipViewHolder holder, ClipDto clip, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
        String userRole = prefs.getString(Constantes.PREF_MY_USER_ROLE, null);
        boolean isAdmin = "ADMIN".equals(userRole);

        holder.ibDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("ATENCIÓN")
                    .setMessage("¿Estás seguro de que quieres eliminar este clip?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        new DeleteClipController(context, clip.getId(), new DeleteCallback() {
                            @Override
                            public void onDeleteSuccess(org.json.JSONObject response) {
                                int pos = holder.getAdapterPosition();
                                if (pos != RecyclerView.NO_POSITION) {
                                    clipList.remove(pos);
                                    notifyItemRemoved(pos);
                                }
                            }
                            @Override
                            public void onDeleteError(org.json.JSONObject error) { }
                        }, isAdmin).execute();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    public void releaseAllPlayers() {
        if (recyclerView == null) return;
        for (int i = 0; i < getItemCount(); i++) {
            RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition(i);
            if (vh instanceof ClipViewHolder) {
                ClipViewHolder holder = (ClipViewHolder) vh;
                if (holder.exoPlayer != null) {
                    holder.exoPlayer.release();
                    holder.exoPlayer = null;
                }
            }
        }
    }


}