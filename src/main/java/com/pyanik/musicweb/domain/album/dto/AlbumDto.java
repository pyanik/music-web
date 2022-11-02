package com.pyanik.musicweb.domain.album.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record AlbumDto(Long id,
                       String title,
                       String artistName,
                       String review,
                       String youtubeSingleId,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate releaseDate,
                       String genre,
                       boolean promoted,
                       String cover,
                       double avgRating,
                       int ratingCount) {
}
