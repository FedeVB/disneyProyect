INSERT INTO `autoridades` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN'),(3,'ROLE_SUPERVISOR');
INSERT INTO `generos` (`id`, `nombre`) VALUES ('1', 'Ficcion');
INSERT INTO `peliculas` (`id`, `calificacion`, `fecha_creacion`, `titulo`, `id_genero`) VALUES ('1', '4', '1999-10-02', 'Star Wars 1', '1');
INSERT INTO `peliculas` (`id`, `calificacion`, `fecha_creacion`, `titulo`, `id_genero`) VALUES ('2', '4', '2002-10-02', 'Star Wars 2', '1');
INSERT INTO `peliculas` (`id`, `calificacion`, `fecha_creacion`, `titulo`, `id_genero`) VALUES ('3', '5', '2005-10-02', 'Star Wars 3', '1');
INSERT INTO `personajes` (`edad`, `historia`, `nombre`, `peso`) VALUES ('20', 'Estudiante', 'Susan', '77.5');
INSERT INTO `personajes` (`edad`, `historia`, `nombre`, `peso`) VALUES ('21', 'Estudiante', 'Sara', '77.5');
INSERT INTO `personajes` (`edad`, `historia`, `nombre`, `peso`) VALUES ('22', 'Estudiante', 'Susana', '77.5');
INSERT INTO `personajes_peliculas` (`id_personaje`, `id_pelicula`) VALUES ('2', '1');
INSERT INTO `personajes_peliculas` (`id_personaje`, `id_pelicula`) VALUES ('3', '1');
INSERT INTO `personajes_peliculas` (`id_personaje`, `id_pelicula`) VALUES ('2', '2');
INSERT INTO `usuarios` (`email`, `enabled`, `password`, `username`) VALUES ('susan@gmail.com', true, '21313', 'Susan');
INSERT INTO `usuarios` (`email`, `enabled`, `password`, `username`) VALUES ('fede@gmail.com', true, '1234', 'Fede');
INSERT INTO `usuarios_autoridades` (`id_usuario`, `id_autoridad`) VALUES ('1', '1');
INSERT INTO `usuarios_autoridades` (`id_usuario`, `id_autoridad`) VALUES ('2', '1');
INSERT INTO `usuarios_autoridades` (`id_usuario`, `id_autoridad`) VALUES ('1', '2');

