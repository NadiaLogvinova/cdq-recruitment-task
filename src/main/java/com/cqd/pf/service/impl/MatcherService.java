package com.cqd.pf.service.impl;

import com.cqd.pf.model.TaskRequest;
import com.cqd.pf.model.MatchResult;

import java.util.function.IntConsumer;

public interface MatcherService {

    MatchResult findBestMatch(TaskRequest taskRequest, IntConsumer progressConsumer);

}
