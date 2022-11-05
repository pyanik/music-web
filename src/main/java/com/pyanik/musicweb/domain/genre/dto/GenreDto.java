package com.pyanik.musicweb.domain.genre.dto;

public record GenreDto(Long id, String name, String description) {
    public GenreDto() {
        this(null, null, null);
    }
}
