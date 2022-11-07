package com.pyanik.musicweb.web;

import com.pyanik.musicweb.domain.album.AlbumService;
import com.pyanik.musicweb.domain.album.dto.AlbumDto;
import com.pyanik.musicweb.domain.album.dto.InputAlbumDto;
import com.pyanik.musicweb.domain.genre.GenreService;
import com.pyanik.musicweb.domain.genre.dto.GenreDto;
import com.pyanik.musicweb.domain.rating.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
class AlbumController {

    private final AlbumService albumService;

    private final RatingService ratingService;

    private final GenreService genreService;

    @GetMapping("/album/{id}")
    public String getAlbum(@PathVariable long id,
                           Model model,
                           Authentication authentication) {
        AlbumDto album = albumService.findAlbumById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("album", album);
        if (authentication != null) {
            String currentUserEmail = authentication.getName();
            Integer rating = ratingService.getUserRatingForAlbum(currentUserEmail, id).orElse(0);
            model.addAttribute("userRating", rating);
        }
        return "album";
    }

    @GetMapping("/album/update/{id}")
    public String getAlbumToUpdate(@PathVariable Long id,
                              Model model) {
        AlbumDto album = albumService.findAlbumById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<GenreDto> allGenres = genreService.findAllGenres();
        model.addAttribute("genres", allGenres);
        model.addAttribute("album", album);
        return "album-update-form";
    }

    @PostMapping("album/update/{id}")
    public String updateAlbum(@PathVariable Long id,
                              InputAlbumDto inputAlbumDto) {
        AlbumDto updatedAlbum = albumService.updateAlbum(id, inputAlbumDto);
        return "redirect:/album/" + updatedAlbum.id();
    }

    @GetMapping("album/delete/{id}")
    public String deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return "redirect:/";
    }
}
