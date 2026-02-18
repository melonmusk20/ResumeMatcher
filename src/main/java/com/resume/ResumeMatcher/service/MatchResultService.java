package com.resume.ResumeMatcher.service;

import com.resume.ResumeMatcher.repository.MatchResultRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class MatchResultService {

    private final MatchResultRepo matchResultRepo;


    public MatchResultService(MatchResultRepo matchResultRepo) {
        this.matchResultRepo = matchResultRepo;
    }
}
