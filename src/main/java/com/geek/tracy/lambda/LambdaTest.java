package com.geek.tracy.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LambdaTest {


    @Test
    public void testMap() {
        List<Student> list =  new ArrayList<>();
        list.add(new Student("yang", "10"));
        list.add(new Student("zhi", "12"));
        list.add(new Student("ming", "13"));
        list.add(new Student("yang", "14"));

        Map<String, String> collect = list.stream().collect(Collectors.toMap(
                item -> item.getName(),
                item -> item.getAge(),
                (item1, item2) -> item2));
        System.out.println(collect.toString());
        boolean yang = list.stream().allMatch(item -> {
            String name = item.getName();
            System.out.println("log name : " + name);
            if (name.equals("yang")) {
                return true;
            } else {
                return false;
            }
        });
        System.out.println("result flag: " + yang);
    }

    class Student {
        private String name;
        private String age;

        public Student(String name, String age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
