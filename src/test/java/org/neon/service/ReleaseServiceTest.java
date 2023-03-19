package org.neon.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.neon.AbstractBaseTest;
import org.neon.controller.exceptions.ResourceNotFoundException;
import org.neon.entity.Release;
import org.neon.entity.enums.ReleaseStatus;
import org.neon.models.request.CreateReleaseRequest;
import org.neon.models.request.UpdateReleaseRequest;
import org.neon.repository.ReleaseFilter;
import org.neon.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReleaseServiceTest extends AbstractBaseTest {

    @Autowired
    private ReleaseService service;

    @Autowired
    private ReleaseRepository repository;

    @BeforeEach
    public void setUp() {

        repository.deleteAll();
    }

    @Test
    @DisplayName("Create release - Success")
    public void testCreateRelease() {

        CreateReleaseRequest request =
                new CreateReleaseRequest("New Release", "This is a new release",
                        ReleaseStatus.DONE, LocalDate.of(2023, 3, 31));
        Release release = service.createRelease(request);
        assertNotNull(release.getId());
        assertEquals(request.name(), release.getName());
        assertEquals(request.description(), release.getDescription());
        assertEquals(request.status(), release.getStatus());
        assertEquals(request.releaseDate(), release.getReleaseDate());
    }

    @Test
    @DisplayName("Get filtered releases without filter - Success")
    void testGetFilteredReleasesNoFilters() {

        Release release =
                new Release("name", "description", ReleaseStatus.DONE, LocalDate.of(2007, 12, 3));
        repository.save(release);

        List<Release> allReleases = repository.findAll();
        List<Release> filteredReleases =
                service.getFilteredReleases(new ReleaseFilter(null, null, null, null));

        assertEquals(allReleases.size(), filteredReleases.size());
        assertTrue(allReleases.containsAll(filteredReleases));
        assertTrue(filteredReleases.contains(release));
    }

    @Test
    @DisplayName("Get filtered release by status - Success")
    void testGetFilteredReleasesByStatus() {

        Release release =
                new Release("name", "description", ReleaseStatus.DONE, LocalDate.of(2007, 3, 31));
        repository.save(release);

        ReleaseFilter filter = new ReleaseFilter(ReleaseStatus.DONE, null, null, null);

        List<Release> filteredReleases = service.getFilteredReleases(filter);

        for (Release rl : filteredReleases) {
            assertEquals(ReleaseStatus.DONE, rl.getStatus());
        }
    }

    @Test
    @DisplayName("Get filtered release by date - Success")
    void testGetFilteredReleasesByStartDate() {

        Release release1 =
                new Release("name", "description", ReleaseStatus.DONE, LocalDate.of(2007, 3, 31));
        repository.save(release1);

        Release release2 =
                new Release("name", "description", ReleaseStatus.DONE, LocalDate.of(2010, 3, 31));
        repository.save(release2);

        ReleaseFilter filter = new ReleaseFilter(null, null, LocalDate.of(2009, 1, 1), null);

        List<Release> filteredReleases = service.getFilteredReleases(filter);

        assertEquals(1, filteredReleases.size());
    }

    @Test
    @DisplayName("Get release by id - Success")
    void testGetReleaseById() {
        // Create a new release and save it to the database
        Release release =
                new Release("Release 1", "Description", ReleaseStatus.DONE, LocalDate.now());
        release = repository.save(release);

        // Get the release by its ID
        Release result = service.findById(release.getId());

        // Ensure that the result is not empty and that it matches the original release
        assertEquals(release, result);
    }

    @Test
    @DisplayName("Get release by not found - Success")
    void testGetReleaseByIdNotFound() {
        // Attempt to get a release that doesn't exist

        assertThrows(ResourceNotFoundException.class, () -> service.findById(1234L));
    }

    @Test
    @DisplayName("Delete release - Success")
    void testDeleteRelease() {
        // Create a new release
        Release release = new Release("Test Release", "This is a test release", ReleaseStatus.DONE,
                LocalDate.now());
        Release savedRelease = repository.save(release);

        // Verify that the release was saved successfully
        assertNotNull(savedRelease.getId());

        // Delete the release
        service.deleteRelease(savedRelease.getId());

        // Verify that the release was deleted successfully
        Optional<Release> deletedRelease = repository.findById(savedRelease.getId());
        assertFalse(deletedRelease.isPresent());
    }

    @Test
    @DisplayName("Update release - Success")
    void testUpdateReleaseWithNewNameAndDescription() {

        Release release = new Release("Test Release", "This is a test release", ReleaseStatus.DONE,
                LocalDate.now());
        Release savedRelease = repository.save(release);

        UpdateReleaseRequest updateRequest =
                new UpdateReleaseRequest("New Release Name", "New Release Description", null);
        Release updatedRelease = service.updateRelease(savedRelease.getId(), updateRequest);

        assertEquals("New Release Name", updatedRelease.getName());
        assertEquals("New Release Description", updatedRelease.getDescription());
        assertEquals(ReleaseStatus.DONE, updatedRelease.getStatus());
        assertNotNull(updatedRelease.getLastUpdatedAt());
    }

    @Test
    @DisplayName("Exception updating with invalid id - Success")
    void testUpdateReleaseWithInvalidId() {
        // Arrange
        Long invalidId = 12345L;
        UpdateReleaseRequest updateRequest = new UpdateReleaseRequest("name", null, null);
        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> service.updateRelease(invalidId, updateRequest));
    }

}
