package org.example.bookshop.controller;

import static org.example.bookshop.util.TestDataUtils.createCategoryDto;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import org.example.bookshop.dto.category.CategoryDto;
import org.example.bookshop.dto.category.CreateCategoryRequestDto;
import org.example.bookshop.dto.category.UpdateCategoryRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DataSource dataSource;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        sqlCommandExecutor(dataSource, "books/delete-books-with-category.sql");
    }

    @SneakyThrows
    static void sqlCommandExecutor(DataSource dataSource, String path) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource(path));
        }
    }

    @Test
    @Sql(scripts = "classpath:categories/add-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"USER"})
    @DisplayName("""
            Should return list of CategoryDto categories in DB
            """)
    void getAll_WithDefaultPage_Success() throws Exception {
        //Given
        List<CategoryDto> expected = getDtoList();

        //When
        MvcResult mvcResult = mockMvc
                .perform(
                        get("/categories")
                )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<CategoryDto> actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class,
                        CategoryDto.class));

        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertTrue(EqualsBuilder.reflectionEquals(expected.get(i),
                    actual.get(i)));
        }
    }

    @Test
    @Sql(scripts = "classpath:books/add-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"USER"})
    @DisplayName("""
            Should return list of BookDtoWithoutCategoryIds by entered category id
            """)
    void getBookByCategoriesId_WithDefaultPage_Success() throws Exception {
        //When
        MvcResult mvcResult = mockMvc
                .perform(
                        get("/categories/1/book")
                )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<BookDtoWithoutCategoryIds> actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(
                        List.class, BookDtoWithoutCategoryIds.class));

        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals("Some Title1", actual.getFirst().getTitle());
        Assertions.assertEquals("Some Title2", actual.get(1).getTitle());
    }

    @Test
    @Sql(scripts = "classpath:categories/add-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"USER"})
    @DisplayName("""
            Should return CategoryDto with entered id
            """)
    void getCategory_WithExistedId_Success() throws Exception {
        //Given
        CategoryDto expected = new CategoryDto();
        expected.setId(2L);
        expected.setName("lol info2");
        expected.setDescription("some description2");

        //When
        MvcResult mvcResult = mockMvc
                .perform(
                        get("/categories/2")
                )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        CategoryDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), CategoryDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    @Sql(scripts = "classpath:categories/add-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"USER"})
    @DisplayName("""
            Should return 403 status because object with current id doesn't exist
            """)
    void getCategory_WithNonExistedId_NotFound() throws Exception {
        //When
        mockMvc.perform(
                        get("/categories/20")
                )
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @Sql(scripts = "classpath:categories/add-categories-without-id.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @DisplayName("""
            Should create new Category, save in DB and return CategoryDto with 201 status
            """)
    void createBook_WithCorrectCreateCategoryRequestDto_Success() throws Exception {
        //Given
        CreateCategoryRequestDto createCategoryDto = new CreateCategoryRequestDto();
        createCategoryDto.setName("lol info new");
        createCategoryDto.setDescription("some description new");

        CategoryDto expected = createCategoryDto(1L,
                "lol info new",
                "some description new");

        String jsonRequest = objectMapper.writeValueAsString(createCategoryDto);

        //When
        MvcResult mvcResult = mockMvc
                .perform(
                        post("/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        //Then
        CategoryDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), CategoryDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }

    @Test
    @Sql(scripts = "classpath:categories/add-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @DisplayName("""
            Should update the existing Category
            """)
    void updateBook_WithCorrectCreateCategoryRequestDto_Success() throws Exception {
        //Given
        UpdateCategoryRequestDto updateCategoryRequestDto
                = new UpdateCategoryRequestDto();
        updateCategoryRequestDto.setName("lol info new");
        updateCategoryRequestDto.setDescription("some description new");

        CategoryDto expected = createCategoryDto(2L,
                "lol info new",
                "some description new");

        String jsonRequest = objectMapper
                .writeValueAsString(updateCategoryRequestDto);

        //When
        final MvcResult mvcResult = mockMvc
                .perform(
                        put("/categories/2")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //Then
        CategoryDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                CategoryDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    @Sql(scripts = "classpath:categories/add-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:books/delete-books-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @DisplayName("""
            Should delete Category in DB and return code 204
            """)
    void deleteBook_BookExist_Success() throws Exception {
        //When
        mockMvc.perform(
                delete("/categories/1")
        ).andExpect(status().isNoContent()).andReturn();
    }

    private static List<CategoryDto> getDtoList() {
        List<CategoryDto> expected = new ArrayList<>();
        expected.add(createCategoryDto(1L, "lol info1", "some description1"));
        expected.add(createCategoryDto(2L, "lol info2", "some description2"));
        expected.add(createCategoryDto(3L, "lol info3", "some description3"));
        expected.add(createCategoryDto(4L, "lol info4", "some description4"));

        return expected;
    }
}
