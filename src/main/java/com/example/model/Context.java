package com.example.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Context class to hold tenant and site id
 * @author Ignitiv
 */
@Data
@AllArgsConstructor
public final class Context {

    private Integer tenantId;
    private Integer siteId;

}
