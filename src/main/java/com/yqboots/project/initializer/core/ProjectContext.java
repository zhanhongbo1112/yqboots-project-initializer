package com.yqboots.project.initializer.core;

import com.yqboots.project.initializer.core.theme.Theme;

import javax.validation.Valid;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by Administrator on 2016-06-04.
 */
@SuppressWarnings("serial")
public class ProjectContext implements Serializable {
    public static final String KEY = "context";

    @Valid
    private ProjectMetadata metadata;

    private Theme theme;

    private InputStream file;

    public ProjectMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ProjectMetadata metadata) {
        this.metadata = metadata;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(final InputStream file) {
        this.file = file;
    }
}
