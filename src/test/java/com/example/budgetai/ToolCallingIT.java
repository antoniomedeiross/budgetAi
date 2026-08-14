 package com.example.budgetai;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GROQ_API_KEY", matches = ".+")
public class ToolCallingIT {

    @Autowired
    OpenAiChatModel openAiChatModel;


    static class MathTools {

        @Tool(description = "Soma dois números inteiros")
        public int sum(int a, int b) {
            return a + b;
        }

        @Tool(description = "Subtrai dois números inteiros")
        public int diff(int a, int b) {
            return a - b;
        }

    }

    @Test
    void should_excuteSum_when_prompted() {
        var chatClient = ChatClient.builder(openAiChatModel)
                .defaultTools(new MathTools()).defaultSystem("Você é um Matemático").build();

        var response = chatClient.prompt("Some 10 + 20, depois subtraia 30 do resultado obtido. Exiba apenas o resultado final, sem explicacoes.")
                .call()
                .content();

        assertThat(response).contains("0");

        System.out.println(response);
    }
}
