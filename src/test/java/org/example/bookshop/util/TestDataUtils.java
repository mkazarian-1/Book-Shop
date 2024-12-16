package org.example.bookshop.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.category.CategoryDto;

public class TestDataUtils {
    public static CategoryDto createCategoryDto(Long id, String name, String description) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName(name);
        categoryDto.setDescription(description);
        return categoryDto;
    }

    public static BookDto createBookDto(Long id,
                                        String title,
                                        String author,
                                        String isbn,
                                        BigDecimal price,
                                        String description,
                                        List<Long> categoryIds) {
        BookDto expected = new BookDto();
        expected.setId(id);
        expected.setTitle(title);
        expected.setAuthor(author);
        expected.setIsbn(isbn);
        expected.setPrice(price);
        expected.setDescription(description);
        expected.setCategoryIds(categoryIds);
        return expected;
    }

    public static BookDto createBookDto(Long id,
                                        String title,
                                        String author,
                                        String isbn,
                                        BigDecimal price,
                                        List<Long> categoryIds) {
        BookDto expected = new BookDto();
        expected.setId(id);
        expected.setTitle(title);
        expected.setAuthor(author);
        expected.setIsbn(isbn);
        expected.setPrice(price);
        expected.setCategoryIds(categoryIds);
        return expected;
    }

    public static List<BookDto> getBookDtoList() {
        List<BookDto> expected = new ArrayList<>();
        expected.add(createBookDto(1L,
                "Some Title1",
                "Jhon kik 1",
                "12345",
                new BigDecimal(100),
                List.of(1L, 2L)));
        expected.add(createBookDto(1L,
                "Some Title2",
                "Jhon kik 2",
                "12346",
                new BigDecimal(120),
                List.of(1L)));
        expected.add(createBookDto(1L,
                "Title3",
                "Jhon kik 3",
                "12347",
                new BigDecimal(130),
                List.of()));
        expected.add(createBookDto(1L,
                "Title4",
                "Jhon kik 4",
                "12348",
                new BigDecimal(140),
                List.of()));
        return expected;
    }

    public static List<CategoryDto> getDtoList() {
        List<CategoryDto> expected = new ArrayList<>();
        expected.add(createCategoryDto(1L, "lol info1", "some description1"));
        expected.add(createCategoryDto(2L, "lol info2", "some description2"));
        expected.add(createCategoryDto(3L, "lol info3", "some description3"));
        expected.add(createCategoryDto(4L, "lol info4", "some description4"));
        return expected;
    }
}
