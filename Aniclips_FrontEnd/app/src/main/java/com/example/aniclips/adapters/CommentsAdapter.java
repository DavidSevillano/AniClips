package com.example.aniclips.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aniclips.R;
import com.example.aniclips.dto.ComentarioDto;
import com.example.aniclips.dto.UsuarioClipDto;
import com.example.aniclips.dto.PerfilAvatarDto;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ComentarioViewHolder> {
    private final List<ComentarioDto> comentarios = new ArrayList<>();

    public void setComentarios(List<ComentarioDto> lista) {
        comentarios.clear();
        if (lista != null) comentarios.addAll(lista);
        notifyDataSetChanged();
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
        if (usuario != null) {
            holder.tvUsuario.setText(usuario.getUsername() != null ? usuario.getUsername() : "");
            PerfilAvatarDto perfil = usuario.getGetPerfilAvatarDto();
            if (perfil != null && perfil.getAvatar() != null && !perfil.getAvatar().isEmpty()) {
                Glide.with(holder.ibUser.getContext())
                        .load(perfil.getAvatar())
                        .centerCrop()
                        .placeholder(R.drawable.icono_ejemplo)
                        .into(holder.ibUser);
            } else {
                holder.ibUser.setImageResource(R.drawable.icono_ejemplo);
            }
        } else {
            holder.tvUsuario.setText("");
            holder.ibUser.setImageResource(R.drawable.icono_ejemplo);
        }
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    static class ComentarioViewHolder extends RecyclerView.ViewHolder {
        TextView tvTexto, tvFecha, tvUsuario;
        ShapeableImageView ibUser;
        ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTexto = itemView.findViewById(R.id.tvTextoComentario);
            tvFecha = itemView.findViewById(R.id.tvFechaComentario);
            tvUsuario = itemView.findViewById(R.id.tvUsuarioComentario);
            ibUser = itemView.findViewById(R.id.ibUser);
        }
    }
}