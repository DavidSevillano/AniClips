INSERT INTO usuario (id, username, email, password, enabled, activation_token, created_at)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'naruto', 'naruto@konoha.com', '{noop}1', true, NULL, NOW()),
  ('22222222-2222-2222-2222-222222222222', 'goku', 'goku@saiyan.com', '{noop}hashed_password', true, NULL, NOW()),
  ('33333333-3333-3333-3333-333333333333', 'eren', 'eren@paradis.com', '{noop}hashed_password', true, NULL, NOW()),
  ('44444444-4444-4444-4444-444444444444', 'luffy', 'luffy@strawhat.com', '{noop}hashed_password', true, NULL, NOW()),
  ('55555555-5555-5555-5555-555555555555', 'zoro', 'zoro@strawhat.com', '{noop}hashed_password', true, NULL, NOW()),
  ('7db9261f-f6b5-42a6-a5c0-b6234a39ffb5', 'usuario01', 'usuario01@anime.com', '{noop}hashed_password', true, NULL, NOW()),
  ('67ce3096-3848-4d10-801f-62a41e72c0cb', 'usuario02', 'usuario02@anime.com', '{noop}hashed_password', true, NULL, NOW()),
  ('e69316d5-9003-4d3b-8322-30d8b28245ab', 'usuario03', 'usuario03@anime.com', '{noop}hashed_password', true, NULL, NOW()),
  ('fd46e5c2-c7a9-4196-84d0-5e2ed0c0a56d', 'usuario04', 'usuario04@anime.com', '{noop}hashed_password', true, NULL, NOW()),
  ('fa49b90d-f100-4b69-8bc6-d7cde9a27204', 'usuario05', 'usuario05@anime.com', '{noop}hashed_password', true, NULL, NOW()),
  ('b2b8f4fc-b1f6-4a4e-8baf-11333cd6b5f4', 'usuario06', 'usuario06@anime.com', '{noop}hashed_password', true, NULL, NOW()),
  ('cfb3f7e5-3620-4bd2-85c1-f7589d137c72', 'usuario07', 'usuario07@anime.com', '{noop}hashed_password', true, NULL, NOW()),
  ('b300b6b6-155e-4c0a-a9e3-6506a5b4fffc', 'usuario08', 'usuario08@anime.com', '{noop}hashed_password', true, NULL, NOW()),
  ('f4b0fc53-348e-4e3d-9ff7-6e50e0a2480a', 'usuario09', 'usuario09@anime.com', '{noop}hashed_password', true, NULL, NOW()),
  ('2e0ef9b2-56a0-4bd1-b94f-30e1a01fc879', 'usuario10', 'usuario10@anime.com', '{noop}hashed_password', true, NULL, NOW());

INSERT INTO usuario_roles (usuario_id, roles)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'USER'),
  ('22222222-2222-2222-2222-222222222222', 'USER'),
  ('33333333-3333-3333-3333-333333333333', 'USER'),
  ('44444444-4444-4444-4444-444444444444', 'USER'),
  ('55555555-5555-5555-5555-555555555555', 'ADMIN'),
  ('7db9261f-f6b5-42a6-a5c0-b6234a39ffb5', 'USER'),
  ('67ce3096-3848-4d10-801f-62a41e72c0cb', 'USER'),
  ('e69316d5-9003-4d3b-8322-30d8b28245ab', 'USER'),
  ('fd46e5c2-c7a9-4196-84d0-5e2ed0c0a56d', 'USER'),
  ('fa49b90d-f100-4b69-8bc6-d7cde9a27204', 'USER'),
  ('b2b8f4fc-b1f6-4a4e-8baf-11333cd6b5f4', 'USER'),
  ('cfb3f7e5-3620-4bd2-85c1-f7589d137c72', 'USER'),
  ('b300b6b6-155e-4c0a-a9e3-6506a5b4fffc', 'USER'),
  ('f4b0fc53-348e-4e3d-9ff7-6e50e0a2480a', 'USER'),
  ('2e0ef9b2-56a0-4bd1-b94f-30e1a01fc879', 'USER');


