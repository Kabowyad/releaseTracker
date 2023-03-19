package org.neon.repository;

import org.neon.entity.enums.ReleaseStatus;

import java.time.LocalDate;

public record ReleaseFilter(ReleaseStatus status, String name, LocalDate startDate, LocalDate endDate) {

}
