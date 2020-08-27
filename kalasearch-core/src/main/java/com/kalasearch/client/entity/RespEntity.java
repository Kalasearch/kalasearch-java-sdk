package com.kalasearch.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * entity for resp body
 * @author tomsun28
 * @date 2020-08-26 23:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespEntity {

    private String id;

    private String processedAt;

    private String operation;

    private String status;

    private String hasErrors;

    private Object results;

    private Integer totalHits;

    private Object[] hits;

    private Integer queryTimeUsed;

}
