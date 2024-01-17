package com.cqd.pf.service.impl;

import com.cqd.pf.model.MatchResult;
import com.cqd.pf.model.TaskRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.IntConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatcherServiceImplTest {

    @Mock
    private MatcherServiceImpl matcherService;

    @Mock
    private IntConsumer intConsumer;

    @InjectMocks
    private MatcherServiceImpl matcherServiceImpl;

    @Test
    void findBestMatch_returnAfterBestMatch() {
        when(matcherService.getTypos(anyString(), anyString())).thenReturn(0);

        MatchResult bestMatch = matcherServiceImpl.findBestMatch(new TaskRequest("123", "0"), intConsumer);

        assertEquals(0, bestMatch.getPosition());
        assertEquals(0, bestMatch.getTypo());

        verify(intConsumer).accept(0);

        verify(matcherService).getTypos("1", "0");
        verifyNoMoreInteractions(intConsumer, matcherService);
    }

    @Test
    void findBestMatch_patternNotFound() {
        when(matcherService.getTypos(anyString(), anyString())).thenCallRealMethod();

        MatchResult bestMatch = matcherServiceImpl.findBestMatch(new TaskRequest("12345", "00"), intConsumer);

        assertEquals(0, bestMatch.getPosition());
        assertEquals(2, bestMatch.getTypo());

        verify(intConsumer).accept(0);
        verify(intConsumer).accept(25);
        verify(intConsumer).accept(50);
        verify(intConsumer).accept(75);

        verify(matcherService).getTypos("12", "00");
        verify(matcherService).getTypos("23", "00");
        verify(matcherService).getTypos("34", "00");
        verify(matcherService).getTypos("45", "00");
        verifyNoMoreInteractions(intConsumer, matcherService);
    }

    @Test
    void findBestMatchRequirementCases() {
        when(matcherService.getTypos(anyString(), anyString())).thenCallRealMethod();

        MatchResult bestMatch = matcherServiceImpl.findBestMatch(new TaskRequest("ABCD", "BCD"), intConsumer);
        assertEquals(1, bestMatch.getPosition());
        assertEquals(0, bestMatch.getTypo());

        bestMatch = matcherServiceImpl.findBestMatch(new TaskRequest("ABCD", "BWD"), intConsumer);
        assertEquals(1, bestMatch.getPosition());
        assertEquals(1, bestMatch.getTypo());

        bestMatch = matcherServiceImpl.findBestMatch(new TaskRequest("ABCDEFG", "CFG"), intConsumer);
        assertEquals(4, bestMatch.getPosition());
        assertEquals(1, bestMatch.getTypo());

        bestMatch = matcherServiceImpl.findBestMatch(new TaskRequest("ABCABC", "ABC"), intConsumer);
        assertEquals(0, bestMatch.getPosition());
        assertEquals(0, bestMatch.getTypo());

        bestMatch = matcherServiceImpl.findBestMatch(new TaskRequest("ABCDEFG", "TDD"), intConsumer);
        assertEquals(1, bestMatch.getPosition());
        assertEquals(2, bestMatch.getTypo());

        bestMatch = matcherServiceImpl.findBestMatch(new TaskRequest("ABCDEF", "XABC"), intConsumer);
        assertEquals(0, bestMatch.getPosition());
        assertEquals(4, bestMatch.getTypo());

        bestMatch = matcherServiceImpl.findBestMatch(new TaskRequest("ABCDEF", "DEFX"), intConsumer);
        assertEquals(0, bestMatch.getPosition());
        assertEquals(4, bestMatch.getTypo());
    }

    @Test
    void getTypos() {
        assertEquals(0, matcherServiceImpl.getTypos("11111", "11111"));
        assertEquals(1, matcherServiceImpl.getTypos("11111", "11112"));
        assertEquals(2, matcherServiceImpl.getTypos("11111", "12121"));
        assertEquals(3, matcherServiceImpl.getTypos("11111", "21212"));
        assertEquals(4, matcherServiceImpl.getTypos("11111", "22122"));
        assertEquals(5, matcherServiceImpl.getTypos("11111", "22222"));
    }
}