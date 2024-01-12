package com.cqd.pf.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
public class Task {

    @Id
    private String id;

    private Integer progress;

    private Integer position;

    private Integer typos;
}
