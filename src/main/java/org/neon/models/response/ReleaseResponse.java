package org.neon.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.neon.entity.enums.ReleaseStatus;

import java.time.Instant;
import java.time.LocalDate;

public record ReleaseResponse(
        @JsonProperty(value = "id")
        Long id,
        @JsonProperty(value = "name")
        String name,
        @JsonProperty(value = "description")
        String description,
        @JsonProperty(value = "status")
        ReleaseStatus status,
        @JsonProperty(value = "release_date")
        LocalDate releaseDate,
        @JsonProperty(value = "created_at")
        Instant createdAt,
        @JsonProperty(value = "last_updated_at")
        Instant lastUpdatedAt
) {

}
