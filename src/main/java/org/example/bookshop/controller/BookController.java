package org.example.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.BookDto;
import org.example.bookshop.dto.BookSearchParametersDto;
import org.example.bookshop.dto.CreateBookRequestDto;
import org.example.bookshop.dto.UpdateBookRequestDto;
import org.example.bookshop.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
            description = "returns list"
            + " of all available books"
            + " by pages and give ability to sort books according to the specified parameters")
    public List<BookDto> getAll(@PageableDefault(size = 5) Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id",
            description = "returns the book by the specified parameter")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Get all book by specification",
            description = "returns list of all books that "
            + " match the specified parameter")
    public List<BookDto> getAllBySpecification(BookSearchParametersDto bookSearchParametersDto) {
        return bookService.findAllByParam(bookSearchParametersDto);
    }

    @PostMapping
    @Operation(summary = "Create book",
            description = "return the newly created book if the creation went well")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book",
            description = "return the update book if the update went well")
    public BookDto updateBook(@PathVariable Long id,
                              @RequestBody @Valid UpdateBookRequestDto bookRequestDto) {
        return bookService.update(bookRequestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete book",
            description = "return 204 status if delete went well")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
