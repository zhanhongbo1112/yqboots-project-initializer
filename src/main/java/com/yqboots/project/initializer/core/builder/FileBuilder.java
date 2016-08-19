package com.yqboots.project.initializer.core.builder;

import com.yqboots.project.initializer.core.DomainMetadata;
import com.yqboots.project.initializer.core.ProjectMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Administrator on 2016-06-08.
 */
public interface FileBuilder {
    Path getFile(Path root, ProjectMetadata metadata) throws IOException;

    Path getFile(Path root, ProjectMetadata metadata, DomainMetadata domainMetadata) throws IOException;

    List<Path> getFiles(Path root, ProjectMetadata metadata, InputStream inputStream) throws IOException;
}
