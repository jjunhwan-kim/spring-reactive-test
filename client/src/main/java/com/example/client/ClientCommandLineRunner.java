package com.example.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Slf4j
@Component
public class ClientCommandLineRunner implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void run(String... args) throws Exception {

        log.info("[{}] V1 요청 시작", LocalDateTime.now());

        for (int i = 1; i <= 5; i++) {

            String id = String.valueOf(i);

            String response = restTemplate.getForObject("http://localhost:8080/v1/books/" + id, String.class);

            log.info("[{}] V1 응답: {}", LocalDateTime.now(), response);
        }

        log.info("[{}] V2 요청 시작", LocalDateTime.now());

        for (int i = 1; i <= 5; i++) {

            String id = String.valueOf(i);

            String response = restTemplate.getForObject("http://localhost:8080/v2/books/" + id, String.class);

            log.info("[{}] V2 응답: {}", LocalDateTime.now(), response);
        }

    }
}
