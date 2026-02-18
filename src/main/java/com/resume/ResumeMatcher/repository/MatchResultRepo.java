package com.resume.ResumeMatcher.repository;

import com.resume.ResumeMatcher.model.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchResultRepo extends JpaRepository<MatchResult, Integer> {
}
