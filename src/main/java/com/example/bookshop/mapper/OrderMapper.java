package com.example.bookshop.mapper;

import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.mapperconfig.MapperConfig;
import com.example.bookshop.model.Order;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
    OrderDto toDto(Order order);
}
