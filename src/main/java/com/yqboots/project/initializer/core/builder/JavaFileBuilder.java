package com.yqboots.project.initializer.core.builder;

import com.yqboots.project.initializer.core.DomainMetadata;
import com.yqboots.project.initializer.core.ProjectMetadata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2016-06-12.
 */
public class JavaFileBuilder extends FileTemplateBuilder {
    public static final String ROOT = "/src/main/java";

    public JavaFileBuilder(final String template, final String path) {
        super(template, path);
    }

    @Override
    public Path getFile(final Path root, final ProjectMetadata metadata) throws IOException {
        // dynamic calculate the java file path based on groupId
        final String groupPath = metadata.getGroupPath();
        Path _root = Paths.get(root.toAbsolutePath() + ROOT + File.separator + groupPath);

        return super.getFile(_root, metadata);
    }

    @Override
    public Path getFile(final Path root, final ProjectMetadata metadata, final DomainMetadata domainMetadata)
            throws IOException {
        // dynamic calculate the java file path based on groupId
        final String groupPath = metadata.getGroupPath();
        Path _root = Paths.get(root.toAbsolutePath() + ROOT + File.separator + groupPath + File.separator
                + domainMetadata.getModule());

        return super.getFile(_root, metadata);
    }
}
