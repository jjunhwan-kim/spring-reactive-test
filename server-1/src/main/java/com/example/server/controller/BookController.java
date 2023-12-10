package com.example.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class BookController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/v1/books/{id}")
    public BookReadResponse getBookV1(@PathVariable String id) {

        String url = "http://localhost:8081/books/" + id + "/title";
        String title = restTemplate.getForObject(url, String.class);

        url = "http://localhost:8081/books/" + id + "/content";
        String content = restTemplate.getForObject(url, String.class);

        return new BookReadResponse(title, content);
    }

    @GetMapping("/v2/books/{id}")
    public Mono<BookReadResponse> getBookV2(@PathVariable String id) {
        String url = "http://localhost:8081/books/" + id + "/title";

        Mono<String> title = WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

        url = "http://localhost:8081/books/" + id + "/content";

        Mono<String> content = WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

        return Mono.zip(title, content)
                .map(tuple -> new BookReadResponse(tuple.getT1(), tuple.getT2()));
    }
}
