package com.example.bookshop.mapper;

import com.example.bookshop.dto.CategoryDto;
import com.example.bookshop.mapperconfig.MapperConfig;
import com.example.bookshop.model.Category;
import java.util.Optional;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    CategoryDto toDto(Optional<Category> byId);

    Category toEntity(CategoryDto categoryDto);
}
