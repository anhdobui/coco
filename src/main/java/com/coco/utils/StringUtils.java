package com.coco.utils;

public class StringUtils {
    public static boolean isNullOrEmpty(String value) {
        if(value != null && !value.trim().equals(""))
            return false;
        return true;
    }
    public static String extractFirstLetters(String str){

        if(!isNullOrEmpty(str)){
            StringBuilder output = new StringBuilder();
            String[] parts = str.trim().split(" ");
            for (String part : parts) {
                if (!part.isEmpty()) {
                    output.append(part.charAt(0));
                }
            }
            return output.toString();
        }
        return "NON";
    }
}
