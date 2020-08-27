package com.kalasearch.client.entity;

import lombok.Builder;
import lombok.Data;

/**
 * entity for search query req body
 * @author tomsun28
 * @date 2020-08-27 07:49
 */
@Data
@Builder
public class QueryInfo {

    private String query;

    private String[] filters;

    private String[] searchableFields;

    private String[] highlightFields;

    private Integer page;

    private Integer hitsPerPage;
}
