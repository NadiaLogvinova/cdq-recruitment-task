package com.cqd.pf.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TaskRequest {

    @NotBlank(message = "The input string is required and can't be blank.")
    private String input;

    @NotBlank(message = "The pattern is required and can't be blank.")
    @Size(max = 3)
    private String pattern;
}
