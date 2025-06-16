package com.example.aniclips.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.adapters.CommentsAdapter;
import com.example.aniclips.controllers.CommentsController;
import com.example.aniclips.controllers.CreateCommentController;
import com.example.aniclips.controllers.DeleteClipController;
import com.example.aniclips.controllers.FollowController;
import com.example.aniclips.controllers.HomeFragmentController;
import com.example.aniclips.controllers.LikeController;
import com.example.aniclips.controllers.RateController;
import com.example.aniclips.dialogs.RegisterDialog;
import com.example.aniclips.dto.ClipDto;
import com.example.aniclips.dto.ComentarioDto;
import com.example.aniclips.dto.UsuarioClipDto;
import com.example.aniclips.interfaces.DeleteCallback;
import com.example.aniclips.interfaces.FollowCallback;
import com.example.aniclips.interfaces.HomeClipsCallback;
import com.example.aniclips.interfaces.MeGustaCallback;
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
    private ImageButton ibCommentFilled;
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
        ibComment = view.findViewById(R.id.ibComment);
        ibCommentFilled = view.findViewById(R.id.ibCommentFilled);
        progressBar = view.findViewById(R.id.progressBar);

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
                        ibDelete.setOnClickListener(v -> {
                            new DeleteClipController(requireContext(), clip.getId(), new DeleteCallback() {
                                @Override
                                public void onDeleteSuccess(JSONObject response) {
                                    requireActivity().onBackPressed();
                                }
                                @Override
                                public void onDeleteError(JSONObject error) { }
                            }).execute();
                        });
                    } else {
                        ibDelete.setVisibility(View.GONE);
                    }

                    if (clip.isLoComento()) {
                        ibComment.setVisibility(View.GONE);
                        ibCommentFilled.setVisibility(View.VISIBLE);
                    } else {
                        ibComment.setVisibility(View.VISIBLE);
                        ibCommentFilled.setVisibility(View.GONE);
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

                    String fechaStr = clip.getFecha();
                    try {
                        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        Date date = sdfInput.parse(fechaStr);
                        SimpleDateFormat sdfOutput = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
                        String fechaFormateada = sdfOutput.format(date);
                        tvDate.setText(fechaFormateada);
                    } catch (Exception e) {
                        tvDate.setText(fechaStr);
                    }

                    textViewUsername.setOnClickListener(v -> {
                        ProfileFragment fragment = ProfileFragment.newInstance(clip.getGetUsuarioClipDto().getIdUser().toString());
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.mainContainer, fragment)
                                .addToBackStack(null)
                                .commit();
                    });
                    imageViewPerfil.setOnClickListener(v -> {
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

                    View.OnClickListener openCommentsSheet = v -> {
                        LayoutInflater inflater = LayoutInflater.from(requireContext());
                        View sheetView = inflater.inflate(R.layout.bottom_sheet_comentarios, null);

                        HideNavigationBar.hideNavigationBar(sheetView);

                        int maxHeight = (int) (requireContext().getResources().getDisplayMetrics().heightPixels * 0.55);
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
                        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
                        CommentsAdapter adapter = new CommentsAdapter();
                        rv.setAdapter(adapter);

                        EditText etNuevoComentario = sheetView.findViewById(R.id.etNuevoComentario);
                        ImageButton btnEnviarComentario = sheetView.findViewById(R.id.btnEnviarComentario);

                        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
                        dialog.setContentView(sheetView);

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

                        new CommentsController(requireContext(), clip.getId(), new CommentsController.CommentsCallback() {
                            @Override
                            public void onCommentsSuccess(List<ComentarioDto> comentarios) {
                                adapter.setComentarios(comentarios);
                            }
                            @Override
                            public void onError(String errorMsg) { }
                        }).execute();

                        btnEnviarComentario.setOnClickListener(view1 -> {
                            String texto = etNuevoComentario.getText().toString().trim();
                            if (texto.isEmpty()) return;
                            btnEnviarComentario.setEnabled(false);
                            etNuevoComentario.setText("");
                            etNuevoComentario.clearFocus();

                            clip.setLoComento(true);
                            ibComment.setVisibility(View.GONE);
                            ibCommentFilled.setVisibility(View.VISIBLE);

                            int currentCount = 0;
                            try {
                                currentCount = Integer.parseInt(tvCommentCount.getText().toString());
                            } catch (NumberFormatException ignored) {}
                            tvCommentCount.setText(String.valueOf(currentCount + 1));

                            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.hideSoftInputFromWindow(etNuevoComentario.getWindowToken(), 0);
                            }

                            String username = prefs.getString(Constantes.PREF_USER_USERNAME, "Usuario");

                            UsuarioClipDto usuario = new UsuarioClipDto();
                            usuario.setUsername(username);

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

                            new CreateCommentController(requireContext(), clip.getId(), texto, new CreateCommentController.CreateCommentCallback() {
                                @Override
                                public void onSuccess() {
                                    btnEnviarComentario.setEnabled(true);
                                    new CommentsController(requireContext(), clip.getId(), new CommentsController.CommentsCallback() {
                                        @Override
                                        public void onCommentsSuccess(List<ComentarioDto> comentarios) {
                                            adapter.setComentarios(comentarios);
                                            rv.scrollToPosition(adapter.getItemCount() - 1);
                                            clip.setCantidadComentarios(comentarios.size());
                                            tvCommentCount.setText(String.valueOf(comentarios.size()));
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

                    ibComment.setOnClickListener(openCommentsSheet);
                    ibCommentFilled.setOnClickListener(openCommentsSheet);

                    setupLike(clip);
                    setupRating(clip);
                    setupFollow(clip);
                }
            }

            @Override
            public void onNoClips() { }
        }, 0, 1, clipId, progressBar).execute();

        return view;
    }

    private void setupLike(ClipDto clip) {
        Long clipIdObj = clip.getId();

        if (clip.isLedioLike()) {
            ibLike.setVisibility(View.GONE);
            ibLikeFilled.setVisibility(View.VISIBLE);
            ibLikeFilled.setColorFilter(requireContext().getColor(R.color.btn_focused));
        } else {
            ibLike.setVisibility(View.VISIBLE);
            ibLikeFilled.setVisibility(View.GONE);
            ibLike.setColorFilter(requireContext().getColor(R.color.bottom_nav_icon_color));
        }

        ibLike.setOnClickListener(v -> {
            clip.setLedioLike(true);
            clip.setCantidadMeGusta(clip.getCantidadMeGusta() + 1);
            tvLikeCount.setText(String.valueOf(clip.getCantidadMeGusta()));
            ibLike.setVisibility(View.GONE);
            ibLikeFilled.setVisibility(View.VISIBLE);
            ibLikeFilled.setColorFilter(requireContext().getColor(R.color.btn_focused));

            new LikeController(requireContext(), clipIdObj, "POST", new MeGustaCallback() {
                @Override
                public void onMegustaSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });

        ibLikeFilled.setOnClickListener(v -> {
            clip.setLedioLike(false);
            clip.setCantidadMeGusta(Math.max(0, clip.getCantidadMeGusta() - 1));
            tvLikeCount.setText(String.valueOf(clip.getCantidadMeGusta()));
            ibLike.setVisibility(View.VISIBLE);
            ibLikeFilled.setVisibility(View.GONE);
            ibLike.setColorFilter(requireContext().getColor(R.color.bottom_nav_icon_color));

            new LikeController(requireContext(), clipIdObj, "DELETE", new MeGustaCallback() {
                @Override
                public void onMegustaSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });
    }

    private void setupRating(ClipDto clip) {
        Long clipIdObj = clip.getId();

        if (clip.isLoRateo()) {
            ibRating.setVisibility(View.GONE);
            ibRatingFilled.setVisibility(View.VISIBLE);
            ibRatingFilled.setColorFilter(requireContext().getColor(R.color.btn_focused));
        } else {
            ibRating.setVisibility(View.VISIBLE);
            ibRatingFilled.setVisibility(View.GONE);
            ibRating.setColorFilter(requireContext().getColor(R.color.bottom_nav_icon_color));
        }

        View.OnClickListener ratingClickListener = v -> {
            View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.popup_rating, null);
            RatingBar ratingBar = popupView.findViewById(R.id.ratingBar);

            Integer prevRating = ratingsMap.get(clipIdObj);
            ratingBar.setRating(prevRating != null ? prevRating : 0);
            ratingBar.setProgressTintList(ColorStateList.valueOf(requireContext().getColor(R.color.btn_focused)));

            android.widget.PopupWindow popupWindow = new android.widget.PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
            );
            popupWindow.setElevation(8f);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);

            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popupWindow.showAtLocation(v, android.view.Gravity.NO_GRAVITY, location[0], location[1] - popupView.getMeasuredHeight());

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
                        ibRatingFilled.setColorFilter(requireContext().getColor(R.color.btn_focused));
                    } else {
                        ibRating.setVisibility(View.VISIBLE);
                        ibRatingFilled.setVisibility(View.GONE);
                        ibRating.setColorFilter(requireContext().getColor(R.color.bottom_nav_icon_color));
                    }

                    new RateController(requireContext(), clipIdObj, (int) rating, new RateCallback() {
                        @Override
                        public void onRateSuccess(JSONObject response) { }
                        @Override
                        public void onError(String errorMsg) { }
                    }).execute();

                    popupWindow.dismiss();
                }
            });
        };
        ibRating.setOnClickListener(ratingClickListener);
        ibRatingFilled.setOnClickListener(ratingClickListener);
    }

    private void setupFollow(ClipDto clip) {
        SharedPreferences prefs = requireContext().getSharedPreferences("My_prefs", Context.MODE_PRIVATE);
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
            clip.setLoSigue(true);
            followButton.setVisibility(View.GONE);
            followedButton.setVisibility(View.VISIBLE);

            new FollowController(requireContext(), seguidoId, "POST", new FollowCallback() {
                @Override
                public void onFollowSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });

        followedButton.setOnClickListener(v -> {
            UUID seguidoId = clip.getGetUsuarioClipDto().getIdUser();
            clip.setLoSigue(false);
            followButton.setVisibility(View.VISIBLE);
            followedButton.setVisibility(View.GONE);

            new FollowController(requireContext(), seguidoId, "DELETE", new FollowCallback() {
                @Override
                public void onFollowSuccess(JSONObject response) { }
                @Override
                public void onError(String errorMsg) { }
            }).execute();
        });
    }
}