INSERT INTO perfil (avatar, descripcion, usuario_id)
VALUES
  ('http://192.168.1.141:8080/iconos/icono_ejemplo1.jpg', 'Soy Naruto Uzumaki, futuro Hokage!', '11111111-1111-1111-1111-111111111111'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo2.jpg', '¡El más fuerte del universo!', '22222222-2222-2222-2222-222222222222'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo3.jpg', 'Por la libertad de la humanidad.', '33333333-3333-3333-3333-333333333333'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo4.jpg', 'Soy Monkey D. Luffy, ¡quiero ser el Rey de los Piratas!', '44444444-4444-4444-4444-444444444444'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo5.jpg', 'Soy Roronoa Zoro, el espadachín más fuerte.', '55555555-5555-5555-5555-555555555555'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo6.jpg', 'Soy usuario01, fan del anime.', '7db9261f-f6b5-42a6-a5c0-b6234a39ffb5'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo7.jpg', 'Soy usuario02, fan del anime.', '67ce3096-3848-4d10-801f-62a41e72c0cb'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo8.jpg', 'Soy usuario03, fan del anime.', 'e69316d5-9003-4d3b-8322-30d8b28245ab'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo9.jpg', 'Soy usuario04, fan del anime.', 'fd46e5c2-c7a9-4196-84d0-5e2ed0c0a56d'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo10.jpg', 'Soy usuario05, fan del anime.', 'fa49b90d-f100-4b69-8bc6-d7cde9a27204'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo11.jpg', 'Soy usuario06, fan del anime.', 'b2b8f4fc-b1f6-4a4e-8baf-11333cd6b5f4'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo12.jpg', 'Soy usuario07, fan del anime.', 'cfb3f7e5-3620-4bd2-85c1-f7589d137c72'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo13.jpg', 'Soy usuario08, fan del anime.', 'b300b6b6-155e-4c0a-a9e3-6506a5b4fffc'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo14.jpg', 'Soy usuario09, fan del anime.', 'f4b0fc53-348e-4e3d-9ff7-6e50e0a2480a'),
  ('http://192.168.1.141:8080/iconos/icono_ejemplo15.jpg', 'Soy usuario10, fan del anime.', '2e0ef9b2-56a0-4bd1-b94f-30e1a01fc879');

--INSERT INTO perfil (avatar, descripcion, usuario_id)
--VALUES
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo1.jpg', 'Soy Naruto Uzumaki, futuro Hokage!', '11111111-1111-1111-1111-111111111111'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo2.jpg', '¡El más fuerte del universo!', '22222222-2222-2222-2222-222222222222'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo3.jpg', 'Por la libertad de la humanidad.', '33333333-3333-3333-3333-333333333333'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo4.jpg', 'Soy Monkey D. Luffy, ¡quiero ser el Rey de los Piratas!', '44444444-4444-4444-4444-444444444444'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo5.jpg', 'Soy Roronoa Zoro, el espadachín más fuerte.', '55555555-5555-5555-5555-555555555555'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo6.jpg', 'Soy usuario01, fan del anime.', '7db9261f-f6b5-42a6-a5c0-b6234a39ffb5'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo7.jpg', 'Soy usuario02, fan del anime.', '67ce3096-3848-4d10-801f-62a41e72c0cb'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo8.jpg', 'Soy usuario03, fan del anime.', 'e69316d5-9003-4d3b-8322-30d8b28245ab'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo9.jpg', 'Soy usuario04, fan del anime.', 'fd46e5c2-c7a9-4196-84d0-5e2ed0c0a56d'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo10.jpg', 'Soy usuario05, fan del anime.', 'fa49b90d-f100-4b69-8bc6-d7cde9a27204'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo11.jpg', 'Soy usuario06, fan del anime.', 'b2b8f4fc-b1f6-4a4e-8baf-11333cd6b5f4'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo12.jpg', 'Soy usuario07, fan del anime.', 'cfb3f7e5-3620-4bd2-85c1-f7589d137c72'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo13.jpg', 'Soy usuario08, fan del anime.', 'b300b6b6-155e-4c0a-a9e3-6506a5b4fffc'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo14.jpg', 'Soy usuario09, fan del anime.', 'f4b0fc53-348e-4e3d-9ff7-6e50e0a2480a'),
--  ('http://192.168.56.1:8080/iconos/icono_ejemplo15.jpg', 'Soy usuario10, fan del anime.', '2e0ef9b2-56a0-4bd1-b94f-30e1a01fc879');

