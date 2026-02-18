package com.resume.ResumeMatcher.service;

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

    public int calculateMatchScore(String resumeText, String jobDescription){

        String[] jdWords = jobDescription.toLowerCase().split("\\W+");
        resumeText = resumeText.toLowerCase();

        int matchCount = 0;

        for(String word: jdWords){
            if(resumeText.contains(word)){
                matchCount++;
            }
        }

        if(jdWords.length == 0){
            return 0;
        }

        return (matchCount * 100) / jdWords.length;
    }

    public Resume getResumeById(Long resumeId) {
        return resumeRepository.findById(resumeId).orElseThrow(() -> new RuntimeException("Resume not found"));
    }
}
