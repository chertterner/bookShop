package com.example.bookshop.mapper;

import com.example.bookshop.dto.CategoryDto;
import com.example.bookshop.dto.CategoryRequestDto;
import com.example.bookshop.mapperconfig.MapperConfig;
import com.example.bookshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryRequestDto categoryRequestDto);

    void updateEntityFromDto(CategoryRequestDto categoryDto, @MappingTarget Category category);
}