INSERT INTO clip (nombre_anime, descripcion, url_video, fecha, visitas, duracion, miniatura, usuario_id, genero)
VALUES
  ('Naruto Shippuden', 'Naruto vs Pain', 'http://192.168.1.141:8080/videos/Ejemplo1.mp4', '2023-01-15', 5000, 120, 'http://192.168.1.141:8080/miniaturas/miniatura1.jpg', '11111111-1111-1111-1111-111111111111', 'Shonen'),
  ('Dragon Ball Z', 'Goku se transforma en Super Saiyan', 'http://192.168.1.141:8080/videos/Ejemplo2.mp4', '2023-02-20', 10000, 150, 'http://192.168.1.141:8080/miniaturas/miniatura2.jpg', '22222222-2222-2222-2222-222222222222', 'Shonen'),
  ('One Piece', 'Luffy vs Crocodile', 'http://192.168.1.141:8080/videos/Ejemplo3.mp4', '2023-03-10', 8000, 130, 'http://192.168.1.141:8080/miniaturas/miniatura3.jpg', '44444444-4444-4444-4444-444444444444', 'Shonen'),
  ('One Piece', 'Zoro vs Mihawk', 'http://192.168.1.141:8080/videos/Ejemplo4.mp4', '2023-04-12', 15000, 180, 'http://192.168.1.141:8080/miniaturas/miniatura4.jpg', '55555555-5555-5555-5555-555555555555', 'Shonen'),
  ('Naruto Shippuden', 'Batalla final entre ninjas legendarios', 'http://192.168.1.141:8080/videos/Ejemplo5.mp4', '2024-01-01', 1000, 85, 'http://192.168.1.141:8080/miniaturas/miniatura5.jpg', '7db9261f-f6b5-42a6-a5c0-b6234a39ffb5', 'Acción'),
  ('One Piece', 'La aventura en el Grand Line continúa', 'http://192.168.1.141:8080/videos/Ejemplo6.mp4', '2024-01-02', 1000, 90, 'http://192.168.1.141:8080/miniaturas/miniatura6.jpg', '67ce3096-3848-4d10-801f-62a41e72c0cb', 'Acción'),
  ('My Hero Academia', 'El despertar de un nuevo héroe', 'http://192.168.1.141:8080/videos/Ejemplo7.mp4', '2024-01-03', 1000, 70, 'http://192.168.1.141:8080/miniaturas/miniatura7.jpg', 'e69316d5-9003-4d3b-8322-30d8b28245ab', 'Acción'),
  ('Demon Slayer', 'Una lucha contra demonios para salvar a la familia', 'http://192.168.1.141:8080/videos/Ejemplo8.mp4', '2024-01-04', 1000, 80, 'http://192.168.1.141:8080/miniaturas/miniatura8.jpg', 'fd46e5c2-c7a9-4196-84d0-5e2ed0c0a56d', 'Acción'),
  ('Fullmetal Alchemist', 'Hermandad y alquimia en un mundo oscuro', 'http://192.168.1.141:8080/videos/Ejemplo9.mp4', '2024-01-05', 1000, 75, 'http://192.168.1.141:8080/miniaturas/miniatura9.jpg', 'fa49b90d-f100-4b69-8bc6-d7cde9a27204', 'Acción'),
  ('Death Note', 'El juego mortal de la justicia', 'http://192.168.1.141:8080/videos/Ejemplo10.mp4', '2024-01-06', 1000, 65, 'http://192.168.1.141:8080/miniaturas/miniatura10.jpg', 'b2b8f4fc-b1f6-4a4e-8baf-11333cd6b5f4', 'Acción'),
  ('Sword Art Online', 'Atrapados en un mundo virtual', 'http://192.168.1.141:8080/videos/Ejemplo11.mp4', '2024-01-07', 1000, 88, 'http://192.168.1.141:8080/miniaturas/miniatura11.jpg', 'cfb3f7e5-3620-4bd2-85c1-f7589d137c72', 'Acción'),
  ('Tokyo Ghoul', 'La dualidad entre humano y monstruo', 'http://192.168.1.141:8080/videos/Ejemplo12.mp4', '2024-01-08', 1000, 85, 'http://192.168.1.141:8080/miniaturas/miniatura12.jpg', 'b300b6b6-155e-4c0a-a9e3-6506a5b4fffc', 'Acción'),
  ('Bleach', 'El destino de los shinigami en batalla', 'http://192.168.1.141:8080/videos/Ejemplo13.mp4', '2024-01-09', 1000, 80, 'http://192.168.1.141:8080/miniaturas/miniatura13.jpg', 'f4b0fc53-348e-4e3d-9ff7-6e50e0a2480a', 'Acción'),
  ('Hunter x Hunter', 'La búsqueda de un cazador legendario', 'http://192.168.1.141:8080/videos/Ejemplo14.mp4', '2024-01-10', 1000, 90, 'http://192.168.1.141:8080/miniaturas/miniatura14.jpg', '2e0ef9b2-56a0-4bd1-b94f-30e1a01fc879', 'Acción'),
  ('Jujutsu Kaisen', 'Gojo vs Sukuna', 'http://192.168.1.141:8080/videos/Ejemplo15.mp4', '2024-01-15', 1200, 110, 'http://192.168.1.141:8080/miniaturas/miniatura15.jpg', '22222222-2222-2222-2222-222222222222', 'Shonen'),
  ('Chainsaw Man', 'Denji se transforma', 'http://192.168.1.141:8080/videos/Ejemplo16.mp4', '2024-01-16', 1300, 95, 'http://192.168.1.141:8080/miniaturas/miniatura16.jpg', '22222222-2222-2222-2222-222222222222', 'Acción'),
  ('Attack on Titan', 'Eren vs Reiner', 'http://192.168.1.141:8080/videos/Ejemplo17.mp4', '2024-01-17', 1400, 105, 'http://192.168.1.141:8080/miniaturas/miniatura17.jpg', '33333333-3333-3333-3333-333333333333', 'Shonen'),
  ('One Piece', 'Luffy Gear 5', 'http://192.168.1.141:8080/videos/Ejemplo18.mp4', '2024-01-18', 1500, 115, 'http://192.168.1.141:8080/miniaturas/miniatura18.jpg', '44444444-4444-4444-4444-444444444444', 'Aventura'),
  ('Demon Slayer', 'Tanjiro contra Akaza', 'http://192.168.1.141:8080/videos/Ejemplo19.mp4', '2024-01-19', 1600, 100, 'http://192.168.1.141:8080/miniaturas/miniatura19.jpg', '55555555-5555-5555-5555-555555555555', 'Acción'),
  ('My Hero Academia', 'Deku Full Power', 'http://192.168.1.141:8080/videos/Ejemplo20.mp4', '2024-01-20', 1700, 98, 'http://192.168.1.141:8080/miniaturas/miniatura20.jpg', '7db9261f-f6b5-42a6-a5c0-b6234a39ffb5', 'Shonen');

