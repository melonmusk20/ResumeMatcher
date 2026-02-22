package com.resume.ResumeMatcher.service;


import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private final Client client;

    public AiService(@Value("${gemini.api.key}") String apiKey){
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }


    public String optimizeResume(String resumeText, String jobDescription) {

        try {
            String prompt = """
                            You are an expert ATS Resume Optimizer.
                    
                            Given the resume and job description below,
                            rewrite the resume so that it aligns better with the job description.
                            Keep it professional and ATS friendly.
                    
                    Resume:
                    """ + resumeText + """
                    
                    job Description:
                    """ + jobDescription;


            Content content = Content.fromParts(
                    com.google.genai.types.Part.fromText(prompt)
            );

            GenerateContentConfig config = GenerateContentConfig.builder()
                    .temperature(0.5f)
                    .build();

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    content, config
            );
            return response.text();
        } catch (Exception e) {
            throw new RuntimeException("Error while calling Gemini API", e);
        }
    }

    public String getMatchAnalysis(String resumeText, String jobDescription){

        try {
            String prompt = """
                    Compare the resume and job description.
                    
                                    Provide:
                                    1. Match percentage (numeric)
                                    2. Missing skills
                                    3. Suggestions to improve
                    
                    Resume: 
                    """ + resumeText + """
                    
                    Job Description:
                    """ + jobDescription;


            Content content = Content.fromParts(
                    com.google.genai.types.Part.fromText(prompt)
            );

            GenerateContentConfig config = GenerateContentConfig.builder()
                    .temperature(0.5f)
                    .build();

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    content, config
            );
            return response.text();
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

}
