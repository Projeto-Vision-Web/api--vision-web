package com.visionweb.app_vision_web.application.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visionweb.app_vision_web.application.dto.NlpResultado;
import com.visionweb.app_vision_web.domain.core.entities.Enum.Sentimento;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class NlpService {

    private final WebClient cerebrasWebClient;
    private final ObjectMapper objectMapper;
    private final String model;

    public NlpService(WebClient cerebrasWebClient,
                      ObjectMapper objectMapper,
                      @Value("${ceresbras.model}") String model) {
        this.cerebrasWebClient = cerebrasWebClient;
        this.objectMapper = objectMapper;
        this.model = model;
    }

    public NlpResultado analisarTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            return new NlpResultado(Sentimento.NEUTRO, 0.0, "GERAL", 0.0);
        }

        try {
            // Monta o corpo da requisição para o chat completions
            Map<String, Object> body = Map.of(
                    "model", model,
                    "messages", new Object[]{
                            Map.of("role", "system", "content",
                                    """
                                    Você é um serviço de análise de feedback de colaboradores.
                                    Leia o texto e responda APENAS com um JSON com o seguinte formato:
                                    {
                                      "sentimento": "POSITIVO|NEGATIVO|NEUTRO",
                                      "score": número entre -1 e 1,
                                      "cluster": "string com o tema principal",
                                      "risco_turnover": número entre 0 e 1
                                    }
                                    Não inclua nenhum outro texto fora do JSON.
                                    """),
                            Map.of("role", "user", "content", texto)
                    },
                    // Modo JSON do Cerebras: garante que a resposta seja um JSON ou erro estruturado
                    "response_format", Map.of("type", "json_object"),
                    "max_completion_tokens", 256
            );

            String response = cerebrasWebClient.post()
                    .uri("/chat/completions")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(ex -> {
                        // Em caso de erro, você pode logar e retornar algo neutro
                        ex.printStackTrace();
                        return Mono.just("");
                    })
                    .block();

            if (response == null || response.isBlank()) {
                return new NlpResultado(Sentimento.NEUTRO, 0.0, "GERAL", 0.0);
            }

            // A resposta segue o padrão OpenAI-like:
            // { choices: [ { message: { content: "{...json aqui...}" } } ], ... }
            JsonNode root = objectMapper.readTree(response);
            JsonNode contentNode = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content");

            if (contentNode.isMissingNode() || contentNode.isNull()) {
                return new NlpResultado(Sentimento.NEUTRO, 0.0, "GERAL", 0.0);
            }

            // content deve ser um JSON em string ou já objeto JSON
            JsonNode jsonSentimento;

            if (contentNode.isTextual()) {
                // às vezes vem como string contendo JSON
                jsonSentimento = objectMapper.readTree(contentNode.asText());
            } else {
                jsonSentimento = contentNode;
            }

            String sentimentoStr = jsonSentimento.path("sentimento").asText("NEUTRO");
            double score = jsonSentimento.path("score").asDouble(0.0);
            String cluster = jsonSentimento.path("cluster").asText("GERAL");
            double riscoTurnover = jsonSentimento.path("risco_turnover").asDouble(0.0);

            Sentimento sentimento;
            try {
                sentimento = Sentimento.valueOf(sentimentoStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                sentimento = Sentimento.NEUTRO;
            }

            return new NlpResultado(sentimento, score, cluster, riscoTurnover);

        } catch (Exception e) {
            e.printStackTrace();
            // fallback em caso de problema de parsing / chamada
            return new NlpResultado(Sentimento.NEUTRO, 0.0, "GERAL", 0.0);
        }
    }
}
