package com.yqboots.project.initializer.core.builder;

import com.yqboots.project.initializer.core.ProjectContext;

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
    public Path getFile(final Path root, final ProjectContext context) throws IOException {
        // dynamic calculate the java file path based on groupId
        final String groupPath = context.getMetadata().getGroupPath();
        Path _root = Paths.get(root.toAbsolutePath() + ROOT + File.separator + groupPath);

        return super.getFile(_root, context);
    }
}
