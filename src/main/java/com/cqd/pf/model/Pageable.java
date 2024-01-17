package com.cqd.pf.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema
public class Pageable {

    static final int DEFAULT_PAGE_SIZE = 10;

    @Schema(example = "0")
    @Min(0)
    private int page;

    @Schema(example = "10")
    @Min(1)
    private int size = DEFAULT_PAGE_SIZE;
}
