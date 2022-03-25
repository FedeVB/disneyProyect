package com.fede.alk.back.app.controllers;

import com.fede.alk.back.app.models.dtos.PeliculaDto;
import com.fede.alk.back.app.models.entity.Foto;
import com.fede.alk.back.app.models.entity.Pelicula;
import com.fede.alk.back.app.service.interfaces.FotoService;
import com.fede.alk.back.app.service.interfaces.PeliculaService;
import com.fede.alk.back.app.utils.ContentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/movies")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;
    @Autowired
    private FotoService fotoService;

    @GetMapping
    public ResponseEntity<List<PeliculaDto>> listar() {
        return new ResponseEntity<List<PeliculaDto>>(peliculaService.findAll().stream()
                .map(pelicula -> new PeliculaDto(pelicula.getUrlFoto(), pelicula.getTitulo(), pelicula.getFechaCreacion()))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/detalle/{id}")
    public ResponseEntity<?> detalle(@PathVariable(value = "id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        Pelicula pelicula;

        try {
            pelicula = peliculaService.findById(id).orElse(null);
        } catch (DataAccessException e) {
            response.put("mensaje", "Ha ocurrido un error al intentar buscar la pelicula");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pelicula == null) {
            response.put("mensaje", "La pelicula con el id " + id + " no se encuentra en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("pelicula", pelicula);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Pelicula pelicula, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Pelicula newPelicula;

        if (result.hasErrors()) {
            System.out.println("Hay errores");
            List<String> errores = result.getFieldErrors().stream()
                    .map(error -> "El campo " + error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errores", errores);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            newPelicula = peliculaService.save(pelicula);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear la pelicula en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("pelicula", newPelicula);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> editar(@Valid @RequestBody Pelicula pelicula, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Pelicula peliculaUpdate;

        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors().stream()
                    .map(error -> "El campo " + error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errores", errores);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            peliculaUpdate = peliculaService.save(pelicula);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al editar la pelicula en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("pelicula", peliculaUpdate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/imagen")
    public ResponseEntity<?> asignarImagen(@RequestParam(value = "id", name = "id") Integer id
            , @RequestParam(value = "foto", name = "foto") MultipartFile foto) {
        Map<String, Object> response = new HashMap<>();

        Pelicula pelicula = peliculaService.findById(id).orElse(null);

        if (pelicula == null) {
            response.put("mensaje", "No se encontro la pelicula con el id : " + id + " en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Foto imagen = new Foto();
        imagen.setContent(ContentUtil.devolverContent(foto));
        imagen = fotoService.save(imagen);

        try {
            if (pelicula.getUrlFoto() != null) {
                fotoService.deleteById(Integer.parseInt(pelicula.getUrlFoto()
                        .replace("http://localhost:8080/movies/imagen/", "")));
            }
            pelicula.setUrlFoto("http://localhost:8080/movies/imagen/" + imagen.getId());
            peliculaService.save(pelicula);
        } catch (DataAccessException e) {
            fotoService.deleteById(imagen.getId());
            response.put("mensaje", "Error al asignarle la imagen a la pelicula");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La imagen ha sido cargada con exito");
        response.put("pelicula", pelicula);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> eliminar(@PathVariable(value = "id") Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            peliculaService.deleteById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la pelicula en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La pelicula con el id " + id + " ha sido eliminada exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> porNombre(@PathVariable(value = "name") String name) {
        Map<String, Object> response = new HashMap<>();
        List<Pelicula> peliculasBusc;

        try {
            peliculasBusc = peliculaService.findAllByNombre(name);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al buscar la pelicula en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (peliculasBusc == null) {
            response.put("mensaje", "No se encontraron peliculas con el nombre : " + name + " en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("pelicula", peliculasBusc);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/genre/{idGenero}")
    public ResponseEntity<?> porGenero(@PathVariable(value = "idGenero") Integer idGenero) {
        Map<String, Object> response = new HashMap<>();
        List<Pelicula> peliculasBusc;

        try {
            peliculasBusc = peliculaService.findAllByGeneroId(idGenero);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al buscar las peliculas en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("peliculas", peliculasBusc);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/order/ASC")
    public ResponseEntity<?> ordernAsc() {
        Map<String, Object> response = new HashMap<>();
        List<Pelicula> peliculasOrder;

        try {
            peliculasOrder = peliculaService.findAllByOrderByTituloAsc();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("peliculas", peliculasOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/order/DESC")
    public ResponseEntity<?> ordernDesc() {
        Map<String, Object> response = new HashMap<>();
        List<Pelicula> peliculasOrder;

        try {
            peliculasOrder = peliculaService.findAllByOrderByTituloDesc();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("peliculas", peliculasOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<?> imagenes(@PathVariable(value = "id") Integer id) {
        Foto foto = fotoService.findById(id).orElse(null);
        byte[] imagen = foto.getContent();
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, cabecera, HttpStatus.OK);
    }
}
