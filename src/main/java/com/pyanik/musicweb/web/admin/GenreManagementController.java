package com.pyanik.musicweb.web.admin;

import com.pyanik.musicweb.domain.genre.GenreService;
import com.pyanik.musicweb.domain.genre.dto.GenreDto;
import com.pyanik.musicweb.domain.genre.dto.InputGenreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
class GenreManagementController {

    private final GenreService genreService;

    @GetMapping("/admin/add-genre")
    public String addGenreForm(Model model) {
        GenreDto genre = new GenreDto();
        model.addAttribute("genre", genre);
        return "admin/genre-add-form";
    }

    @PostMapping("admin/add-genre")
    public String addGenre(InputGenreDto inputGenreDto, RedirectAttributes redirectAttributes) {
        genreService.addGenre(inputGenreDto);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Genre %s has been saved".formatted(inputGenreDto.name())
        );
        return "redirect:/admin";
    }
}
