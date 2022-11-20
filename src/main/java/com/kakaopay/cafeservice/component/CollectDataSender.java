package com.kakaopay.cafeservice.component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.cafeservice.domain.order.OrderDataSendDto;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CollectDataSender {

    private MockWebServer mockWebServer;
    private WebClient webClient;
    private String expectedResponse;

    @PostConstruct
    public void initDataSend() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        expectedResponse = objectMapper.writeValueAsString("true");

        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url("/collect-api/v1").toString();
        webClient = WebClient.create(baseUrl);
    }

    public Mono<DataSenderResponse> sendData(OrderDataSendDto orderDataSendDto) {
        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value())
            .setBody(expectedResponse)
            .addHeader("Content-Type", MediaType.APPLICATION_JSON)
        );

        return webClient
            .post()
            .uri("/order")
            .body(BodyInserters.fromValue(orderDataSendDto))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(DataSenderResponse.class)
            .doOnSuccess(response -> log.info("data send success : " + response.toString()))
            .doOnError(e -> log.error(e.getMessage(), e));
    }

    @Getter
    @Setter
    @JsonAutoDetect
    @AllArgsConstructor
    public static class DataSenderResponse {

        private String msg;
    }

}
