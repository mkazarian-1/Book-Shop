package org.example.bookshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.example.bookshop.dto.validator.Unique;
import org.example.bookshop.model.Book;

@Getter
@Setter
public class UpdateBookRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    @Min(value = 0)
    private BigDecimal price;
    @NotNull
    @Unique(entity = Book.class, fieldName = "isbn")
    @Pattern(
            regexp = "^\\d{3}-\\d{10}$",
            message = "Invalid ISBN format"
    )
    private String isbn;
    private String description;
    private String coverImage;
}
