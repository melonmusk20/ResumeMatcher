package com.resume.ResumeMatcher.repository;

import com.resume.ResumeMatcher.model.JobDescription;
import com.resume.ResumeMatcher.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDescriptionRepo extends JpaRepository<JobDescription, Long> {
}
