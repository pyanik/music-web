package com.pyanik.musicweb.domain.album;

import com.pyanik.musicweb.domain.album.dto.AlbumDto;
import com.pyanik.musicweb.domain.album.dto.InputAlbumDto;
import com.pyanik.musicweb.domain.genre.Genre;
import com.pyanik.musicweb.domain.genre.GenreRepository;
import com.pyanik.musicweb.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    private final GenreRepository genreRepository;

    private final AlbumModelMapper albumModelMapper;

    private final FileStorageService fileStorageService;

    public Page<AlbumDto> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Album> page = albumRepository.findAllByPromotedIsTrue(pageable);
        List<AlbumDto> albumDtos = page
                .stream()
                .map(albumModelMapper::mapAlbumEntityToAlbumDto)
                .toList();
        return new PageImpl<>(albumDtos, pageable, page.getTotalElements());
    }

    public Optional<AlbumDto> findAlbumById(long id) {
        return albumRepository.findById(id).map(albumModelMapper::mapAlbumEntityToAlbumDto);
    }

    public void addAlbum(InputAlbumDto inputAlbumDto) {
        Album album = new Album();
        album.setTitle(inputAlbumDto.getTitle());
        album.setArtistName(inputAlbumDto.getArtistName());
        album.setPromoted(inputAlbumDto.isPromoted());
        album.setReleaseDate(inputAlbumDto.getReleaseDate());
        album.setReview(inputAlbumDto.getReview());
        album.setYoutubeSingleId(inputAlbumDto.getYoutubeSingleId());
        Genre genre = genreRepository.findByNameIgnoreCase(inputAlbumDto.getGenre()).orElseThrow();
        album.setGenre(genre);
        if (inputAlbumDto.getCover() != null) {
            String savedFileName = fileStorageService.saveImage(inputAlbumDto.getCover());
            album.setCover(savedFileName);
        }
        albumRepository.save(album);
    }

    public Page<AlbumDto> findPaginatedAlbumsByGenreName(int pageNo, int pageSize, String genreName) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Album> page = albumRepository.findAllByGenre_NameIgnoreCase(genreName, pageable);
        List<AlbumDto> albumListDto = page
                .stream()
                .map(albumModelMapper::mapAlbumEntityToAlbumDto)
                .toList();
        return new PageImpl<>(albumListDto, pageable, page.getTotalElements());
    }

    @Transactional
    public AlbumDto updateAlbum(Long id, InputAlbumDto inputAlbumDto) {

        Album albumToUpdate = albumRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        albumToUpdate.setTitle(inputAlbumDto.getTitle());
        albumToUpdate.setArtistName(inputAlbumDto.getArtistName());
        albumToUpdate.setArtistName(inputAlbumDto.getArtistName());
        albumToUpdate.setReview(inputAlbumDto.getReview());
        albumToUpdate.setYoutubeSingleId(inputAlbumDto.getYoutubeSingleId());
        albumToUpdate.setReleaseDate(inputAlbumDto.getReleaseDate());
        Genre genre = genreRepository.findByNameIgnoreCase(inputAlbumDto.getGenre()).orElseThrow();
        albumToUpdate.setGenre(genre);
        if (inputAlbumDto.getCover() != null) {
            String savedFileName = fileStorageService.saveImage(inputAlbumDto.getCover());
            albumToUpdate.setCover(savedFileName);
        }
        return albumModelMapper.mapAlbumEntityToAlbumDto(albumToUpdate);
    }

    public void deleteAlbum(Long id) {
        Album albumToDelete = albumRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        albumRepository.delete(albumToDelete);
    }
}
