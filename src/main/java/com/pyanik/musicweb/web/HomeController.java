package com.pyanik.musicweb.web;

import com.pyanik.musicweb.domain.album.AlbumService;
import com.pyanik.musicweb.domain.album.dto.AlbumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
class HomeController {

    private final AlbumService albumService;

    @GetMapping("/")
    public String home(Model model) {
        return findPaginated(1, model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;

        Page<AlbumDto> page = albumService.findPaginated(pageNo, pageSize);
        List<AlbumDto> albums = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("heading", "Promoted albums");
        model.addAttribute("description", "Music albums promoted by our team");
        model.addAttribute("page", page);

        int totalPages = page.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("albums", albums);
        return "album-listing";
    }
}
