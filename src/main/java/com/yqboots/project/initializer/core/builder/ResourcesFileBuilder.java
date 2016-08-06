package com.yqboots.project.initializer.core.builder;

import com.yqboots.project.initializer.core.ProjectContext;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2016-06-12.
 */
public class ResourcesFileBuilder extends FileBuilderImpl {
    public static final String ROOT = "/src/main/resources";

    public ResourcesFileBuilder(final String template, final String path) {
        super(template, path);
    }

    @Override
    public Path getFile(final Path root, final ProjectContext context) throws IOException {
        Path _root = Paths.get(root.toAbsolutePath() + ROOT);

        return super.getFile(_root, context);
    }
}
