package com.cqd.pf.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema
public class Pageable {

    @Schema(example = "0")
    private int page;

    @Schema(example = "10")
    private int size;
}
