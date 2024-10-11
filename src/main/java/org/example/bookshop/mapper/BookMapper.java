package org.example.bookshop.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import org.example.bookshop.dto.book.CreateBookRequestDto;
import org.example.bookshop.dto.book.UpdateBookRequestDto;
import org.example.bookshop.model.Book;
import org.example.bookshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = {CategoryMapper.class})
public interface BookMapper {
    @Mapping(source = "categories", target = "categoryIds",
            qualifiedByName = "setCategoryIds")
    BookDto toDto(Book book);

    @Named("setCategoryIds")
    default List<Long> setCategoryIds(Set<Category> categories) {
        return categories.stream()
                .map(Category::getId)
                .toList();
    }

    @Mapping(target = "categories", source = "categoryIds",
            qualifiedByName = "setCategoriesFromIds")
    Book toEntity(CreateBookRequestDto requestDto);

    @Mapping(target = "categories", source = "categoryIds",
            qualifiedByName = "setCategoriesFromIds")
    void updateBookFromDto(UpdateBookRequestDto requestDto,
                           @MappingTarget Book book);

    @Named("setCategoriesFromIds")
    default Set<Category> setCategoriesFromIds(List<Long> categoryIds) {
        if (categoryIds == null) {
            return new HashSet<>();
        }
        return categoryIds.stream()
                .map(id -> {
                    Category category = new Category();
                    category.setId(id);
                    return category;
                })
                .collect(Collectors.toSet());
    }

    BookDtoWithoutCategoryIds toDtoWithOutCategoryIds(Book book);
}
