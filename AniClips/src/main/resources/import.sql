INSERT INTO usuario (id, username, email, password, enabled, activation_token, created_at)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'naruto', 'naruto@konoha.com', 'hashed_password', true, NULL, NOW()),
  ('22222222-2222-2222-2222-222222222222', 'goku', 'goku@saiyan.com', 'hashed_password', true, NULL, NOW()),
  ('33333333-3333-3333-3333-333333333333', 'eren', 'eren@paradis.com', 'hashed_password', true, NULL, NOW());

INSERT INTO perfil (avatar, descripcion, usuario_id)
VALUES
  ('https://example.com/naruto', 'Soy Naruto Uzumaki, futuro Hokage!', '11111111-1111-1111-1111-111111111111'),
  ('https://example.com/goku', '¡El más fuerte del universo!', '22222222-2222-2222-2222-222222222222'),
  ('https://example.com/eren', 'Por la libertad de la humanidad.', '33333333-3333-3333-3333-333333333333');

INSERT INTO clip (nombre_anime, descripcion, url_video, fecha, visitas, duracion, miniatura, usuario_id, genero)
VALUES
  ('Naruto Shippuden', 'Naruto vs Pain', 'https://example.com/naruto-vs-pain', '2023-01-15', 5000, 120, 'https://example.com/naruto-vs-pain.jpg', '11111111-1111-1111-1111-111111111111', 'Shonen'),
  ('Dragon Ball Z', 'Goku se transforma en Super Saiyan', 'https://example.com/goku-ssj', '2023-02-20', 10000, 150, 'https://example.com/goku-ssj.jpg', '22222222-2222-2222-2222-222222222222', 'Shonen'),
  ('Attack on Titan', 'Eren vs Reiner', 'https://example.com/eren-vs-reiner', '2023-03-10', 8000, 140, 'https://example.com/eren-vs-reiner', '33333333-3333-3333-3333-333333333333', 'Seinen'),
  ('Naruto Shippuden', 'Naruto vs Pepe', 'https://example.com/naruto-vs-pepe', '2024-02-18', 2500, 20, 'https://example.com/naruto-vs-pepe.jpg', '11111111-1111-1111-1111-111111111111', 'Shonen'),
  ('Dragon Ball Z', 'Goku se destransforma en Super Soyon', 'https://example.com/gokusoyon-ssj', '2024-01-9', 2000, 100, 'https://example.com/gokusoyon-ssj.jpg', '22222222-2222-2222-2222-222222222222', 'Shonen'),
  ('Attack on Titan', 'Eren vs bertorto', 'https://example.com/eren-vs-bertorto', '2024-04-14', 48000, 240, 'https://example.com/eren-vs-bertorto', '33333333-3333-3333-3333-333333333333', 'Seinen');

INSERT INTO comentario (fecha, texto, usuario_id, clip_id)
VALUES
  ('2023-01-16', '¡Esta batalla fue épica!', '22222222-2222-2222-2222-222222222222', 1),
  ('2023-02-21', '¡Siempre me emociona esta escena!', '33333333-3333-3333-3333-333333333333', 2),
  ('2023-03-11', 'No hay mejor pelea en AOT.', '11111111-1111-1111-1111-111111111111', 3);

INSERT INTO me_gusta (fecha, usuario_id, clip_id)
VALUES
  ('2023-01-16', '22222222-2222-2222-2222-222222222222', 1),
  ('2023-02-21', '33333333-3333-3333-3333-333333333333', 2),
  ('2023-03-11', '11111111-1111-1111-1111-111111111111', 3);



INSERT INTO valoracion (puntuacion, fecha, usuario_id, clip_id)
VALUES
  (9.5, '2023-01-16', '22222222-2222-2222-2222-222222222222', 1),
  (10.0, '2023-02-21', '33333333-3333-3333-3333-333333333333', 2),
  (8.5, '2023-03-11', '11111111-1111-1111-1111-111111111111', 3)
  (4, '2023-01-16', '22222222-2222-2222-2222-222222222222', 4),
  (5.5, '2023-02-21', '33333333-3333-3333-3333-333333333333', 5),
  (7.5, '2023-03-11', '11111111-1111-1111-1111-111111111111', 6),
  (4, '2023-01-16', '22222222-2222-2222-2222-222222222222', 1),
  (5.5, '2023-02-21', '33333333-3333-3333-3333-333333333333', 2),
  (7.5, '2023-03-11', '11111111-1111-1111-1111-111111111111', );

INSERT INTO seguidores (usuario_id, seguido_id)
VALUES
  ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222'),
  ('22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333'),
  ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111');

