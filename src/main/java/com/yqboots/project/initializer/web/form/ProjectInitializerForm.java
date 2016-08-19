package com.yqboots.project.initializer.web.form;

import com.yqboots.project.initializer.core.ProjectMetadata;
import com.yqboots.project.initializer.core.theme.Theme;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by Administrator on 2016-06-04.
 */
@SuppressWarnings("serial")
public class ProjectInitializerForm implements Serializable {
    @Valid
    private ProjectMetadata metadata;

    private Theme theme;

    private MultipartFile file;

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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(final MultipartFile file) {
        this.file = file;
    }
}
