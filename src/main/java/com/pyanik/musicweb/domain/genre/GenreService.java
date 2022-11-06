package com.pyanik.musicweb.domain.genre;

import com.pyanik.musicweb.domain.genre.dto.GenreDto;
import com.pyanik.musicweb.domain.genre.dto.InputGenreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    private final GenreModelMapper genreModelMapper;

    public Optional<GenreDto> findGenreByName(String name) {
        return genreRepository.findByNameIgnoreCase(name)
                .map(genreModelMapper::mapGenreEntityToGenreDto);
    }

    public List<GenreDto> findAllGenres() {
        return genreRepository.findAll().stream()
                .map(genreModelMapper::mapGenreEntityToGenreDto)
                .toList();
    }

    @Transactional
    public void addGenre(InputGenreDto genre) {
        Genre genreToSave = genreModelMapper.mapInputGenreDtoToGenreEntity(genre);
        genreRepository.save(genreToSave);
    }

    @Transactional
    public GenreDto updateGenre(String genreName, InputGenreDto inputGenreDto) {
        Genre genreToUpdate = genreRepository.findByNameIgnoreCase(genreName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        genreToUpdate.setName(inputGenreDto.name());
        genreToUpdate.setDescription(inputGenreDto.description());

        return genreModelMapper.mapGenreEntityToGenreDto(genreToUpdate);
    }
}
