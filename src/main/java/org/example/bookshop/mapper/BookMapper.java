package org.example.bookshop.mapper;

import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.BookDto;
import org.example.bookshop.dto.CreateBookRequestDto;
import org.example.bookshop.dto.UpdateBookRequestDto;
import org.example.bookshop.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    Book toModel(UpdateBookRequestDto requestDto);

    void updateBookFromDto(UpdateBookRequestDto requestDto, @MappingTarget Book book);
}
