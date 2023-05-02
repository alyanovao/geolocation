package ru.aao.consumerservice.service;

import java.sql.Timestamp;

public class Util {
    public static Timestamp toTimestamp(Long value) {
        return new Timestamp(value * 1000);
    }
}
