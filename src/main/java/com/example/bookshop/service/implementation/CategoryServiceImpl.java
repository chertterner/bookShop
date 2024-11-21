package com.example.bookshop.service.implementation;

import com.example.bookshop.dto.BookDtoWithoutCategoryIds;
import com.example.bookshop.dto.CategoryDto;
import com.example.bookshop.dto.CategoryRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.mapper.CategoryMapper;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        return new PageImpl<>(categoryRepository.findAll(pageable)
                .stream()
                .map(categoryMapper::toDto)
                .toList());
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No such a category with id: " + id));
    }

    @Override
    public CategoryDto save(CategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.toEntity(categoryRequestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, CategoryRequestDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't found category with id: " + id));
        categoryMapper.updateEntityFromDto(categoryDto, category);
        return categoryMapper.toDto(
                categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long id) {
        return bookRepository.findAllByCategoryId(id)
                .stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }
}
