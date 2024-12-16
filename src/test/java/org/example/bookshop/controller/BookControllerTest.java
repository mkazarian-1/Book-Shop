package org.example.bookshop.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.bookshop.util.TestDataUtils.createBookDto;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.book.CreateBookRequestDto;
import org.example.bookshop.dto.book.UpdateBookRequestDto;
import org.example.bookshop.util.TestDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Sql(scripts = "classpath:books/add-books-with-category-except-id.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DisplayName("""
            Should create and save Book in DB
            and return BookDto with 201 status
            """)
    void createBook_WithCorrectCreateBookRequestDto_Success()
            throws Exception {
        //Given
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

        BookDto expected = createBookDto(1L,
                "Jhon Snow1",
                "Author1",
                "978-2388981567",
                new BigDecimal(5),
                """
                        sed do eiusmod tempor incididunt
                        ut labore et dolore magna aliqua.
                        """,
                List.of(1L, 2L));

        String jsonRequest = objectMapper.writeValueAsString(createBookDto);

        //When
        MvcResult mvcResult = mockMvc
                .perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType
                                        .APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        //Then
        BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        assertTrue(EqualsBuilder.reflectionEquals(expected,
                actual, List.of("categoryIds", "id")));
        assertThat(actual.getCategoryIds())
                .containsExactlyInAnyOrderElementsOf(
                        expected.getCategoryIds()
                );
    }

    @Test
    @Sql(scripts = "classpath:books/add-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @DisplayName("""
            Should update Book in DB and return BookDto
            """)
    void updateBook_WithCorrectUpdateBookRequestDto_Success()
            throws Exception {
        UpdateBookRequestDto expected = new UpdateBookRequestDto();
        expected.setTitle("Some Title1 new");
        expected.setAuthor("Jhon kik 1 new");
        expected.setIsbn("978-2388981567");
        expected.setPrice(new BigDecimal(100));
        expected.setCategoryIds(List.of(1L, 2L));

        String jsonRequest = objectMapper.writeValueAsString(expected);

        //When
        MvcResult mvcResult = mockMvc
                .perform(
                        put("/books/1")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //Then
        BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());

        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getAuthor(), actual.getAuthor());
        Assertions.assertEquals(expected.getIsbn(), actual.getIsbn());
        Assertions.assertEquals(expected.getPrice().stripTrailingZeros(),
                actual.getPrice().stripTrailingZeros());
        assertThat(actual.getCategoryIds())
                .containsExactlyInAnyOrderElementsOf(
                        expected.getCategoryIds()
                );
    }

    @Test
    @Sql(scripts = "classpath:books/add-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @DisplayName("""
            Should delete Book in DB and return code 204
            """)
    void deleteBook_BookExist_Success() throws Exception {
        //When
        mockMvc.perform(
                delete("/books/1")
        ).andExpect(status().isNoContent()).andReturn();
    }

    @Test
    @Sql(scripts = "classpath:books/add-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"USER"})
    @DisplayName("""
            Should return all books in DB
            """)
    public void getAll_WithDefaultPage_Success() throws Exception {
        //Given
        List<BookDto> expected = TestDataUtils.getBookDtoList();

        //When
        MvcResult mvcResult = mockMvc
                .perform(
                        get("/books")
                )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<BookDto> actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, BookDto.class)
        );

        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            BookDto expectedBook = expected.get(i);
            BookDto actualBook = actual.get(i);
            expectedBook.setPrice(expectedBook.getPrice().stripTrailingZeros());
            actualBook.setPrice(actualBook.getPrice().stripTrailingZeros());
            assertTrue(EqualsBuilder.reflectionEquals(expected.get(i),
                    actual.get(i), List.of("categoryIds", "id")));
            assertThat(actual.get(i).getCategoryIds())
                    .containsExactlyInAnyOrderElementsOf(
                            expected.get(i).getCategoryIds()
                    );
        }

    }

    @Test
    @Sql(scripts = "classpath:books/add-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"USER"})
    @DisplayName("""
            Should find book with entered id
            """)
    public void getById_WitheExistedBook_Success()
            throws Exception {
        //Given
        BookDto expected = createBookDto(2L,
                "Some Title2",
                "Jhon kik 2",
                "12346",
                new BigDecimal(120),
                List.of(1L));

        //When
        MvcResult mvcResult = mockMvc
                .perform(
                        get("/books/2")
                )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                BookDto.class
        );

        expected.setPrice(expected.getPrice().stripTrailingZeros());
        actual.setPrice(actual.getPrice().stripTrailingZeros());
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual,
                List.of("categoryIds")));
        assertThat(actual.getCategoryIds())
                .containsExactlyInAnyOrderElementsOf(
                        expected.getCategoryIds()
                );
    }

    @Test
    @Sql(scripts = "classpath:books/add-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"USER"})
    @DisplayName("""
            Should find book with entered specification
            """)
    public void getAllBySpecification_WitheExistedBook_Success() throws Exception {
        //When
        MvcResult mvcResult = mockMvc
                .perform(
                        get("/books/search")
                                .param("title", "Some Title2")
                )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<BookDto> actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, BookDto.class)
        );
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("Some Title2", actual.getFirst().getTitle());
    }

    @Test
    @Sql(scripts = "classpath:books/add-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"USER"})
    @DisplayName("""
            Should return empty array because book with current
            specification doesn't exist
            """)
    public void getAllBySpecification_WitheNoExistedBook_Success()
            throws Exception {
        //When
        MvcResult mvcResult = mockMvc
                .perform(
                        get("/books/search")
                                .param("title", "Some bad title")
                )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<BookDto> actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class,
                        BookDto.class)
        );
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    @Sql(scripts = "classpath:books/add-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"USER"})
    @DisplayName("""
            Should return all books because current param doesn't exist
            """)

    public void getAllBySpecification_WitheNoExistedParam_Success()
            throws Exception {
        //When
        MvcResult mvcResult = mockMvc
                .perform(
                        get("/books/search")
                                .param("wrongName", "Some bad title")
                )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<BookDto> actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, BookDto.class)
        );
        Assertions.assertEquals(4, actual.size());
    }
}
