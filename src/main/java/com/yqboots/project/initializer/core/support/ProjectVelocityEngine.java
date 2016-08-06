package com.yqboots.project.initializer.core.support;

import com.yqboots.project.initializer.core.builder.FileBuilder;
import org.apache.velocity.app.VelocityEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-08.
 */
public class ProjectVelocityEngine extends VelocityEngine {
    private List<FileBuilder> builders = new ArrayList<>();

    public ProjectVelocityEngine(final List<FileBuilder> builders) {
        super();
        this.builders = builders;
    }

    public List<FileBuilder> getBuilders() {
        return builders;
    }
}