package org.example.bookshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.BookDto;
import org.example.bookshop.dto.CreateBookRequestDto;
import org.example.bookshop.dto.UpdateBookRequestDto;
import org.example.bookshop.mapper.BookMapper;
import org.example.bookshop.repository.BookRepository;
import org.example.bookshop.service.BookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(book)));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll()
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
    public BookDto update(UpdateBookRequestDto bookRequestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(bookRequestDto)));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
