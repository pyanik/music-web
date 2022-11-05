package com.pyanik.musicweb.domain.genre;

import com.pyanik.musicweb.domain.genre.dto.GenreDto;
import com.pyanik.musicweb.domain.genre.dto.InputGenreDto;
import com.pyanik.musicweb.domain.user.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreModelMapper {
    GenreDto mapGenreEntityToGenreDto(Genre genre);

    @Mapping(target = "id", ignore = true)
    Genre mapInputGenreDtoToGenreEntity(InputGenreDto inputGenreDto);
}