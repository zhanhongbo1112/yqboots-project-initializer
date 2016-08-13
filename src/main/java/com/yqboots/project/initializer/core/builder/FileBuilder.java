package com.yqboots.project.initializer.core.builder;

import com.yqboots.project.initializer.core.ProjectContext;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Administrator on 2016-06-08.
 */
public interface FileBuilder {
    Path getFile(Path root, ProjectContext context) throws IOException;

    List<Path> getFiles(Path root, ProjectContext context) throws IOException;
}
