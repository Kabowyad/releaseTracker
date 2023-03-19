package org.neon.service;

import lombok.RequiredArgsConstructor;
import org.neon.controller.exceptions.ResourceNotFoundException;
import org.neon.entity.Release;
import org.neon.models.request.CreateReleaseRequest;
import org.neon.models.request.UpdateReleaseRequest;
import org.neon.repository.ReleaseFilter;
import org.neon.repository.ReleaseRepository;
import org.neon.repository.specification.ReleaseSpecificationImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNullElse;

@Service
@RequiredArgsConstructor
public class ReleaseService {

    private final ReleaseRepository repository;

    /**
     * Creates a new release with the given information and saves it to the repository.
     *
     * @param request the {@link CreateReleaseRequest} object containing the information to create the release.
     * @return the created {@link Release} object.
     */
    public Release createRelease(CreateReleaseRequest request) {

        return repository.save(new Release(request.name(), request.description(), request.status(),
                request.releaseDate()));
    }

    /**
     * Retrieves a list of releases based on the given filter criteria.
     *
     * @param filter the {@link ReleaseFilter} object containing the filter criteria.
     * @return a {@link List} of {@link Release} objects that match the filter criteria.
     */
    public List<Release> getFilteredReleases(ReleaseFilter filter) {

        ReleaseSpecificationImpl specification = new ReleaseSpecificationImpl(filter);
        return repository.findAll(specification);
    }

    /**
     * Retrieves a specific release by its ID.
     *
     * @param id the ID of the release to retrieve.
     * @return the {@link Release} object with the specified ID.
     * @throws ResourceNotFoundException if no release with the specified ID is found in the repository.
     */
    public Release findById(Long id) {

        return repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Deletes a release with the specified ID from the repository.
     *
     * @param id the ID of the release to delete.
     */
    public void deleteRelease(Long id) {

        repository.deleteById(id);
    }


    /**
     * Updates a release with the specified ID based on the information provided in the given {@link UpdateReleaseRequest}.
     *
     * @param id the ID of the release to update.
     * @param dto the {@link UpdateReleaseRequest} object containing the updated information.
     * @return the updated {@link Release} object.
     * @throws ResourceNotFoundException if no release with the specified ID is found in the repository.
     */
    public Release updateRelease(Long id, UpdateReleaseRequest dto) {

        Release release = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return repository.save(release
                .setDescription(requireNonNullElse(dto.description(), release.getDescription()))
                .setName(requireNonNullElse(dto.name(), release.getName()))
                .setStatus(requireNonNullElse(dto.status(), release.getStatus())));

    }
}
