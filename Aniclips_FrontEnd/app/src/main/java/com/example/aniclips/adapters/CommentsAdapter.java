package com.example.aniclips.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aniclips.R;
import com.example.aniclips.dto.ComentarioDto;
import com.example.aniclips.dto.UsuarioClipDto;
import com.example.aniclips.dto.PerfilAvatarDto;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONObject;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ComentarioViewHolder> {
    private final List<ComentarioDto> comentarios = new ArrayList<>();

    private String myUserId;
    private boolean isAdmin;

    public interface OnCommentsChangedListener {
        void onCommentsCountChanged(int newCount, List<ComentarioDto> comentarios);
    }
    private OnCommentsChangedListener commentsChangedListener;

    public void setOnCommentsChangedListener(OnCommentsChangedListener listener) {
        this.commentsChangedListener = listener;
    }

    public CommentsAdapter(String myUserId, boolean isAdmin) {
        this.myUserId = myUserId;
        this.isAdmin = isAdmin;
    }
    public void setComentarios(List<ComentarioDto> lista) {
        comentarios.clear();
        if (lista != null) comentarios.addAll(lista);
        notifyDataSetChanged();
    }

    public List<ComentarioDto> getComentarios() {
        return new ArrayList<>(comentarios);
    }

    @NonNull
    @Override
    public ComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_comentario, parent, false);
        return new ComentarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioViewHolder holder, int position) {
        ComentarioDto comentario = comentarios.get(position);
        holder.tvTexto.setText(comentario.getTexto() != null ? comentario.getTexto() : "");

        if (comentario.getFecha() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy");
            holder.tvFecha.setText(comentario.getFecha().format(formatter));
        } else {
            holder.tvFecha.setText("");
        }

        UsuarioClipDto usuario = comentario.getGetUsuarioClipDto();
        String comentarioUserId = null;
        if (usuario != null) {
            holder.tvUsuario.setText(usuario.getUsername() != null ? usuario.getUsername() : "");
            PerfilAvatarDto perfil = usuario.getGetPerfilAvatarDto();
            if (perfil != null && perfil.getAvatar() != null && !perfil.getAvatar().isEmpty()) {
                Glide.with(holder.ibUser.getContext())
                        .load(perfil.getAvatar())
                        .placeholder(R.drawable.ic_profile)
                        .circleCrop()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(holder.ibUser);
            } else {
                holder.ibUser.setImageResource(R.drawable.ic_profile);
            }
            if (usuario.getIdUser() != null) {
                comentarioUserId = usuario.getIdUser().toString();
            }
        } else {
            holder.tvUsuario.setText("");
            holder.ibUser.setImageResource(R.drawable.icono_ejemplo);
        }

        if (isAdmin || (myUserId != null && myUserId.equals(comentarioUserId))) {
            holder.ibDelete.setVisibility(View.VISIBLE);
            holder.ibDelete.setOnClickListener(v -> {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition == RecyclerView.NO_POSITION) return;
                Context context = holder.itemView.getContext();
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("ATENCIÓN")
                        .setMessage("¿Estás seguro de que quieres eliminar este comentario?")
                        .setPositiveButton("Sí", (dialogInterface, which) -> {
                            new com.example.aniclips.controllers.DeleteCommentController(
                                    context,
                                    comentarios.get(currentPosition).getId(),
                                    new com.example.aniclips.interfaces.DeleteCallback() {
                                        @Override
                                        public void onDeleteSuccess(JSONObject response) {
                                            comentarios.remove(currentPosition);
                                            notifyItemRemoved(currentPosition);
                                            if (commentsChangedListener != null) {
                                                commentsChangedListener.onCommentsCountChanged(comentarios.size(), getComentarios());
                                            }
                                        }
                                        @Override
                                        public void onDeleteError(org.json.JSONObject error) { }
                                    },
                                    isAdmin
                            ).execute();
                        })
                        .setNegativeButton("No", null)
                        .create();

                dialog.setOnShowListener(dlg -> {
                    dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(context.getColor(android.R.color.white));
                    dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(context.getColor(android.R.color.white));
                });
                dialog.show();
            });
        } else {
            holder.ibDelete.setVisibility(View.GONE);
            holder.ibDelete.setOnClickListener(null);
        }
    }
    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    static class ComentarioViewHolder extends RecyclerView.ViewHolder {
        TextView tvTexto, tvFecha, tvUsuario;
        ShapeableImageView ibUser;
        ImageButton ibDelete;
        ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTexto = itemView.findViewById(R.id.tvTextoComentario);
            tvFecha = itemView.findViewById(R.id.tvFechaComentario);
            tvUsuario = itemView.findViewById(R.id.tvUsuarioComentario);
            ibUser = itemView.findViewById(R.id.ibUser);
            ibDelete = itemView.findViewById(R.id.ibDelete);
        }
    }
}