package com.geek.tracy.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Tracy
 * @Date 2023/6/2
 */
public class ObjectMapperTest {

    /**
     * 泛型类
     */
    @Data
    public static class Generic<T> {
        private String name;
        private T info;
    }

    /**
     * 普通类
     */
    @Data
    public static class Shape {
        private Integer hight;
        private Integer wight;
    }

    /**
     * 测试类
     */
    @Test
    public void test () throws JsonProcessingException {
        Generic<List<Shape>> generic = intiData();

        ObjectMapper mapper = new ObjectMapper();

        // -- mapper 参数配置 开始

        // 序列化是，对象属性包含设置
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 反序列化时，遇到位置属性不会报错：true --> 遇到没有的属性会报错，false --> 没有的属性不会管，不会报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 序列化时，如果时空对象，不报错
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略 transient 修饰的属性
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, false);
        // 设置时间格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // -- mapper 参数配置 结束
        // 序列化
        String string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generic);
        System.out.println(string);

        // 反序列化
        Generic<List<Shape>> generic1 = mapper.readValue(string, new TypeReference<Generic<List<Shape>>>() {
        });
        System.out.println(generic1.toString());
    }
        /**
     * 初始化数据
     */
    public Generic<List<Shape>> intiData() {
        Generic<List<Shape>> generic = new Generic<>();
        generic.setName("generic name");

        Shape shape = new Shape();
        shape.setHight(100);
        shape.setWight(200);

        Shape shape2 = new Shape();
        shape2.setHight(200);
        shape2.setWight(400);

        ArrayList<Shape> list= new ArrayList<>();
        list.add(shape);
        list.add(shape2);

        generic.setInfo(list);

        return generic;
    }
}
