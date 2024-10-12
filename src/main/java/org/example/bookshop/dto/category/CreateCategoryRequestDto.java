package org.example.bookshop.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequestDto {
    @NotBlank
    @Size(max = 30)
    private String name;
    @Size(max = 1000)
    private String description;
}
