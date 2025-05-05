package com.kitm.atsiskaitymas_2025_05_05.Controllers;

import com.kitm.atsiskaitymas_2025_05_05.dto.CommentRequestDTO;
import com.kitm.atsiskaitymas_2025_05_05.dto.CommentResponseDTO;
import com.kitm.atsiskaitymas_2025_05_05.entity.Comment;
import com.kitm.atsiskaitymas_2025_05_05.entity.Film;
import com.kitm.atsiskaitymas_2025_05_05.entity.User;
import com.kitm.atsiskaitymas_2025_05_05.mapper.CommentMapper;
import com.kitm.atsiskaitymas_2025_05_05.security.services.UserDetailsImpl;
import com.kitm.atsiskaitymas_2025_05_05.services.CommentService;
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
@RequestMapping("/api/v1/comment")
public class CommentController {

    private CommentService commentService;
    private FilmService filmService;

    @Autowired
    public CommentController(CommentService commentService, FilmService filmService)
    {
        this.commentService = commentService;
        this.filmService = filmService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentResponseDTO> addComment(@Valid @RequestBody CommentRequestDTO dto)
    {
        Film film = filmService.findById(dto.getFilm_id());

        Comment comment = CommentMapper.toEntity(dto, film);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = new User(userDetails.getUsername(), userDetails.getPassword());
        user.setId(userDetails.getId());

        comment.setUser(user);

        Comment savedComment = commentService.save(comment);

        return ResponseEntity.ok(CommentMapper.toResponse(savedComment));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> findAll()
    {
        List<CommentResponseDTO> comments = commentService.findAll().stream().map(CommentMapper::toResponse).collect(Collectors.toList());

        Map<String, Object> response = Map.of("status", "success",
                "results", comments.size(),
                "data", comments);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentResponseDTO> getComment(@PathVariable Long id)
    {
        Optional<Comment> result = Optional.ofNullable(commentService.findById(id));

        return result.map(comment -> ResponseEntity.ok(CommentMapper.toResponse(comment))).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteComment(@PathVariable Long id)
    {
        Comment comment = commentService.findById(id);

        if (comment == null)
        {
            return ResponseEntity.notFound().build();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (comment.getUser().getId() != userDetails.getId())
        {
            throw new RuntimeException("Can not delete other users comment");
        }

        commentService.deleteById(id);

        return ResponseEntity.ok("Deleted Comment id - " + id);
    }
}
