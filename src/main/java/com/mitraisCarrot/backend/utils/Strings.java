package com.mitraisCarrot.backend.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Strings {

    public static List<String> stringSeparator(String fields) {
        List<String> fieldList = new ArrayList<>();

        if (fields == null || fields.isEmpty()) {
            return fieldList;
        }

        fieldList = Arrays.asList(fields.trim().replace(" ", "").split(","));

        return fieldList;
    }

    public static String stringToTitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }
}
