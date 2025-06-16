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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.activities.MainActivity;
import com.example.aniclips.controllers.DeleteClipController;
import com.example.aniclips.controllers.FollowController;
import com.example.aniclips.controllers.HomeFragmentController;
import com.example.aniclips.controllers.LikeController;
import com.example.aniclips.controllers.RateController;
import com.example.aniclips.dialogs.RegisterDialog;
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
    private ImageButton ibComment;
    private ImageView ivMiniatura;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private ProgressBar progressBar;

    private final Map<Long, Integer> ratingsMap = new HashMap<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable showMiniaturaRunnable;

    private ClipDto currentClip;

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
        ibComment = view.findViewById(R.id.ibComment);

        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        view.animate().alpha(1f).setDuration(250).start();

        SharedPreferences prefs = getContext().getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
        String userRole = prefs.getString(Constantes.PREF_MY_USER_ROLE, null);

        new HomeFragmentController(requireActivity(), new HomeClipsCallback() {
            @Override
            public void onClipsLoaded(List<ClipDto> clips) {
                if (clips != null && !clips.isEmpty()) {
                    ClipDto clip = clips.get(0);
                    currentClip = clip;

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

                    textViewUsername.setOnClickListener(v -> {
                        if (!checkLoginAndShowDialog(requireContext())) return;
                        ProfileFragment fragment = ProfileFragment.newInstance(clip.getGetUsuarioClipDto().getIdUser().toString());
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.mainContainer, fragment)
                                .addToBackStack(null)
                                .commit();
                    });
                    imageViewPerfil.setOnClickListener(v -> {
                        if (!checkLoginAndShowDialog(requireContext())) return;
                        ProfileFragment fragment = ProfileFragment.newInstance(clip.getGetUsuarioClipDto().getIdUser().toString());
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.mainContainer, fragment)
                                .addToBackStack(null)
                                .commit();
                    });

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

                    ibComment.setOnClickListener(v -> {
                        LayoutInflater inflater = LayoutInflater.from(requireContext());
                        View sheetView = inflater.inflate(R.layout.bottom_sheet_comentarios, null);

                        RecyclerView rv = sheetView.findViewById(R.id.rvComentarios);
                        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
                        com.example.aniclips.adapters.CommentsAdapter adapter = new com.example.aniclips.adapters.CommentsAdapter();
                        rv.setAdapter(adapter);

                        com.google.android.material.bottomsheet.BottomSheetDialog dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(requireContext());
                        dialog.setContentView(sheetView);
                        dialog.show();

                        new com.example.aniclips.controllers.CommentsController(requireContext(), clipId, new com.example.aniclips.controllers.CommentsController.CommentsCallback() {
                            @Override
                            public void onCommentsSuccess(java.util.List<com.example.aniclips.dto.ComentarioDto> comentarios) {
                                adapter.setComentarios(comentarios);
                            }
                            @Override
                            public void onError(String errorMsg) {
                            }
                        }).execute();
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
            public void onNoClips() { }
        }, 0, 0, clipId, progressBar).execute();

        return view;
    }

    private boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
        return prefs.contains(Constantes.PREF_TOKEN_JWT);
    }

    private boolean checkLoginAndShowDialog(Context context) {
        if (!isUserLoggedIn(context)) {
            new RegisterDialog().show(getParentFragmentManager(), "RegisterDialog");
            return false;
        }
        return true;
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
            if (!checkLoginAndShowDialog(context)) return;
            clip.setLedioLike(true);
            clip.setCantidadMeGusta(clip.getCantidadMeGusta() + 1);
            tvLikeCount.setText(String.valueOf(clip.getCantidadMeGusta()));
            ibLike.setVisibility(View.GONE);
            ibLikeFilled.setVisibility(View.VISIBLE);
            ibLikeFilled.setColorFilter(context.getColor(R.color.btn_focused));

            new LikeController(context, clipIdObj, "POST", new MeGustaCallback() {
                @Override
                public void onMegustaSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) {
                    Log.e("Megusta", "Error al enviar like: " + errorMsg);
                }
            }).execute();
        });

        ibLikeFilled.setOnClickListener(v -> {
            if (!checkLoginAndShowDialog(context)) return;
            clip.setLedioLike(false);
            clip.setCantidadMeGusta(Math.max(0, clip.getCantidadMeGusta() - 1));
            tvLikeCount.setText(String.valueOf(clip.getCantidadMeGusta()));
            ibLike.setVisibility(View.VISIBLE);
            ibLikeFilled.setVisibility(View.GONE);
            ibLike.setColorFilter(context.getColor(R.color.bottom_nav_icon_color));

            new LikeController(context, clipIdObj, "DELETE", new MeGustaCallback() {
                @Override
                public void onMegustaSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) {
                    Log.e("Megusta", "Error al quitar like: " + errorMsg);
                }
            }).execute();
        });
    }

    private void showRatingPopup(Context context, Long clipIdObj, View anchorView, ClipDto clip, TextView tvRatingCount) {
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

                popupWindow.dismiss();
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

        View.OnClickListener ratingClickListener = v -> {
            if (!checkLoginAndShowDialog(context)) return;
            showRatingPopup(context, clipIdObj, v, clip, tvRatingCount);
        };
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
            if (!checkLoginAndShowDialog(context)) return;
            UUID seguidoId = clip.getGetUsuarioClipDto().getIdUser();
            clip.setLoSigue(true);
            followButton.setVisibility(View.GONE);
            followedButton.setVisibility(View.VISIBLE);

            new FollowController(context, seguidoId, "POST", new FollowCallback() {
                @Override
                public void onFollowSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) {
                    Log.e("Follow", "Error: " + errorMsg);
                }
            }).execute();
        });

        followedButton.setOnClickListener(v -> {
            if (!checkLoginAndShowDialog(context)) return;
            UUID seguidoId = clip.getGetUsuarioClipDto().getIdUser();
            clip.setLoSigue(false);
            followButton.setVisibility(View.VISIBLE);
            followedButton.setVisibility(View.GONE);

            new FollowController(context, seguidoId, "DELETE", new FollowCallback() {
                @Override
                public void onFollowSuccess(JSONObject response) { }
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