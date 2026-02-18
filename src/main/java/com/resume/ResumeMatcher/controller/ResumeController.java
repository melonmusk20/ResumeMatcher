package com.resume.ResumeMatcher.controller;

import com.resume.ResumeMatcher.model.Resume;
import com.resume.ResumeMatcher.service.ResumeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<Resume> upload(@RequestParam("file") MultipartFile file) throws IOException{

      return ResponseEntity.ok(resumeService.uploadResume(file));
    }

    @PostMapping("/match")
    public ResponseEntity<Integer> match(@RequestParam Long resumeId, @RequestParam String jobDescription){

        Resume resume = resumeService.getResumeById(resumeId);

        int score = resumeService.calculateMatchScore(resume.getExtractedText(), jobDescription);

        return ResponseEntity.ok(score);


    }
}
