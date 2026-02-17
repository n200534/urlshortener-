package com.example.urlshortner.util;



public class Base62Encoder {

    private static final String BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encode(Long number) {

        if (number == 0) {
            return "a";
        }

        StringBuilder sb = new StringBuilder();

        while (number > 0) {
            int remainder = (int) (number % 62);
            sb.append(BASE62.charAt(remainder));
            number /= 62;
        }

        return sb.reverse().toString();
    }
}
