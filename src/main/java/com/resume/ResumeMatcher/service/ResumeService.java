package com.resume.ResumeMatcher.service;

import com.resume.ResumeMatcher.model.JobDescription;
import com.resume.ResumeMatcher.model.Resume;
import com.resume.ResumeMatcher.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    private final AiService aiService;


    public Resume uploadResume(MultipartFile file) throws IOException {

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filepath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File destination = new File(filepath);
        file.transferTo(destination);

        String extractedText = extractText(destination);

        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setFilePath(filepath);
        resume.setExtractedText(extractedText);

        return resumeRepository.save(resume);
    }

    public String extractText(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return pdfTextStripper.getText(document);
        }
    }

    public String matchScore(Long resumeId, String jobDescription){

        Resume resume = getResumeById(resumeId);

        return aiService.getMatchAnalysis(resume.getExtractedText(), jobDescription);
    }

    public Resume getResumeById(Long resumeId) {
        return resumeRepository.findById(resumeId).orElseThrow(() -> new RuntimeException("Resume not found"));
    }

    public String optimizeResume(Long resumeId, String jobDescription) {
        Resume resume = getResumeById(resumeId);

        String resumeText = resume.getExtractedText();

        return aiService.optimizeResume(resumeText, jobDescription);

    }
}
