package com.geek.tracy.util;

import java.util.AbstractList;
import java.util.List;

/**
 * @Author Tracy
 * @Date 2023/10/12
 */
public class ListUtil {
    private ListUtil() {
    }

    /**
     * 分片
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        // list 判空
        if (list == null) {
            throw new NullPointerException("list must not be null");
        }
        // size参数校验
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        return new ListUtil.Partition(list, size);

    }

    private static class Partition<T> extends AbstractList<List<T>> {
        private final List<T> list;
        private final int size;

        public Partition(List<T> list, int size) {
            this.list = list;
            this.size = size;
        }

        @Override
        public List<T> get(int index) {
            int listSize = this.size();
            if (index < 0) {
                throw new IndexOutOfBoundsException("index " + index + " must not be negative");
            } else if (index >= listSize) {
                throw new IndexOutOfBoundsException("index " + index + " must be less than size");
            } else {
                int start = index * this.size;
                int end = Math.min(start + this.size, this.list.size());
                return this.list.subList(start, end);
            }
        }

        @Override
        public int size() {
            // 分区数组大小，转double后，待分区数组大小/分组区间大小，商向上取整
            return (int) Math.ceil((double) this.list.size() / (double) this.size);
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < this.size(); i++) {
                List<T> part = get(i);
                sb.append("partition ").append(i).append(":");
                part.forEach(item -> sb.append(item).append(" "));
            }
            sb.append("]");
            return sb.toString();
        }
    }
}
