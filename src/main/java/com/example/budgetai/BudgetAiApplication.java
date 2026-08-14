package com.example.budgetai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.beans.BeanProperty;

@SpringBootApplication
public class BudgetAiApplication {

    @Bean
    ChatClient chatChatClient(ChatClient.Builder builder) {
        return  builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(BudgetAiApplication.class, args);
    }

}
