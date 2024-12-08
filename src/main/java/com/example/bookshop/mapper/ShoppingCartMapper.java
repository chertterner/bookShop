package com.example.bookshop.mapper;

import com.example.bookshop.dto.ShoppingCartDto;
import com.example.bookshop.mapperconfig.MapperConfig;
import com.example.bookshop.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
