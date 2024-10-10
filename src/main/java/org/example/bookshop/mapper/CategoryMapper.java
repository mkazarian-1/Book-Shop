package org.example.bookshop.mapper;

import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.category.CategoryDto;
import org.example.bookshop.dto.category.CreateCategoryRequestDto;
import org.example.bookshop.dto.category.UpdateCategoryRequestDto;
import org.example.bookshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.lang.annotation.Target;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequestDto requestDto);

    void updateCategoryFromDto(@MappingTarget Category category, UpdateCategoryRequestDto requestDto);
}
