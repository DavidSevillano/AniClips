INSERT INTO usuario (id, username, email, password, enabled, activation_token, created_at)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'naruto', 'naruto@konoha.com', 'hashed_password', true, NULL, NOW()),
  ('22222222-2222-2222-2222-222222222222', 'goku', 'goku@saiyan.com', 'hashed_password', true, NULL, NOW()),
  ('33333333-3333-3333-3333-333333333333', 'eren', 'eren@paradis.com', 'hashed_password', true, NULL, NOW());

INSERT INTO perfil (id, avatar, descripcion, usuario_id)
VALUES
  (1, 'https://example.com/naruto', 'Soy Naruto Uzumaki, futuro Hokage!', '11111111-1111-1111-1111-111111111111'),
  (2, 'https://example.com/goku', '¡El más fuerte del universo!', '22222222-2222-2222-2222-222222222222'),
  (3, 'https://example.com/eren', 'Por la libertad de la humanidad.', '33333333-3333-3333-3333-333333333333');

INSERT INTO clip (id, nombre_anime, descripcion, url, fecha, visitas, duracion, miniatura, usuario_id)
VALUES
  (1, 'Naruto Shippuden', 'Naruto vs Pain', 'https://example.com/naruto-vs-pain', '2023-01-15', 5000, 120, 0, '11111111-1111-1111-1111-111111111111'),
  (2, 'Dragon Ball Z', 'Goku se transforma en Super Saiyan', 'https://example.com/goku-ssj', '2023-02-20', 10000, 150, 0, '22222222-2222-2222-2222-222222222222'),
  (3, 'Attack on Titan', 'Eren vs Reiner', 'https://example.com/eren-vs-reiner', '2023-03-10', 8000, 140, 0, '33333333-3333-3333-3333-333333333333');

INSERT INTO comentario (id, fecha, texto, usuario_id, clip_id)
VALUES
  (1, '2023-01-16', '¡Esta batalla fue épica!', '22222222-2222-2222-2222-222222222222', 1),
  (2, '2023-02-21', '¡Siempre me emociona esta escena!', '33333333-3333-3333-3333-333333333333', 2),
  (3, '2023-03-11', 'No hay mejor pelea en AOT.', '11111111-1111-1111-1111-111111111111', 3);

INSERT INTO me_gusta (id, fecha, usuario_id, clip_id)
VALUES
  (1, '2023-01-16', '22222222-2222-2222-2222-222222222222', 1),
  (2, '2023-02-21', '33333333-3333-3333-3333-333333333333', 2),
  (3, '2023-03-11', '11111111-1111-1111-1111-111111111111', 3);

INSERT INTO valoracion (id, puntuacion, fecha, usuario_id, clip_id)
VALUES
  (1, 9.5, '2023-01-16', '22222222-2222-2222-2222-222222222222', 1),
  (2, 10.0, '2023-02-21', '33333333-3333-3333-3333-333333333333', 2),
  (3, 8.5, '2023-03-11', '11111111-1111-1111-1111-111111111111', 3);

INSERT INTO seguidores (usuario_id, seguido_id)
VALUES
  ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222'),
  ('22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333'),
  ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111');

