package ru.aao.geoproxyservice.commons;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpUtils {
    public static Map<String, String> getQueryParam(String query) {
        return Arrays.stream(query.split("&"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }
}
