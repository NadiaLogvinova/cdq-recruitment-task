package com.cqd.pf.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class TaskRequest {

    @NotBlank(message = "The input string is required and can not be blank.")
    private String input;

    @NotBlank(message = "The pattern is required and can not be blank.")
    private String pattern;

}
