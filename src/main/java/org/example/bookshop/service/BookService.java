package org.example.bookshop.service;

import java.util.List;
import org.example.bookshop.dto.BookDto;
import org.example.bookshop.dto.BookSearchParametersDto;
import org.example.bookshop.dto.CreateBookRequestDto;
import org.example.bookshop.dto.UpdateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    List<BookDto> findAllByParam(BookSearchParametersDto bookSearchParametersDto,
                                 Pageable pageable);

    BookDto getById(Long id);

    BookDto update(UpdateBookRequestDto bookRequestDto, Long id);

    void deleteById(Long id);
}
