package com.example.aniclips.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.activities.MainActivity;
import com.example.aniclips.adapters.ClipsHomeAdapter;
import com.example.aniclips.controllers.DeleteClipController;
import com.example.aniclips.controllers.FollowController;
import com.example.aniclips.controllers.HomeFragmentController;
import com.example.aniclips.controllers.LikeController;
import com.example.aniclips.controllers.RateController;
import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.interfaces.DeleteCallback;
import com.example.aniclips.interfaces.FollowCallback;
import com.example.aniclips.interfaces.HomeClipsCallback;
import com.example.aniclips.interfaces.MeGustaCallback;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class ClipDetailFragment extends Fragment {
    private static final String ARG_CLIP_ID = "clip_id";
    private long clipId;

    private ImageView imageViewPerfil;
    private TextView textViewUsername;
    private TextView tvLikeCount;
    private TextView tvCommentCount;
    private TextView tvRatingCount;
    private TextView tvDescription;
    private TextView tvDate;
    private MaterialButton followButton;
    private MaterialButton followedButton;
    private ImageButton ibPlayVideo;
    private ImageButton ibLike;
    private ImageButton ibLikeFilled;
    private ImageButton ibRating;
    private ImageButton ibRatingFilled;
    private ImageButton ibDelete;
    private ImageView ivMiniatura;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private ProgressBar progressBar;


    private final Map<Long, Integer> ratingsMap = new HashMap<>();

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable showMiniaturaRunnable;

    public static ClipDetailFragment newInstance(long clipId) {
        ClipDetailFragment fragment = new ClipDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_CLIP_ID, clipId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clipId = getArguments().getLong(ARG_CLIP_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list_clip, container, false);

        imageViewPerfil = view.findViewById(R.id.imageViewPerfil);
        textViewUsername = view.findViewById(R.id.tvUsername);
        followButton = view.findViewById(R.id.btnFollow);
        playerView = view.findViewById(R.id.playerView);
        ibPlayVideo = view.findViewById(R.id.ibPlayVideo);
        ivMiniatura = view.findViewById(R.id.ivMiniatura);
        ibLike = view.findViewById(R.id.ibLike);
        ibLikeFilled = view.findViewById(R.id.ibLikeFilled);
        ibRating = view.findViewById(R.id.ibRating);
        ibRatingFilled = view.findViewById(R.id.ibRatingFilled);
        followedButton = view.findViewById(R.id.btnFollowed);
        tvLikeCount = view.findViewById(R.id.tvLikeCount);
        tvCommentCount = view.findViewById(R.id.tvCommentCount);
        tvRatingCount = view.findViewById(R.id.tvRatingCount);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvDate = view.findViewById(R.id.tvDate);
        ibDelete = view.findViewById(R.id.ibDelete);
        progressBar = view.findViewById(R.id.progressBar);


        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        view.animate().alpha(1f).setDuration(250).start();

        SharedPreferences prefs = getContext().getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
        String userRole = prefs.getString(Constantes.PREF_MY_USER_ROLE, null);

        new HomeFragmentController(requireActivity(), new HomeClipsCallback() {
            @Override
            public void onHomeClipsCallback(List<ClipDto> clips) {
                if (clips != null && !clips.isEmpty()) {

                    ClipDto clip = clips.get(0);

                    if ("ADMIN".equals(userRole)) {
                        ibDelete.setVisibility(View.VISIBLE);
                        setupDelete(clip, requireContext());
                    } else {
                        ibDelete.setVisibility(View.GONE);
                    }

                    textViewUsername.setText(clip.getGetUsuarioClipDto().getUsername());

                    Glide.with(requireContext())
                            .load(clip.getUrlMiniatura())
                            .centerCrop()
                            .into(ivMiniatura);

                    tvLikeCount.setText(String.valueOf(clip.getCantidadMeGusta()));
                    tvCommentCount.setText(String.valueOf(clip.getCantidadComentarios()));
                    tvRatingCount.setText(String.format(Locale.US, "%.2f", clip.getMediaValoraciones()));
                    tvDescription.setText(clip.getDescripcion());

                    Glide.with(requireContext())
                            .load(clip.getGetUsuarioClipDto().getGetPerfilAvatarDto().getAvatar())
                            .centerCrop()
                            .into(imageViewPerfil);

                    ibPlayVideo.setOnClickListener(v -> {
                        if (showMiniaturaRunnable != null) {
                            handler.removeCallbacks(showMiniaturaRunnable);
                        }
                        ibPlayVideo.setVisibility(View.GONE);
                        ivMiniatura.setVisibility(View.GONE);
                        playerView.setVisibility(View.VISIBLE);

                        if (exoPlayer == null) {
                            exoPlayer = new ExoPlayer.Builder(requireContext()).build();
                            exoPlayer.setVolume(1f);
                            playerView.setPlayer(exoPlayer);
                            MediaItem mediaItem = MediaItem.fromUri(clip.getUrlVideo());
                            exoPlayer.setMediaItem(mediaItem);
                            exoPlayer.prepare();
                            exoPlayer.addListener(new Player.Listener() {
                                @Override
                                public void onPlaybackStateChanged(int state) {
                                    if (state == Player.STATE_ENDED) {
                                        ibPlayVideo.setVisibility(View.VISIBLE);
                                        playerView.setVisibility(View.VISIBLE);
                                        ivMiniatura.setVisibility(View.GONE);

                                        if (showMiniaturaRunnable != null) {
                                            handler.removeCallbacks(showMiniaturaRunnable);
                                        }
                                        showMiniaturaRunnable = () -> {
                                            ivMiniatura.setAlpha(0f);
                                            ivMiniatura.setVisibility(View.VISIBLE);
                                            ivMiniatura.animate()
                                                    .alpha(1f)
                                                    .setDuration(600)
                                                    .start();
                                        };
                                        handler.postDelayed(showMiniaturaRunnable, 2000);
                                    }
                                }
                            });
                        }
                        if (exoPlayer.getPlaybackState() == Player.STATE_ENDED) {
                            exoPlayer.seekTo(0);
                        }
                        exoPlayer.setPlayWhenReady(true);
                    });

                    playerView.setOnClickListener(v -> {
                        if (exoPlayer != null && exoPlayer.isPlaying()) {
                            exoPlayer.pause();
                            ibPlayVideo.setVisibility(View.VISIBLE);
                        }
                    });

                    String fechaStr = clip.getFecha();
                        try {
                            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            Date date = sdfInput.parse(fechaStr);
                            SimpleDateFormat sdfOutput = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new java.util.Locale("es", "ES"));
                            String fechaFormateada = sdfOutput.format(date);
                            tvDate.setText(fechaFormateada);
                        } catch (Exception e) {
                            tvDate.setText(fechaStr);
                        }
                    setupLike(clip, requireContext());
                    setupRating(clip, requireContext());
                    setupFollow(clip, requireContext());
                }
            }

            @Override
            public void onError(String message) {
            }
        }, 0, 0, clipId, progressBar).execute();

        return view;
    }

    private void setupLike(ClipDto clip, Context context) {
        Long clipIdObj = clip.getId();

        if (clip.isLedioLike()) {
            ibLike.setVisibility(View.GONE);
            ibLikeFilled.setVisibility(View.VISIBLE);
            ibLikeFilled.setColorFilter(context.getColor(R.color.btn_focused));
        } else {
            ibLike.setVisibility(View.VISIBLE);
            ibLikeFilled.setVisibility(View.GONE);
            ibLike.setColorFilter(context.getColor(R.color.bottom_nav_icon_color));
        }

        ibLike.setOnClickListener(v -> {
            ibLike.setVisibility(View.GONE);
            ibLikeFilled.setVisibility(View.VISIBLE);

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

        ibLikeFilled.setOnClickListener(v -> {
            ibLike.setVisibility(View.VISIBLE);
            ibLikeFilled.setVisibility(View.GONE);

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

    private void showRatingPopup(Context context, Long clipIdObj, View anchorView) {
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
                    ibRating.setVisibility(View.GONE);
                    ibRatingFilled.setVisibility(View.VISIBLE);
                    ibRatingFilled.setColorFilter(context.getColor(R.color.btn_focused));
                } else {
                    ibRating.setVisibility(View.VISIBLE);
                    ibRatingFilled.setVisibility(View.GONE);
                    ibRating.setColorFilter(context.getColor(R.color.bottom_nav_icon_color));
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

    private void setupRating(ClipDto clip, Context context) {
        Long clipIdObj = clip.getId();

        if (clip.isLoRateo()) {
            ibRating.setVisibility(View.GONE);
            ibRatingFilled.setVisibility(View.VISIBLE);
            ibRatingFilled.setColorFilter(context.getColor(R.color.btn_focused));
        } else {
            ibRating.setVisibility(View.VISIBLE);
            ibRatingFilled.setVisibility(View.GONE);
            ibRating.setColorFilter(context.getColor(R.color.bottom_nav_icon_color));
        }

        View.OnClickListener ratingClickListener = v -> showRatingPopup(context, clipIdObj, v);
        ibRating.setOnClickListener(ratingClickListener);
        ibRatingFilled.setOnClickListener(ratingClickListener);
    }

    private void setupFollow(ClipDto clip, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
        String myUserId = prefs.getString(Constantes.PREF_MY_USER_ID, null);

        String clipUserId = clip.getGetUsuarioClipDto().getIdUser().toString();

        if (myUserId != null && myUserId.equals(clipUserId)) {
            followButton.setVisibility(View.GONE);
            followedButton.setVisibility(View.GONE);
            return;
        }

        if (clip.isLoSigue()) {
            followButton.setVisibility(View.GONE);
            followedButton.setVisibility(View.VISIBLE);
        } else {
            followButton.setVisibility(View.VISIBLE);
            followedButton.setVisibility(View.GONE);
        }

        followButton.setOnClickListener(v -> {
            UUID seguidoId = clip.getGetUsuarioClipDto().getIdUser();
            new FollowController(context, seguidoId, new FollowCallback() {
                @Override
                public void onFollowSuccess(JSONObject response) {
                    followButton.setVisibility(View.GONE);
                    followedButton.setVisibility(View.VISIBLE);
                }
                @Override
                public void onError(String errorMsg) {
                    Log.e("Follow", "Error: " + errorMsg);
                }
            }).execute();
        });
    }

    private void setupDelete(ClipDto clip, Context context) {
        ibDelete.setOnClickListener(v -> {
            androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("ATENCIÓN")
                    .setMessage("¿Estás seguro de que quieres eliminar este clip?")
                    .setPositiveButton("Sí", (dialogInterface, which) -> {
                        new DeleteClipController(context, clip.getId(), new DeleteCallback() {
                            @Override
                            public void onDeleteSuccess(JSONObject response) {
                                Toast.makeText(context, "Clip eliminado correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(requireContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                requireActivity().finish();
                            }
                            @Override
                            public void onDeleteError(JSONObject error) {
                                Toast.makeText(context, "Error al eliminar el clip", Toast.LENGTH_SHORT).show();
                            }
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
    public void onDestroyView() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
        if (showMiniaturaRunnable != null) {
            handler.removeCallbacks(showMiniaturaRunnable);
        }
        super.onDestroyView();
    }
}