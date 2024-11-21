package com.example.bookshop.service;

import com.example.bookshop.dto.BookDtoWithoutCategoryIds;
import com.example.bookshop.dto.CategoryDto;
import com.example.bookshop.dto.CategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CategoryRequestDto categoryDto);

    CategoryDto update(Long id, CategoryRequestDto categoryDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long id);
}
