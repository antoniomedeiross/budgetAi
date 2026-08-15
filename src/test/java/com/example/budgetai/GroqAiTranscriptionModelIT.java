package com.example.budgetai;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GROQ_API_KEY", matches = ".+")
public class GroqAiTranscriptionModelIT {
    @Autowired
    OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;

    @ParameterizedTest
    @CsvSource({
            "audio-1.mp3, 150 reais",
            "audio-2.mp3, 42,90 reais",
            "audio-3.mp3, 380,50 reais",
            "audio-4.mp3, 380,50 reais",
            "audio-5.mp3, 89,90 reais",
    })
    public void should_containExpectedKeyWords_when_audioFileAreProcessed(String fileName, String expectedKeywords) {
        var recording = new ClassPathResource("audio/" + fileName);

        var response = openAiAudioTranscriptionModel.transcribe(recording);

        System.out.println("Transcription:");
        System.out.println(response);

        assertThat(response).contains(expectedKeywords);
    }
}
