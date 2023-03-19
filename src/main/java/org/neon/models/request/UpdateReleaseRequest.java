package org.neon.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.neon.entity.enums.ReleaseStatus;

public record UpdateReleaseRequest(
        @JsonProperty(value = "name")
        String name,
        @JsonProperty(value = "description")
        String description,
        @JsonProperty(value = "status")
        ReleaseStatus status
) {

}