--INSERT INTO clip (nombre_anime, descripcion, url_video, fecha, visitas, duracion, miniatura, usuario_id, genero)
--VALUES
--  ('Naruto Shippuden', 'Naruto vs Pain', 'http://192.168.56.1:8080/videos/Ejemplo1.mp4', '2023-01-15', 5000, 120, 'http://192.168.56.1:8080/miniaturas/miniatura1.jpg', '11111111-1111-1111-1111-111111111111', 'Shonen'),
--  ('Dragon Ball Z', 'Goku se transforma en Super Saiyan', 'http://192.168.56.1:8080/videos/Ejemplo2.mp4', '2023-02-20', 10000, 150, 'http://192.168.56.1:8080/miniaturas/miniatura2.jpg', '22222222-2222-2222-2222-222222222222', 'Shonen'),
--  ('One Piece', 'Luffy vs Crocodile', 'http://192.168.56.1:8080/videos/Ejemplo3.mp4', '2023-03-10', 8000, 130, 'http://192.168.56.1:8080/miniaturas/miniatura3.jpg', '44444444-4444-4444-4444-444444444444', 'Shonen'),
--  ('One Piece', 'Zoro vs Mihawk', 'http://192.168.56.1:8080/videos/Ejemplo4.mp4', '2023-04-12', 15000, 180, 'http://192.168.56.1:8080/miniaturas/miniatura4.jpg', '55555555-5555-5555-5555-555555555555', 'Shonen'),
--  ('Naruto Shippuden', 'Batalla final entre ninjas legendarios', 'http://192.168.56.1:8080/videos/Ejemplo5.mp4', '2024-01-01', 1000, 85, 'http://192.168.56.1:8080/miniaturas/miniatura5.jpg', '7db9261f-f6b5-42a6-a5c0-b6234a39ffb5', 'Acción'),
--  ('One Piece', 'La aventura en el Grand Line continúa', 'http://192.168.56.1:8080/videos/Ejemplo6.mp4', '2024-01-02', 1000, 90, 'http://192.168.56.1:8080/miniaturas/miniatura6.jpg', '67ce3096-3848-4d10-801f-62a41e72c0cb', 'Acción'),
--  ('My Hero Academia', 'El despertar de un nuevo héroe', 'http://192.168.56.1:8080/videos/Ejemplo7.mp4', '2024-01-03', 1000, 70, 'http://192.168.56.1:8080/miniaturas/miniatura7.jpg', 'e69316d5-9003-4d3b-8322-30d8b28245ab', 'Acción'),
--  ('Demon Slayer', 'Una lucha contra demonios para salvar a la familia', 'http://192.168.56.1:8080/videos/Ejemplo8.mp4', '2024-01-04', 1000, 80, 'http://192.168.56.1:8080/miniaturas/miniatura8.jpg', 'fd46e5c2-c7a9-4196-84d0-5e2ed0c0a56d', 'Acción'),
--  ('Fullmetal Alchemist', 'Hermandad y alquimia en un mundo oscuro', 'http://192.168.56.1:8080/videos/Ejemplo9.mp4', '2024-01-05', 1000, 75, 'http://192.168.56.1:8080/miniaturas/miniatura9.jpg', 'fa49b90d-f100-4b69-8bc6-d7cde9a27204', 'Acción'),
--  ('Death Note', 'El juego mortal de la justicia', 'http://192.168.56.1:8080/videos/Ejemplo10.mp4', '2024-01-06', 1000, 65, 'http://192.168.56.1:8080/miniaturas/miniatura10.jpg', 'b2b8f4fc-b1f6-4a4e-8baf-11333cd6b5f4', 'Acción'),
--  ('Sword Art Online', 'Atrapados en un mundo virtual', 'http://192.168.56.1:8080/videos/Ejemplo11.mp4', '2024-01-07', 1000, 88, 'http://192.168.56.1:8080/miniaturas/miniatura11.jpg', 'cfb3f7e5-3620-4bd2-85c1-f7589d137c72', 'Acción'),
--  ('Tokyo Ghoul', 'La dualidad entre humano y monstruo', 'http://192.168.56.1:8080/videos/Ejemplo12.mp4', '2024-01-08', 1000, 85, 'http://192.168.56.1:8080/miniaturas/miniatura12.jpg', 'b300b6b6-155e-4c0a-a9e3-6506a5b4fffc', 'Acción'),
--  ('Bleach', 'El destino de los shinigami en batalla', 'http://192.168.56.1:8080/videos/Ejemplo13.mp4', '2024-01-09', 1000, 80, 'http://192.168.56.1:8080/miniaturas/miniatura13.jpg', 'f4b0fc53-348e-4e3d-9ff7-6e50e0a2480a', 'Acción'),
--  ('Hunter x Hunter', 'La búsqueda de un cazador legendario', 'http://192.168.56.1:8080/videos/Ejemplo14.mp4', '2024-01-10', 1000, 90, 'http://192.168.56.1:8080/miniaturas/miniatura14.jpg', '2e0ef9b2-56a0-4bd1-b94f-30e1a01fc879', 'Acción');
--  ('Jujutsu Kaisen', 'Gojo vs Sukuna', 'http://192.168.56.1:8080/videos/Ejemplo15.mp4', '2024-01-15', 1200, 110, 'http://192.168.56.1:8080/miniaturas/miniatura15.jpg', '22222222-2222-2222-2222-222222222222', 'Shonen'),
--  ('Chainsaw Man', 'Denji se transforma', 'http://192.168.56.1:8080/videos/Ejemplo16.mp4', '2024-01-16', 1300, 95, 'http://192.168.56.1:8080/miniaturas/miniatura16.jpg', '22222222-2222-2222-2222-222222222222', 'Acción'),
--  ('Attack on Titan', 'Eren vs Reiner', 'http://192.168.56.1:8080/videos/Ejemplo17.mp4', '2024-01-17', 1400, 105, 'http://192.168.56.1:8080/miniaturas/miniatura17.jpg', '33333333-3333-3333-3333-333333333333', 'Shonen'),
--  ('One Piece', 'Luffy Gear 5', 'http://192.168.56.1:8080/videos/Ejemplo18.mp4', '2024-01-18', 1500, 115, 'http://192.168.56.1:8080/miniaturas/miniatura18.jpg', '44444444-4444-4444-4444-444444444444', 'Aventura'),
--  ('Demon Slayer', 'Tanjiro contra Akaza', 'http://192.168.56.1:8080/videos/Ejemplo19.mp4', '2024-01-19', 1600, 100, 'http://192.168.56.1:8080/miniaturas/miniatura19.jpg', '55555555-5555-5555-5555-555555555555', 'Acción'),
--  ('My Hero Academia', 'Deku Full Power', 'http://192.168.56.1:8080/videos/Ejemplo20.mp4', '2024-01-20', 1700, 98, 'http://192.168.56.1:8080/miniaturas/miniatura20.jpg', '7db9261f-f6b5-42a6-a5c0-b6234a39ffb5', 'Shonen');

