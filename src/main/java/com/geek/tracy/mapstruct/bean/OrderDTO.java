package com.geek.tracy.mapstruct.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {
    private Integer id;

    private String name;

    private String createTime;

    private String updateTime;
}
