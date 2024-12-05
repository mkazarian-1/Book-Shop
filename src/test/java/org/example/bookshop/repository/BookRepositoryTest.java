package org.example.bookshop.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.example.bookshop.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("""
            Find all books with Pageable from empty DB
            """)
    void findAllBooksWithPageable_AnyBooks_ReturnsEmptyList() {
        List<Book> actual = bookRepository.findAll(
                PageRequest.of(0, 10)).toList();
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("""
            Save book with correct values
            """)
    void save_OneBook_ReturnsOneBook() {
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

        Book actual1 = bookRepository.save(expected);
        assertTrue(EqualsBuilder.reflectionEquals(expected,
                actual1, List.of("categoryIds", "id")));
        Book actual2 = bookRepository.findById(actual1.getId()).get();
        assertTrue(EqualsBuilder.reflectionEquals(expected,
                actual2, List.of("categoryIds", "id")));

        bookRepository.deleteById(actual1.getId());
    }

    @Test
    @DisplayName("""
            Find all books with Pageable
            """)
    @Sql(scripts = "classpath:books/add-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAll_WithBooksAndCategories_ReturnsListOfBooks() {
        List<Book> actual = bookRepository.findAll(
                PageRequest.of(0, 10)).toList();
        Assertions.assertEquals(4, actual.size());
        Assertions.assertEquals(2, actual.getFirst()
                .getCategories().size());
        Assertions.assertEquals(1, actual.get(1)
                .getCategories().size());
        Assertions.assertEquals(0, actual.get(2)
                .getCategories().size());
    }

    @Test
    @DisplayName("""
            Find all books with current category id
            """)
    @Sql(scripts = "classpath:books/add-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoriesId_WithBooksAndCategories_ReturnListOfBooks() {
        List<Book> actual = bookRepository.findAllByCategoriesId(
                PageRequest.of(0, 10), 1L);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals("Some Title1",
                actual.getFirst().getTitle());
        Assertions.assertEquals("Some Title2",
                actual.get(1).getTitle());
    }

    @Test
    @DisplayName("""
            Find all books by specification
            """)
    @Sql(scripts = "classpath:books/add-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAll_WithBooksAndCategoriesBySpecification_ReturnListOfBooks() {
        Specification<Book> specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%Some%");

        List<Book> actual = bookRepository.findAll(specification,
                PageRequest.of(0, 10)).toList();
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals("Some Title1",
                actual.getFirst().getTitle());
        Assertions.assertEquals("Some Title2",
                actual.get(1).getTitle());
    }
}
