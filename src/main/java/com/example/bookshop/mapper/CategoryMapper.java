package com.example.bookshop.mapper;

import com.example.bookshop.dto.CategoryDto;
import com.example.bookshop.mapperconfig.MapperConfig;
import com.example.bookshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);

    @Mapping(target = "id", source = "categoryDto.id")
    @Mapping(target = "name",source = "categoryDto.name")
    @Mapping(target = "description", source = "categoryDto.description")
    Category updateEntityFromDto(CategoryDto categoryDto, Category category);
}
