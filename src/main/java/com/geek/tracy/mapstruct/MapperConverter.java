package com.geek.tracy.mapstruct;

import com.geek.tracy.mapstruct.bean.Order;
import com.geek.tracy.mapstruct.bean.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 */
@Mapper
public interface MapperConverter {

    MapperConverter INSTANCE = Mappers.getMapper(MapperConverter.class);

    // 定义映射
    OrderDTO orderToDto(Order order);


}
