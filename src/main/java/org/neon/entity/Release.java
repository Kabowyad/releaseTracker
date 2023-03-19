package org.neon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.neon.entity.enums.ReleaseStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@ToString
@Entity
@Table(name = "release")
@RequiredArgsConstructor
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @Column(name = "description",
            nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReleaseStatus status;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(name = "created_at",
            nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at",
            nullable = false)
    @UpdateTimestamp
    private Instant lastUpdatedAt;

    public Release(String name, String description, ReleaseStatus status, LocalDate releaseDate) {

        this.name = name;
        this.description = description;
        this.status = status;
        this.releaseDate = releaseDate;
    }

    public Release setName(String name) {

        this.name = name;
        return this;
    }

    public Release setDescription(String description) {

        this.description = description;
        return this;
    }

    public Release setStatus(ReleaseStatus status) {

        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Release release = (Release) o;
        return getId() != null && Objects.equals(getId(), release.getId());
    }

    @Override
    public int hashCode() {

        return getClass().hashCode();
    }
}
