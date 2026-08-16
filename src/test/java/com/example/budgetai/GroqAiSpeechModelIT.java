package com.example.budgetai;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GROQ_API_KEY", matches = ".+")
public class GroqAiSpeechModelIT {
    @Autowired
    OpenAiAudioSpeechModel openAiAudioSpeechModel;

    @Test
    public void should_produceAudio_when_textIsProvided() throws IOException {
        var response = openAiAudioSpeechModel.call("[professionally] O valor total do servico ficou em 150 reais, posso confirmar que o pagamento foi realizado com sucesso?");

        assertThat(response).hasSizeGreaterThan(1025);

        var tempFile = Files.createTempFile("AUDIO_", ".wav");

        Files.write(tempFile, response);
        System.out.println(tempFile.toAbsolutePath());
    }
}
