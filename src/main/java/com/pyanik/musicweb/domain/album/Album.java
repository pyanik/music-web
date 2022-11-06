package com.pyanik.musicweb.domain.album;

import com.pyanik.musicweb.domain.genre.Genre;
import com.pyanik.musicweb.domain.rating.Rating;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String artistName;

    private String review;

    private String youtubeSingleId;

    private LocalDate releaseDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @OneToMany(mappedBy = "album")
    private Set<Rating> ratings = new HashSet<>();

    private boolean promoted;

    private String cover;
}
