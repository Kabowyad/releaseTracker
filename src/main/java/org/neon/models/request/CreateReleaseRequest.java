package org.neon.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.neon.entity.enums.ReleaseStatus;

import java.time.LocalDate;

public record CreateReleaseRequest(
        @NotBlank(message = "Name is mandatory")
        @JsonProperty(value = "name")
        String name,

        @NotBlank(message = "Description is mandatory")
        @JsonProperty(value = "description")
        String description,

        @NotNull(message = "Status is mandatory")
        @JsonProperty(value = "status")
        ReleaseStatus status,

        @NotNull(message = "Release date is mandatory")
        @JsonProperty(value = "release_date")
        LocalDate releaseDate) { }
