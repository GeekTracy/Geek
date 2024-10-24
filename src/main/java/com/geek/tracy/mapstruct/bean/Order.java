package com.geek.tracy.mapstruct.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Order {

    private Integer id;

    private String name;

    private String createTime;

    private String updateTime;
}