INSERT INTO comentario (fecha, texto, usuario_id, clip_id)
VALUES
  ('2023-01-16', '¡Esta batalla fue épica!', '22222222-2222-2222-2222-222222222222', 1),
  ('2023-01-15', '¡Que locura!', '11111111-1111-1111-1111-111111111111', 1),
  ('2023-02-21', '¡Siempre me emociona esta escena!', '33333333-3333-3333-3333-333333333333', 2),
  ('2023-03-11', '¡Un verdadero espadachín!', '44444444-4444-4444-4444-444444444444', 3),
  ('2023-04-13', 'Zoro nunca se rinde, ¡es increíble!', '55555555-5555-5555-5555-555555555555', 4),
  ('2024-01-02', '¡Me encantó esta escena!', '67ce3096-3848-4d10-801f-62a41e72c0cb', 1),
  ('2024-01-03', 'Increíble pelea!', 'e69316d5-9003-4d3b-8322-30d8b28245ab', 2),
  ('2024-01-04', 'No puedo esperar el próximo episodio.', 'fd46e5c2-c7a9-4196-84d0-5e2ed0c0a56d', 3),
  ('2024-01-05', 'Me gustó mucho la animación.', 'fa49b90d-f100-4b69-8bc6-d7cde9a27204', 4),
  ('2024-01-06', '¡Best moment!', 'b2b8f4fc-b1f6-4a4e-8baf-11333cd6b5f4', 5),
  ('2024-01-07', 'Quiero ver más.', 'cfb3f7e5-3620-4bd2-85c1-f7589d137c72', 6),
  ('2024-01-08', 'Muy bueno.', 'b300b6b6-155e-4c0a-a9e3-6506a5b4fffc', 7),
  ('2024-01-09', 'Gran trabajo del equipo.', 'f4b0fc53-348e-4e3d-9ff7-6e50e0a2480a', 8),
  ('2024-01-10', 'Increíble!', '2e0ef9b2-56a0-4bd1-b94f-30e1a01fc879', 9),
  ('2024-01-11', 'Esto me hace feliz.', '7db9261f-f6b5-42a6-a5c0-b6234a39ffb5', 10);

