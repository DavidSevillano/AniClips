package com.example.AniClips.error;

public class ClipNotFoundException extends RuntimeException {

  public ClipNotFoundException(Long id) {
    super("No hay clip con ese ID: %d".formatted(id));
  }

  public ClipNotFoundException(String msg) {
    super(msg);
  }

  public ClipNotFoundException() {
    super("No hay clip con esos requisitos de búsqueda");
  }
}
