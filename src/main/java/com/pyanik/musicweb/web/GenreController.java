package com.pyanik.musicweb.web;

import com.pyanik.musicweb.domain.album.AlbumService;
import com.pyanik.musicweb.domain.album.dto.AlbumDto;
import com.pyanik.musicweb.domain.genre.GenreService;
import com.pyanik.musicweb.domain.genre.dto.GenreDto;
import com.pyanik.musicweb.domain.genre.dto.InputGenreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
class GenreController {

    private final GenreService genreService;

    private final AlbumService albumService;

    @GetMapping("/genre/{name}")
    public String getGenre(@PathVariable(value = "name") String genreName, Model model) {
        return getGenrePaginated(genreName, 1, model);
    }

    @GetMapping("/genre/{name}/page/{pageNo}")
    public String getGenrePaginated(@PathVariable(value = "name") String genreName, @PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 3;

        GenreDto genre = genreService.findGenreByName(genreName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Page<AlbumDto> page = albumService.findPaginatedAlbumsByGenreName(pageNo, pageSize, genreName);
        List<AlbumDto> albums = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("genreName", genreName);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("heading", genre.name());
        model.addAttribute("description", genre.description());
        model.addAttribute("page", page);
        model.addAttribute("albums", albums);

        int totalPages = page.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "genre-album-listing";
    }

    @GetMapping("/genre")
    public String getGenreList(Model model) {
        List<GenreDto> genres = genreService.findAllGenres();
        model.addAttribute("genres", genres);
        return "genre-listing";
    }

    @GetMapping("/genre/update/{name}")
    public String getGenreToUpdate(@PathVariable(value = "name") String genreName,
                                   Model model) {
        GenreDto genreToUpdate = genreService.findGenreByName(genreName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("genre", genreToUpdate);
        return "genre-update-form";
    }

    @PostMapping("/genre/update/{name}")
    public String updateGenre(@PathVariable(value = "name") String genreName,
                           InputGenreDto inputGenreDto) {
        GenreDto updatedGenre = genreService.updateGenre(genreName, inputGenreDto);
        return "redirect:/genre";
    }
}