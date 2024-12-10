package com.geek.tracy.lambda;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.Optional;

/**
 * @author yangzhiming
 * @date 2024/12/10
 * @description Optional
 */
public class OptionalTest {

    /**
     * 使用Optional.ofNullable()创建Optional对象，使用map链式获取子层级属性值，最后使用orElse()返回值。
     */
    @Test
    public void optionalTest() {
        // 使用Optional，去掉复杂的判空写法
        // 正常情况1：使用map链式获取
        City city = City.builder().name("武汉市").build();
        Province province1 = Province.builder().name("湖北省").city(city).build();
        Country country1 = Country.builder().name("中国").province(province1).build();

        String city1 = Optional.ofNullable(country1).map(Country::getProvince).map(Province::getCity).map(City::getName)
                .orElse("空");
        System.out.println("正常情况：" + city1);

        // 情况2：城市为空
        Province province2 = Province.builder().name("江西省").build();
        Country country2 = Country.builder().name("中国").province(province2).build();
        String city2 = Optional.ofNullable(country2).map(Country::getProvince).map(Province::getCity).map(City::getName)
                .orElse("空2");
        System.out.println("情况2：城市为空：" + city2);

        // 情况3：省为空
        Country country3 = Country.builder().name("中国").build();
        String city3 = Optional.ofNullable(country3).map(Country::getProvince).map(Province::getCity).map(City::getName)
                .orElse("空3");
        System.out.println("情况3：省为空：" + city3);

        // 情况4：国家为空
        Country country4 = new Country();
        String city4 = Optional.ofNullable(country4).map(Country::getProvince).map(Province::getCity).map(City::getName)
                .orElse("空4");
        System.out.println("情况4：国家为空：" + city4);
    }

    /**
     * 国家
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Country {

        private String name;

        private Province province;

    }

    /**
     * 省
     */
    @Data
    @Builder
    private static class Province {

        private String name;

        private City city;
    }

    /**
     * 城市
     */
    @Data
    @Builder
    private static class City {

        private String name;
    }


}
