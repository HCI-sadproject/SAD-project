package com.example.hci;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ApiHelper {
    public static String encodeApiKey(String apiKey) {
        try {
            return URLEncoder.encode(apiKey, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // UTF-8은 Java에서 항상 지원되므로, 사실상 이 블록은 실행되지 않습니다.
            throw new RuntimeException("Encoding failed: UTF-8 not supported", e);
        }
    }
}
