package com.kitm.atsiskaitymas_2025_05_05.Controllers;

import com.kitm.atsiskaitymas_2025_05_05.dto.CategoryRequestDTO;
import com.kitm.atsiskaitymas_2025_05_05.dto.CatergoryResponseDTO;
import com.kitm.atsiskaitymas_2025_05_05.dto.FilmRequestDTO;
import com.kitm.atsiskaitymas_2025_05_05.dto.FilmResponseDTO;
import com.kitm.atsiskaitymas_2025_05_05.entity.Category;
import com.kitm.atsiskaitymas_2025_05_05.entity.Film;
import com.kitm.atsiskaitymas_2025_05_05.entity.User;
import com.kitm.atsiskaitymas_2025_05_05.mapper.CategoryMapper;
import com.kitm.atsiskaitymas_2025_05_05.mapper.FilmMapper;
import com.kitm.atsiskaitymas_2025_05_05.security.services.UserDetailsImpl;
import com.kitm.atsiskaitymas_2025_05_05.services.CategoryService;
import com.kitm.atsiskaitymas_2025_05_05.services.FilmService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/films")
public class FilmController {

    private FilmService filmService;
    private CategoryService categoryService;

    @Autowired
    public FilmController(FilmService filmService, CategoryService categoryService)
    {
        this.filmService = filmService;
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FilmResponseDTO> addFilm(@Valid @RequestBody FilmRequestDTO filmRequestDTO)
    {

        Category category = categoryService.findById(filmRequestDTO.getCategory_id());

        Film film = FilmMapper.toEntity(filmRequestDTO, category);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = new User(userDetails.getUsername(), userDetails.getPassword());
        user.setId(userDetails.getId());

        film.setUser(user);

        Film savedFilmed = filmService.save(film);

        return ResponseEntity.ok(FilmMapper.mapToDto(savedFilmed));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> findAll()
    {
        List<FilmResponseDTO> films = filmService.findAll().stream().map(FilmMapper::mapToDto).collect(Collectors.toList());

        Map<String, Object> response = Map.of("status", "success",
                "results", films.size(),
                "data", films);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FilmResponseDTO> getFilm(@PathVariable Long id)
    {
        Optional<Film> result = Optional.ofNullable(filmService.findById(id));

        return result.map(film -> ResponseEntity.ok(FilmMapper.mapToDto(film))).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FilmResponseDTO> updateFilm(@PathVariable Long id, @RequestBody FilmRequestDTO dto)
    {
        Film film = filmService.findById(id);

        if (film == null)
        {
            return ResponseEntity.notFound().build();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if(film.getUser().getId() != userDetails.getId())
        {
            throw new RuntimeException("Can not update other users Film");
        }

        Film updatedFilm = FilmMapper.toEntity(dto, film.getCategory());
        updatedFilm.setId(id);
        updatedFilm.setUser(film.getUser());

        Film savedFilm = filmService.save(updatedFilm);

        return ResponseEntity.ok(FilmMapper.mapToDto(savedFilm));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteFilm(@PathVariable Long id)
    {
        Film film = filmService.findById(id);

        if (film == null)
        {
            return ResponseEntity.notFound().build();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if(film.getUser().getId() != userDetails.getId())
        {
            throw new RuntimeException("Can not delete other users Film");
        }

        filmService.deleteById(id);

        return ResponseEntity.ok("Deleted film id - " + id);
    }
}
