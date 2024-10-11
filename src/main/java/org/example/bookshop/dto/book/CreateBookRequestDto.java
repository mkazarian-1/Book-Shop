package org.example.bookshop.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    @Min(value = 0)
    private BigDecimal price;
    @NotBlank
    @Pattern(
            regexp = "^\\d{3}-\\d{10}$",
            message = "Invalid ISBN format"
    )
    private String isbn;
    private String description;
    private String coverImage;
    @NotNull
    private List<Long> categoryIds;
}
