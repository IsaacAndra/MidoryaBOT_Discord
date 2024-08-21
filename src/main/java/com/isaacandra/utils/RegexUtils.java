package com.isaacandra.utils;

public class RegexUtils {
    public static final String SPECIAL_CHARACTERES_REGEX = "^[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]+$";

    public String getSpecialCharacteresRegex(){
        return SPECIAL_CHARACTERES_REGEX;
    }
}
