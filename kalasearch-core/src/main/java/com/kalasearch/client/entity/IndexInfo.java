package com.kalasearch.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * index info entity
 * @author tomsun28
 * @date 2020-08-28 01:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexInfo {

    private String[] numericalFields;

    private String[] stringFields;

    private String[] booleanFields;

    private Integer numOfDocs;
}
