package org.example.bookshop.dto.book;

public record BookSearchParametersDto(String[] title, String[] author, String[] isbn) {
}
