package com.example.chatbottest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindMatcher {
    private Matcher matcher;

    public boolean findMatcher(String message, String regex) {
        this.matcher = Pattern.compile(regex).matcher(message);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
