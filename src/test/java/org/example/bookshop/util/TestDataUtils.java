package org.example.bookshop.util;

import java.math.BigDecimal;
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
}
