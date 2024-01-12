package com.cqd.pf.utils;

import org.springframework.stereotype.Component;

@Component
public class MatherUtils {

    public MatcherResult findFirstBestMath(String input, String pattern) {
        int position = 0;
        int typos = pattern.length();

        for (int i = 0; i <= input.length() - pattern.length(); i++) {
            String inputPart = input.substring(i, i + pattern.length());

            int typoCount = 0;
            for (int j = 0; j < inputPart.length(); j++) {
                if (inputPart.charAt(j) != pattern.charAt(j)) {
                    typoCount++;
                }
            }
            if (typoCount == 0) {
                position = i;
                typos = 0;
                return new MatcherResult(position, typos);
            }
            if (typoCount < typos) {
                position = i;
                typos = typoCount;
            }
        }
        return new MatcherResult(position, typos);
    }
}
