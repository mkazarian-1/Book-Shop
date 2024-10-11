package org.example.bookshop.service;

import java.util.List;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import org.example.bookshop.dto.book.BookSearchParametersDto;
import org.example.bookshop.dto.book.CreateBookRequestDto;
import org.example.bookshop.dto.book.UpdateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    List<BookDto> findAllByParam(BookSearchParametersDto bookSearchParametersDto,
                                 Pageable pageable);

    List<BookDtoWithoutCategoryIds> findAllByCategoriesId(Pageable pageable, Long categoryId);

    BookDto getById(Long id);

    BookDto update(UpdateBookRequestDto bookRequestDto, Long id);

    void deleteById(Long id);

}
