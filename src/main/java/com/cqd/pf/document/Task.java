package com.cqd.pf.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Getter
@Setter
@Builder
public class Task implements Serializable {

    @Id
    private String id;

    private Integer progress;

    private Integer position;

    private Integer typos;
}
