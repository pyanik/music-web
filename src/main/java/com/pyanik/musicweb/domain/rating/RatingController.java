package com.pyanik.musicweb.domain.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/rate-album")
    public String addAlbumRating(@RequestParam long albumId,
                                 @RequestParam int rating,
                                 @RequestHeader String referer,
                                 Authentication authentication) {
        String currentUserEmail = authentication.getName();
        ratingService.addOrUpdateRating(currentUserEmail, albumId, rating);
        return "redirect:" + referer;
    }
}
