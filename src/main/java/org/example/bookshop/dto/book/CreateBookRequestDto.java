package org.example.bookshop.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotBlank
    @Size(max = 255)
    private String title;
    @NotBlank
    @Size(max = 255)
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
    @Size(max = 255)
    private String description;
    @Size(max = 255)
    private String coverImage;
    @NotEmpty
    private List<Long> categoryIds;
}
