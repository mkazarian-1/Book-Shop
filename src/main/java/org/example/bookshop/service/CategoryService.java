package org.example.bookshop.service;

import org.example.bookshop.dto.book.UpdateBookRequestDto;
import org.example.bookshop.dto.category.CategoryDto;
import org.example.bookshop.dto.category.CreateCategoryRequestDto;
import org.example.bookshop.dto.category.UpdateCategoryRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto requestDto);

    CategoryDto update(Long id, UpdateCategoryRequestDto requestDto);

    void deleteById(Long id);
}
