package com.yqboots.project.initializer.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-18.
 */
@SuppressWarnings("serial")
public class DomainMetadata implements Serializable {
    private final String module;

    private final String name;

    private final List<DomainMetadataProperty> properties;

    public DomainMetadata(final String module, final String name, final List<DomainMetadataProperty> properties) {
        this.module = module;
        this.name = name;
        this.properties = properties;
    }

    public String getModule() {
        return module;
    }

    public String getName() {
        return name;
    }

    public List<DomainMetadataProperty> getProperties() {
        return properties;
    }
}
