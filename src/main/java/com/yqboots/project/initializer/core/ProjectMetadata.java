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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-05-28.
 */
@SuppressWarnings("serial")
public class ProjectMetadata implements Serializable {
    @NotEmpty
    @Length(max = 30)
    private String name;

    @Length(max = 64)
    private String description;

    @NotEmpty
    @Length(max = 30)
    private String groupId;

    @NotEmpty
    @Length(max = 20)
    private String artifactId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return StringUtils.defaultIfEmpty(this.description, StringUtils.EMPTY);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getGroupPath() {
        return StringUtils.replace(getGroupId(), ".", "/");
    }
}
