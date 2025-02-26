INSERT INTO usuario (id, username, email, password, enabled, activation_token, created_at)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'naruto', 'naruto@konoha.com', '{noop}hashed_password', true, NULL, NOW()),
  ('22222222-2222-2222-2222-222222222222', 'goku', 'goku@saiyan.com', '{noop}hashed_password', true, NULL, NOW()),
  ('33333333-3333-3333-3333-333333333333', 'eren', 'eren@paradis.com', '{noop}hashed_password', true, NULL, NOW()),
  ('44444444-4444-4444-4444-444444444444', 'luffy', 'luffy@strawhat.com', '{noop}hashed_password', true, NULL, NOW()),
  ('55555555-5555-5555-5555-555555555555', 'zoro', 'zoro@strawhat.com', '{noop}hashed_password', true, NULL, NOW());

INSERT INTO usuario_roles (usuario_id, roles)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'USER'),
  ('22222222-2222-2222-2222-222222222222', 'USER'),
  ('33333333-3333-3333-3333-333333333333', 'USER'),
  ('44444444-4444-4444-4444-444444444444', 'USER'),
  ('55555555-5555-5555-5555-555555555555', 'ADMIN');


INSERT INTO perfil (avatar, descripcion, usuario_id)
VALUES
  ('https://example.com/naruto', 'Soy Naruto Uzumaki, futuro Hokage!', '11111111-1111-1111-1111-111111111111'),
  ('https://example.com/goku', '¡El más fuerte del universo!', '22222222-2222-2222-2222-222222222222'),
  ('https://example.com/eren', 'Por la libertad de la humanidad.', '33333333-3333-3333-3333-333333333333'),
  ('https://example.com/luffy', 'Soy Monkey D. Luffy, ¡quiero ser el Rey de los Piratas!', '44444444-4444-4444-4444-444444444444'),
  ('https://example.com/zoro', 'Soy Roronoa Zoro, el espadachín más fuerte.', '55555555-5555-5555-5555-555555555555');

INSERT INTO clip (nombre_anime, descripcion, url_video, fecha, visitas, duracion, miniatura, usuario_id, genero)
VALUES
  ('Naruto Shippuden', 'Naruto vs Pain', 'https://example.com/naruto-vs-pain', '2023-01-15', 5000, 120, 'https://example.com/naruto-vs-pain.jpg', '11111111-1111-1111-1111-111111111111', 'Shonen'),
  ('Dragon Ball Z', 'Goku se transforma en Super Saiyan', 'https://example.com/goku-ssj', '2023-02-20', 10000, 150, 'https://example.com/goku-ssj.jpg', '22222222-2222-2222-2222-222222222222', 'Shonen'),
  ('One Piece', 'Luffy vs Crocodile', 'https://example.com/luffy-vs-crocodile', '2023-03-10', 8000, 130, 'https://example.com/luffy-vs-crocodile.jpg', '44444444-4444-4444-4444-444444444444', 'Shonen'),
  ('One Piece', 'Zoro vs Mihawk', 'https://example.com/zoro-vs-mihawk', '2023-04-12', 15000, 180, 'https://example.com/zoro-vs-mihawk.jpg', '55555555-5555-5555-5555-555555555555', 'Shonen');

INSERT INTO comentario (fecha, texto, usuario_id, clip_id)
VALUES
  ('2023-01-16', '¡Esta batalla fue épica!', '22222222-2222-2222-2222-222222222222', 1),
  ('2023-01-15', '¡Que locura!', '11111111-1111-1111-1111-111111111111', 1),
  ('2023-02-21', '¡Siempre me emociona esta escena!', '33333333-3333-3333-3333-333333333333', 2),
  ('2023-03-11', '¡Un verdadero espadachín!', '44444444-4444-4444-4444-444444444444', 3),
  ('2023-04-13', 'Zoro nunca se rinde, ¡es increíble!', '55555555-5555-5555-5555-555555555555', 4);

INSERT INTO me_gusta (fecha, usuario_id, clip_id)
VALUES
  ('2023-01-16', '22222222-2222-2222-2222-222222222222', 1),
  ('2023-02-21', '33333333-3333-3333-3333-333333333333', 2),
  ('2023-03-11', '11111111-1111-1111-1111-111111111111', 3),
  ('2023-04-14', '55555555-5555-5555-5555-555555555555', 4);

INSERT INTO valoracion (puntuacion, fecha, usuario_id, clip_id)
VALUES
  (9.5, '2023-01-16', '22222222-2222-2222-2222-222222222222', 1),
  (10.0, '2023-02-21', '33333333-3333-3333-3333-333333333333', 2),
  (8.5, '2023-03-11', '11111111-1111-1111-1111-111111111111', 3),
  (4.0, '2023-04-13', '55555555-5555-5555-5555-555555555555', 4);

INSERT INTO seguidores (usuario_id, seguido_id)
VALUES
  ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222'),
  ('22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333'),
  ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111'),
  ('44444444-4444-4444-4444-444444444444', '55555555-5555-5555-5555-555555555555');
