package org.neon.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.neon.entity.Release;
import org.springframework.data.jpa.domain.Specification;

public interface ReleaseSpecification extends Specification<Release> {
    Predicate toPredicate(Root<Release> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}

