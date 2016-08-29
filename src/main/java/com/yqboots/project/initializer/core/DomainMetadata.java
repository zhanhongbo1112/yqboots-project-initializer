/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.project.initializer.core;

import java.io.Serializable;
import java.util.List;

/**
 * The domain metadata.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class DomainMetadata implements Serializable {
    /**
     * The module which the domain will be in.
     */
    private final String module;

    /**
     * The domain name. Should be a Java-like naming.
     */
    private final String name;

    /**
     * List of properties the domain has.
     */
    private final List<DomainMetadataProperty> properties;

    /**
     * Constructs the DomainMetadata.
     *
     * @param module     the module which the domain will be in.
     * @param name       the domain name
     * @param properties list of properties the domain has
     */
    public DomainMetadata(final String module, final String name, final List<DomainMetadataProperty> properties) {
        this.module = module;
        this.name = name;
        this.properties = properties;
    }

    /**
     * Gets the the module which the domain will be in.
     *
     * @return the module name
     */
    public String getModule() {
        return module;
    }

    /**
     * Gets the domain name.
     *
     * @return the domain name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the properties the domain has.
     *
     * @return the properties
     */
    public List<DomainMetadataProperty> getProperties() {
        return properties;
    }
}
