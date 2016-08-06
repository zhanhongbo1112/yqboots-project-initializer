package com.yqboots.project.initializer.core.builder;

import com.yqboots.project.initializer.core.ProjectContext;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by Administrator on 2016-06-08.
 */
public interface FileBuilder {
    String getTemplate();

    Path getFile(Path root, ProjectContext context) throws IOException;
}
