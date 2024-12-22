package com.example.bookshop.mapper;

import com.example.bookshop.dto.order.OrderItemDto;
import com.example.bookshop.mapperconfig.MapperConfig;
import com.example.bookshop.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem order);
}
