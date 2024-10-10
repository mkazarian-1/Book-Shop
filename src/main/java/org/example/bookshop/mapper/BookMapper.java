package org.example.bookshop.mapper;

import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.book.CreateBookRequestDto;
import org.example.bookshop.dto.book.UpdateBookRequestDto;
import org.example.bookshop.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toEntity(CreateBookRequestDto requestDto);

    void updateBookFromDto(UpdateBookRequestDto requestDto, @MappingTarget Book book);
}
