package com.cqd.pf.utils;

import com.cqd.pf.model.TaskRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.function.IntConsumer;

@Component
@AllArgsConstructor
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MatcherService {

    private MatcherService self;

    public MatchResult findBestMatch(TaskRequest taskRequest, IntConsumer progressConsumer) {
        String input = taskRequest.getInput();
        String pattern = taskRequest.getPattern();

        int position = 0;
        int minTypos = pattern.length();

        for (int i = 0; i <= input.length() - pattern.length(); i++) {
            progressConsumer.accept(100 * i / (input.length() - pattern.length() + 1));
            int typos = self.getTypos(input.substring(i, i + pattern.length()), pattern);
            minTypos = Math.min(minTypos, typos);
            if (minTypos == 0) {
                new MatchResult(position, typos);
            }
        }
        return new MatchResult(position, minTypos);
    }

    @SneakyThrows
    @Cacheable(value = "substring_matcher")
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
