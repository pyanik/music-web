package com.pyanik.musicweb.domain.rating;

import com.pyanik.musicweb.domain.album.Album;
import com.pyanik.musicweb.domain.album.AlbumRepository;
import com.pyanik.musicweb.domain.user.User;
import com.pyanik.musicweb.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    private final UserRepository userRepository;

    private final AlbumRepository albumRepository;

    public void addOrUpdateRating(String userEmail, long albumId, int rating) {
        Rating ratingToSaveOrUpdate = ratingRepository.findByUser_EmailAndAlbum_Id(userEmail, albumId)
                .orElseGet(Rating::new);
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Album album = albumRepository.findById(albumId).orElseThrow();
        ratingToSaveOrUpdate.setUser(user);
        ratingToSaveOrUpdate.setAlbum(album);
        ratingToSaveOrUpdate.setRating(rating);
        ratingRepository.save(ratingToSaveOrUpdate);
    }

    public Optional<Integer> getUserRatingForAlbum(String userEmail, long albumId) {
        return ratingRepository.findByUser_EmailAndAlbum_Id(userEmail, albumId)
                .map(Rating::getRating);
    }
}