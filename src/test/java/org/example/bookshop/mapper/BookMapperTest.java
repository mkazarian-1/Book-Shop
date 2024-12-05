package org.example.bookshop.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.book.CreateBookRequestDto;
import org.example.bookshop.dto.book.UpdateBookRequestDto;
import org.example.bookshop.mapper.impl.BookMapperImpl;
import org.example.bookshop.model.Book;
import org.example.bookshop.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookMapperTest {
    private final BookMapper bookMapper = new BookMapperImpl();

    @Test
    @DisplayName("Should convert valid Book entity to BookDto")
    void toDto_WithCorrectBook_ShouldReturnBookDto() {
        //Given
        Category category1 = new Category();
        category1.setId(1L);
        category1.setDescription("do eiusmod tempor incididunt");
        category1.setName("Cool books");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setDescription("do tempor incididunt");
        category2.setName("Cool cool books");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Jhon Snow1");
        book.setAuthor("Author1");
        book.setIsbn("978-2388981567");
        book.setPrice(new BigDecimal(5));
        book.setDescription("""
                sed do eiusmod tempor incididunt
                ut labore et dolore magna aliqua.
                """);
        book.setCategories(new HashSet<>(List.of(category1, category2)));

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setTitle("Jhon Snow1");
        expected.setAuthor("Author1");
        expected.setIsbn("978-2388981567");
        expected.setPrice(new BigDecimal(5));
        expected.setDescription("""
                sed do eiusmod tempor incididunt
                ut labore et dolore magna aliqua.
                """);
        expected.setCategoryIds(List.of(1L, 2L));

        //When
        BookDto actual = bookMapper.toDto(book);

        //Then
        assertTrue(EqualsBuilder.reflectionEquals(expected,
                actual, "categoryIds"));
        assertThat(actual.getCategoryIds())
                .containsExactlyInAnyOrderElementsOf(expected.getCategoryIds());
    }

    @Test
    @DisplayName("Should convert valid BookDto to Book entity")
    void toEntity_WithCorrectBookDto_ShouldReturnBookDto() {
        //Given
        Category category1 = new Category();
        category1.setId(1L);

        Category category2 = new Category();
        category2.setId(2L);

        Book expected = new Book();
        expected.setId(1L);
        expected.setTitle("Jhon Snow1");
        expected.setAuthor("Author1");
        expected.setIsbn("978-2388981567");
        expected.setPrice(new BigDecimal(5));
        expected.setDescription("""
                sed do eiusmod tempor incididunt
                ut labore et dolore magna aliqua.
                """);
        expected.setCategories(new HashSet<>(
                List.of(category1, category2)));

        CreateBookRequestDto createBookDto = new CreateBookRequestDto();
        createBookDto.setTitle("Jhon Snow1");
        createBookDto.setAuthor("Author1");
        createBookDto.setIsbn("978-2388981567");
        createBookDto.setPrice(new BigDecimal(5));
        createBookDto.setDescription("""
                sed do eiusmod tempor incididunt
                ut labore et dolore magna aliqua.
                """);
        createBookDto.setCategoryIds(List.of(1L, 2L));

        //When
        Book actual = bookMapper.toEntity(createBookDto);

        //Then
        assertTrue(EqualsBuilder.reflectionEquals(expected,
                actual, List.of("categories", "id")));
        assertThat(actual.getCategories())
                .extracting("id")
                .containsExactlyInAnyOrderElementsOf(
                        expected.getCategories().stream().map(Category::getId).toList()
                );
    }

    @Test
    @DisplayName("Should update existing Book by valid BookDto")
    void updateBookFromDto_WithCorrectBookAndBookDto_ShouldReturnBook() {
        //Given
        Category category1 = new Category();
        category1.setId(1L);

        Category category2 = new Category();
        category2.setId(2L);

        Book actual = new Book();
        actual.setId(1L);
        actual.setTitle("Jhon Snow old");
        actual.setAuthor("Author old");
        actual.setIsbn("978-2388981567 old");
        actual.setPrice(new BigDecimal(10));
        actual.setDescription("""
                sed do eiusmod tempor incididunt
                ut labore et dolore ua oldBook.
                """);
        actual.setCategories(new HashSet<>(
                List.of(category2)));

        Book expected = new Book();
        expected.setId(1L);
        expected.setTitle("Jhon Snow1");
        expected.setAuthor("Author1");
        expected.setIsbn("978-2388981567");
        expected.setPrice(new BigDecimal(5));
        expected.setDescription("""
                sed do eiusmod tempor incididunt
                ut labore et dolore magna aliqua.
                """);
        expected.setCategories(new HashSet<>(
                List.of(category1, category2)));

        UpdateBookRequestDto updateBookRequestDto =
                new UpdateBookRequestDto();
        updateBookRequestDto.setTitle("Jhon Snow1");
        updateBookRequestDto.setAuthor("Author1");
        updateBookRequestDto.setIsbn("978-2388981567");
        updateBookRequestDto.setPrice(new BigDecimal(5));
        updateBookRequestDto.setDescription("""
                sed do eiusmod tempor incididunt
                ut labore et dolore magna aliqua.
                """);
        updateBookRequestDto.setCategoryIds(List.of(1L, 2L));

        //When
        bookMapper.updateBookFromDto(updateBookRequestDto, actual);

        //Then
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual,
                List.of("categories", "id")));
        assertThat(actual.getCategories())
                .extracting("id")
                .containsExactlyInAnyOrderElementsOf(
                        expected.getCategories().stream().map(Category::getId).toList()
                );
    }
}