INSERT INTO me_gusta (fecha, usuario_id, clip_id)
VALUES
  ('2023-01-16', '22222222-2222-2222-2222-222222222222', 1),
  ('2023-02-21', '33333333-3333-3333-3333-333333333333', 2),
  ('2023-03-11', '11111111-1111-1111-1111-111111111111', 3),
  ('2023-04-14', '55555555-5555-5555-5555-555555555555', 4),
  ('2024-01-02', '67ce3096-3848-4d10-801f-62a41e72c0cb', 1),
  ('2024-01-03', 'e69316d5-9003-4d3b-8322-30d8b28245ab', 2),
  ('2024-01-04', 'fd46e5c2-c7a9-4196-84d0-5e2ed0c0a56d', 3),
  ('2024-01-05', 'fa49b90d-f100-4b69-8bc6-d7cde9a27204', 4),
  ('2024-01-06', 'b2b8f4fc-b1f6-4a4e-8baf-11333cd6b5f4', 5),
  ('2024-01-07', 'cfb3f7e5-3620-4bd2-85c1-f7589d137c72', 6),
  ('2024-01-08', 'b300b6b6-155e-4c0a-a9e3-6506a5b4fffc', 7),
  ('2024-01-09', 'f4b0fc53-348e-4e3d-9ff7-6e50e0a2480a', 8),
  ('2024-01-10', '2e0ef9b2-56a0-4bd1-b94f-30e1a01fc879', 9),
  ('2024-01-11', '7db9261f-f6b5-42a6-a5c0-b6234a39ffb5', 10);

