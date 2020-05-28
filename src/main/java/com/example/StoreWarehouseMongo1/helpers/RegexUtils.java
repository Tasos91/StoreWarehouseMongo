package com.example.StoreWarehouseMongo1.helpers;

import java.util.regex.Pattern;

public class RegexUtils {

    public static boolean filePathRegex(String filePath) {
        return Pattern.matches("(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)", filePath);
    }

}
