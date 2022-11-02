package com.pyanik.musicweb.domain.album;

import com.pyanik.musicweb.domain.album.dto.AlbumDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlbumModelMapper {
    @Mapping(target="genre", source="album.genre.name")
    @Mapping(target = "ratingCount", expression  = "java(album.getRatings() != null ? album.getRatings().size() : 0)")
    @Mapping(target = "avgRating", expression = "java(album.getRatings().stream().map(rating -> rating.getRating()).mapToDouble(val -> val).average().orElse(0))")
    AlbumDto mapAlbumEntityToAlbumDto(Album album);
}
