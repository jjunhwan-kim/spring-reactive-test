package com.example.server.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookReadResponse {
    private final String title;
    private final String content;
}
