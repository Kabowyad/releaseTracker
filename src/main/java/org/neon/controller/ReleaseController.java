package org.neon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neon.controller.exceptions.ResourceNotFoundException;
import org.neon.entity.Release;
import org.neon.entity.enums.ReleaseStatus;
import org.neon.models.request.CreateReleaseRequest;
import org.neon.models.request.UpdateReleaseRequest;
import org.neon.models.response.ReleaseResponse;
import org.neon.repository.ReleaseFilter;
import org.neon.service.ReleaseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/releases")
public class ReleaseController {

    private final ReleaseService service;

    /**
     * Creates a new release based on the given request data and returns a DTO representation of the created release.
     *
     * @param request the request data for the new release
     * @return a DTO representation of the created release
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReleaseResponse createRelease(@RequestBody @Valid CreateReleaseRequest request) {

        Release createdRelease = service.createRelease(request);
        log.info("Created release with id {} and status {}", createdRelease.getId(), createdRelease.getStatus());
        return mapReleaseToResponse(createdRelease);
    }

    /**
     * Retrieves a list of releases based on the given filters and returns them as DTO representations.
     *
     * @param status    the status to filter releases by, or null to not filter by status
     * @param name      the name to filter releases by, or null to not filter by name
     * @param startDate the earliest release date to filter releases by, or null to not filter by start date
     * @param endDate   the latest release date to filter releases by, or null to not filter by end date
     * @return a list of DTO representations of the filtered releases
     */
    @GetMapping
    public List<ReleaseResponse> getFilteredReleases(
            @RequestParam(name = "status", required = false) ReleaseStatus status,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "end_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return service.getFilteredReleases(new ReleaseFilter(status, name, startDate, endDate))
                .stream().map(this::mapReleaseToResponse).collect(Collectors.toList());
    }

    /**
     * Retrieves a release with the given ID and returns it.
     *
     * @param id the ID of the release to retrieve
     * @return the release with the given ID
     * @throws ResourceNotFoundException if no release with the given ID is found
     */
    @GetMapping("/{id}")
    public Release getReleaseById(@PathVariable Long id) {

        return service.findById(id);
    }

    /**
     * Deletes the release with the given ID.
     *
     * @param id the ID of the release to delete
     */
    @DeleteMapping("/{id}")
    public void deleteRelease(@PathVariable Long id) {

        service.deleteRelease(id);
        log.info("Deleted release with id {}", id);
    }

    /**
     * Updates the release with the given ID based on the provided request data and returns a DTO representation of the updated release.
     *
     * @param id                the ID of the release to update
     * @param releaseRequestDto the request data for the updated release
     * @return a DTO representation of the updated release
     * @throws ResourceNotFoundException    if no release with the given ID is found
     */
    @PutMapping("/{id}")
    public ReleaseResponse updateRelease(
            @PathVariable("id") Long id,
            @RequestBody UpdateReleaseRequest releaseRequestDto) {

        ReleaseResponse response =  mapReleaseToResponse(service.updateRelease(id, releaseRequestDto));
        log.info("Updated release with id {}", id);
        return response;
    }

    private ReleaseResponse mapReleaseToResponse(Release release) {

        return new ReleaseResponse(release.getId(), release.getName(), release.getDescription(),
                release.getStatus(), release.getReleaseDate(), release.getCreatedAt(),
                release.getLastUpdatedAt());
    }
}
