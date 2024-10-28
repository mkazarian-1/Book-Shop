package org.example.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.book.BookDto;
import org.example.bookshop.dto.book.BookSearchParametersDto;
import org.example.bookshop.dto.book.CreateBookRequestDto;
import org.example.bookshop.dto.book.UpdateBookRequestDto;
import org.example.bookshop.exception.DuplicateException;
import org.example.bookshop.service.BookService;
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

@Tag(name = "Book repository manager",
        description = "Endpoints for basic book repository management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books (with pagination and sorting)",
            description = """
                    Returns list
                    of all available books
                    by pages and give ability
                    to sort books according to the specified parameters
                    \nNecessary role: USER
                    """)
    @PreAuthorize("hasAuthority('USER')")
    public List<BookDto> getAll(@PageableDefault(size = 5) Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id",
            description = """
                    Returns the book by the specified parameter
                    \nNecessary role: USER
                    """)
    @PreAuthorize("hasAuthority('USER')")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Get all book by specification",
            description = """
                    Returns list of all books that
                    match the specified parameter
                    (with pagination and sorting)
                    \nNecessary role: USER
                    """)
    @PreAuthorize("hasAuthority('USER')")
    public List<BookDto> getAllBySpecification(BookSearchParametersDto bookSearchParametersDto,
                                               @PageableDefault(size = 5) Pageable pageable) {
        return bookService.findAllByParam(bookSearchParametersDto, pageable);
    }

    @PostMapping
    @Operation(summary = "Create book",
            description = """
                    Return the newly created book if the creation went well.
                    Throws DuplicateIsbnException if the isbn already exists.
                    \nNecessary role: ADMIN
                    """)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookRequestDto)
            throws DuplicateException {
        return bookService.save(bookRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book",
            description = """
                    Return the update book if the update went well.
                    Throws DuplicateException if the isbn already exists.
                    \nNecessary role: ADMIN
                    """)
    @PreAuthorize("hasAuthority('ADMIN')")
    public BookDto updateBook(@PathVariable Long id,
                              @RequestBody @Valid UpdateBookRequestDto bookRequestDto)
            throws DuplicateException {
        return bookService.update(bookRequestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete book",
            description = """
                    Return 204 status if delete went well
                    \nNecessary role: ADMIN
                    """)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
