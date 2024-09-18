package org.example.bookshop.service;

import java.util.List;
import org.example.bookshop.dto.BookDto;
import org.example.bookshop.dto.BookSearchParametersDto;
import org.example.bookshop.dto.CreateBookRequestDto;
import org.example.bookshop.dto.UpdateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll();

    List<BookDto> findAllByParam(BookSearchParametersDto bookSearchParametersDto);

    BookDto getById(Long id);

    BookDto update(UpdateBookRequestDto bookRequestDto, Long id);

    void deleteById(Long id);
}
