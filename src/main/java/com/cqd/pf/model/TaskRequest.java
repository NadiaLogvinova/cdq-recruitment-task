package com.cqd.pf.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TaskRequest {

    @NotBlank(message = "The input string is required and can not be blank.")
    private String input;

    @NotBlank(message = "The pattern is required and can not be blank.")
    private String pattern;

    @Override
    public String toString() {
        return input.concat("_")
                .concat(pattern).concat("_")
                .concat(String.valueOf(input.length()));
    }
}
