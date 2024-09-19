package org.example.bookshop.dto;

public record BookSearchParametersDto(String[] title, String[] author, String[] isbn) {
}
