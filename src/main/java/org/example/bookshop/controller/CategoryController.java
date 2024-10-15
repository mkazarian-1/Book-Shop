package org.example.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import org.example.bookshop.dto.category.CategoryDto;
import org.example.bookshop.dto.category.CreateCategoryRequestDto;
import org.example.bookshop.dto.category.UpdateCategoryRequestDto;
import org.example.bookshop.service.BookService;
import org.example.bookshop.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category repository manager",
        description = "Endpoints for basic category repository management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @GetMapping()
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get all categories (with pagination and sorting)",
            description = """
                    Returns list
                    of all available categories
                    by pages and give ability
                    to sort categories according to the specified parameters
                    \nNecessary role: USER
                    """)
    public List<CategoryDto> getAll(@PageableDefault(size = 5) Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}/book")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get all books from current category (with pagination and sorting)",
            description = """
                    Returns list
                    of all available books from current category
                    by pages and give ability
                    to sort books according to the specified parameters
                    \nNecessary role: USER
                    """)
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            Pageable pageable, @PathVariable Long id) {
        return bookService.findAllByCategoriesId(pageable, id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get category by id",
            description = """
                    Returns the category by the specified parameter
                    \nNecessary role: USER
                    """)
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create category",
            description = """
                    Return the newly created category if the creation went well.
                    \nNecessary role: ADMIN
                    """)
    public CategoryDto createCategory(@RequestBody @Valid CreateCategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update category",
            description = """
                    Return the update book if the update went well
                    \nNecessary role: ADMIN
                    """)
    public CategoryDto updateCategory(@PathVariable Long id,
                                      @RequestBody @Valid UpdateCategoryRequestDto
                                              requestDto) {
        return categoryService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete category",
            description = """
                    Return 204 status if delete went well
                    \nNecessary role: ADMIN
                    """)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
