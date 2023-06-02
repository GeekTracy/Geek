package com.geek.tracy.jackson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Tracy
 * @Date 2023/6/1
 */
public class TypeReferenceTest {

    public static void main(String[] args) {

        class IntMap extends HashMap<String, Integer> {

        }

        IntMap intMap = new IntMap();
        // 获取父类
        System.out.println("获取父类 Superclass：" + intMap.getClass().getSuperclass());
        System.out.println(intMap.getClass().getAnnotatedSuperclass());
        System.out.println(intMap.getClass().getGenericSuperclass());

        Type type = intMap.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            // 参数化 类型
            ParameterizedType pt = (ParameterizedType) type;
            System.out.println("参数化类型 parameterizedType 名称：" + pt.getTypeName());
            Type[] actualTypeArguments = pt.getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println("实际类型参数actualTypeArguments：" + actualTypeArgument.getTypeName());
            }
            System.out.println("-----------------------------------");
            System.out.println(pt.getRawType());
            System.out.println(pt.getOwnerType());
        }
        System.out.println("------------newclass-----------------------");
        Map<String, Integer> newMap = new HashMap<>();
        System.out.println(newMap.getClass().getSuperclass());

        Type genericSuperclass = newMap.getClass().getGenericSuperclass();
        System.out.println(genericSuperclass.getTypeName());

        System.out.println("------------subclass-----------------------");
        Map<String, Integer> newMap11 = new HashMap<String, Integer>(){};
        System.out.println(newMap11.getClass().getSuperclass());

        Type genericSuperclass1 = newMap11.getClass().getGenericSuperclass();
        System.out.println(genericSuperclass1.getTypeName());
    }
}
