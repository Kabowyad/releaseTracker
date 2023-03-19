package org.neon.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.neon.entity.Release;
import org.neon.repository.ReleaseFilter;

import java.util.ArrayList;
import java.util.List;

public class ReleaseSpecificationImpl implements ReleaseSpecification {

    private final ReleaseFilter filter;

    public ReleaseSpecificationImpl(ReleaseFilter filter) {

        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Release> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();

        // Add predicates based on the filter
        if (filter.status() != null) {
            predicates.add(builder.equal(root.get("status"), filter.status()));
        }
        if (filter.name() != null) {
            predicates.add(builder.like(builder.lower(root.get("name")),
                    "%" + filter.name().toLowerCase() + "%"));
        }
        if (filter.startDate() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("releaseDate"), filter.startDate()));
        }
        if (filter.endDate() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("releaseDate"), filter.endDate()));
        }

        // Combine all predicates using AND operator
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
