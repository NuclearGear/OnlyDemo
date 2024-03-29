package com.pajk.utils.cucumber;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shellwords {
    private static final Pattern SHELLWORDS_PATTERN = Pattern.compile("[^\\s']+|'([^']*)'");

    public static List<String> parse(final String cmdline) {
        final List<String> matchList = new ArrayList<String>();
        final Matcher shellwordsMatcher = SHELLWORDS_PATTERN.matcher(cmdline);
        while (shellwordsMatcher.find()) {
            if (shellwordsMatcher.group(1) != null) {
                matchList.add(shellwordsMatcher.group(1));
            } else {
                matchList.add(shellwordsMatcher.group());
            }
        }
        return matchList;
    }
}
