package org.example.bookshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.book.BookSearchParametersDto;
import org.example.bookshop.dto.book.CreateBookRequestDto;
import org.example.bookshop.dto.book.UpdateBookRequestDto;
import org.example.bookshop.exception.DuplicateIsbnException;
import org.example.bookshop.mapper.BookMapper;
import org.example.bookshop.model.Book;
import org.example.bookshop.repository.BookRepository;
import org.example.bookshop.repository.specification.SpecificationBuilder;
import org.example.bookshop.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final SpecificationBuilder<Book> specificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        if (bookRepository.existsByIsbn(bookRequestDto.getIsbn())) {
            throw new DuplicateIsbnException("Book with current isbn already exist: "
                    + bookRequestDto.getIsbn());
        }
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(bookRequestDto)));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> findAllByParam(BookSearchParametersDto bookSearchParametersDto,
                                        Pageable pageable) {
        return bookRepository
                .findAll(specificationBuilder
                        .build(bookSearchParametersDto), pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        return bookMapper.toDto(
                bookRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Can't find book by id:" + id)));
    }

    @Override
    public BookDto update(UpdateBookRequestDto requestDto, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find book by id:" + id));

        if (!book.getIsbn().equals(requestDto.getIsbn())
                && bookRepository.existsByIsbn(requestDto.getIsbn())) {
            throw new DuplicateIsbnException(
                    "Book with current isbn already exist: " + requestDto.getIsbn());
        }

        bookMapper.updateBookFromDto(requestDto, book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
