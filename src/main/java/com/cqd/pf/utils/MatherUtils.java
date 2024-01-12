package com.cqd.pf.utils;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class MatherUtils {

    @SneakyThrows
    public int getTypos(String input, String pattern) {
        Thread.sleep(1000);

        int typos = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != pattern.charAt(i)) {
                typos++;
            }
        }
        return typos;
    }
}
