package com.cqd.pf.errorhandling.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {

    public static final String TASK_DOES_NOT_EXIST = "Task '%s' does not exist";

    public static final String INPUT_LONGER_THAN_PATTERN = "The input can not be longer than the pattern";
}
