package com.resume.ResumeMatcher.service;

import com.resume.ResumeMatcher.repository.JobDescriptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class JobDescriptionService {

    private final JobDescriptionRepo jobDescriptionRepo;


    public JobDescriptionService(JobDescriptionRepo jobDescriptionRepo) {
        this.jobDescriptionRepo = jobDescriptionRepo;
    }
}
