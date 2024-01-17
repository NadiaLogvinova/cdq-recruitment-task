package com.cqd.pf.service.impl;

import com.cqd.pf.config.CacheConfig;
import com.cqd.pf.model.TaskRequest;
import com.cqd.pf.model.MatchResult;
import com.cqd.pf.service.MatcherService;
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
public class MatcherServiceImpl implements MatcherService {

    private MatcherServiceImpl self;

    // TODO: check input >= pattern
    public MatchResult findBestMatch(TaskRequest taskRequest, IntConsumer progressConsumer) {
        String input = taskRequest.getInput();
        String pattern = taskRequest.getPattern();

        int position = 0;
        int minTypos = pattern.length();

        for (int i = 0; i <= input.length() - pattern.length(); i++) {
            progressConsumer.accept(100 * i / (input.length() - pattern.length() + 1));
            int typos = self.getTypos(input.substring(i, i + pattern.length()), pattern);
            if (typos == 0) {
                return new MatchResult(i, 0);
            }
            if (typos < minTypos) {
                position = i;
                minTypos = typos;
            }
        }
        return new MatchResult(position, minTypos);
    }

    @SneakyThrows
    @Cacheable(value = "substring_matcher", key = CacheConfig.SUBSTRING_MATCHER_CACHE_KEY_PATTERN)
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