INSERT INTO valoracion (puntuacion, fecha, usuario_id, clip_id)
VALUES
  (4.5, '2023-01-16', '22222222-2222-2222-2222-222222222222', 1),
  (3.0, '2023-02-21', '33333333-3333-3333-3333-333333333333', 2),
  (3.5, '2023-03-11', '11111111-1111-1111-1111-111111111111', 3),
  (5.0, '2023-04-13', '55555555-5555-5555-5555-555555555555', 4),
  (2.0, '2024-01-02', '67ce3096-3848-4d10-801f-62a41e72c0cb', 1),
  (1.5, '2024-01-03', 'e69316d5-9003-4d3b-8322-30d8b28245ab', 2),
  (2.0, '2024-01-04', 'fd46e5c2-c7a9-4196-84d0-5e2ed0c0a56d', 3),
  (3.5, '2024-01-05', 'fa49b90d-f100-4b69-8bc6-d7cde9a27204', 4),
  (4.5, '2024-01-06', 'b2b8f4fc-b1f6-4a4e-8baf-11333cd6b5f4', 5),
  (5.0, '2024-01-07', 'cfb3f7e5-3620-4bd2-85c1-f7589d137c72', 6),
  (1.0, '2024-01-08', 'b300b6b6-155e-4c0a-a9e3-6506a5b4fffc', 7),
  (2.5, '2024-01-09', 'f4b0fc53-348e-4e3d-9ff7-6e50e0a2480a', 8),
  (3.5, '2024-01-10', '2e0ef9b2-56a0-4bd1-b94f-30e1a01fc879', 9),
  (4.0, '2024-01-11', '7db9261f-f6b5-42a6-a5c0-b6234a39ffb5', 10);

INSERT INTO seguidores (usuario_id, seguido_id)
VALUES
  ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222'),
  ('22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333'),
  ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111'),
  ('44444444-4444-4444-4444-444444444444', '55555555-5555-5555-5555-555555555555'),
  ('7db9261f-f6b5-42a6-a5c0-b6234a39ffb5', '67ce3096-3848-4d10-801f-62a41e72c0cb'),
  ('67ce3096-3848-4d10-801f-62a41e72c0cb', 'e69316d5-9003-4d3b-8322-30d8b28245ab'),
  ('e69316d5-9003-4d3b-8322-30d8b28245ab', 'fd46e5c2-c7a9-4196-84d0-5e2ed0c0a56d'),
  ('fd46e5c2-c7a9-4196-84d0-5e2ed0c0a56d', 'fa49b90d-f100-4b69-8bc6-d7cde9a27204'),
  ('fa49b90d-f100-4b69-8bc6-d7cde9a27204', 'b2b8f4fc-b1f6-4a4e-8baf-11333cd6b5f4'),
  ('b2b8f4fc-b1f6-4a4e-8baf-11333cd6b5f4', 'cfb3f7e5-3620-4bd2-85c1-f7589d137c72'),
  ('cfb3f7e5-3620-4bd2-85c1-f7589d137c72', 'b300b6b6-155e-4c0a-a9e3-6506a5b4fffc'),
  ('b300b6b6-155e-4c0a-a9e3-6506a5b4fffc', 'f4b0fc53-348e-4e3d-9ff7-6e50e0a2480a'),
  ('f4b0fc53-348e-4e3d-9ff7-6e50e0a2480a', '2e0ef9b2-56a0-4bd1-b94f-30e1a01fc879'),
  ('2e0ef9b2-56a0-4bd1-b94f-30e1a01fc879', '7db9261f-f6b5-42a6-a5c0-b6234a39ffb5');