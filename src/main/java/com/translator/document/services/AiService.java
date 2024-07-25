package com.translator.document.services;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    public String chatResponse(String content) {

        // Validates the api key
        OpenAiApi openAiApi = new OpenAiApi(System.getenv("OPENAI_API_KEY"));

        // Sets chat model options
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                .withModel("gpt-3.5-turbo")
                .withTemperature(0.4f)
                .withMaxTokens(200)
                .build();

        OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi, openAiChatOptions);

        // Provides a text for AI to identify the location
        ChatResponse chatResponse = chatModel.call(
                new Prompt(
                        "Informe o idioma do texto a seguir no formato 'idioma-País', " +
                                "ex: \"pt-BR\": " + content
                )
        );
        return chatResponse.getResult().getOutput().getContent();
    }



}
