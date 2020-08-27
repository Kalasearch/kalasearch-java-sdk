package com.kalasearch.client;

import lombok.Builder;
import lombok.Data;

/**
 * @author tomsun28
 * @date 2020-08-27 23:09
 */
@Data
@Builder
public class TestDocument {

    private String name;

    private String actors;

    private String year;
}
