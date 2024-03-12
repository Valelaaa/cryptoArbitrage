package com.example.ccxt.bybit.spot.entity;

import lombok.Data;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
@Data
public class OrderBookDto {
    private String topic;
    private long ts;
    private String type;
    private Data data;
    private long cts;
    @lombok.Data
    public static class Data {
        private String s;
        private List<List<String>> b;
        private List<List<String>> a;
        private int u;
        private int seq;

        @Override
        public String toString() {
            return "Data{" +
                    "s='" + s + '\'' +
                    ", b=" + b +
                    ", a=" + a +
                    ", u=" + u +
                    ", seq=" + seq +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderBookDto{" +
                "topic='" + topic + '\'' +
                ", ts=" + ts +
                ", type='" + type + '\'' +
                ", data=" + data +
                ", cts=" + cts +
                '}';
    }
    // геттеры и сеттеры для всех полей
}
