package org.neon.repository;

import org.neon.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReleaseRepository
        extends JpaRepository<Release, Long>, JpaSpecificationExecutor<Release> {

}
