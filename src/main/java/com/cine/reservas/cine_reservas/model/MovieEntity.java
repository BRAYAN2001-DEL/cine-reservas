package com.cine.reservas.cine_reservas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class MovieEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovieGenreEnum genre;

    @Column(nullable = false)
    private short allowedAge;

    @Column(nullable = false)
    private short lengthMinutes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MovieGenreEnum getGenre() {
        return genre;
    }

    public void setGenre(MovieGenreEnum genre) {
        this.genre = genre;
    }

    public short getAllowedAge() {
        return allowedAge;
    }

    public void setAllowedAge(short allowedAge) {
        this.allowedAge = allowedAge;
    }

    public short getLengthMinutes() {
        return lengthMinutes;
    }

    public void setLengthMinutes(short lengthMinutes) {
        this.lengthMinutes = lengthMinutes;
    }
}