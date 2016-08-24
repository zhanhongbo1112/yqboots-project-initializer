package com.yqboots.project.initializer.web.controller;

import com.yqboots.project.fss.web.util.FssWebUtils;
import com.yqboots.project.initializer.web.form.ProjectInitializerForm;
import com.yqboots.project.initializer.core.ProjectInitializer;
import com.yqboots.project.web.WebKeys;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Created by Administrator on 2016-06-04.
 */
@Controller
@RequestMapping(value = "/project/initializer")
public class ProjectInitializerController {

    public static final String HOME_URL = "project/initializer/form";

    @Autowired
    private ProjectInitializer initializer;

    @ModelAttribute(WebKeys.MODEL)
    public ProjectInitializerForm projectContext() {
        return new ProjectInitializerForm();
    }

    @RequestMapping
    public String index() {
        return HOME_URL;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Object startup(@ModelAttribute(WebKeys.MODEL) @Valid final ProjectInitializerForm form,
                          final BindingResult bindingResult,
                          final ModelMap model) throws IOException {
        if (bindingResult.hasErrors()) {
            return HOME_URL;
        }

        Path path;

        MultipartFile file = form.getFile();
        if (file != null) {
            try (InputStream inputStream = file.getInputStream()) {
                path = initializer.startup(form.getMetadata(), form.getTheme(), inputStream);
            }
        } else {
            path = initializer.startup(form.getMetadata(), form.getTheme());
        }

        HttpEntity<byte[]> result = FssWebUtils.downloadFile(path, new MediaType("application", "zip",
                StandardCharsets.UTF_8));

        // clear temporary folder
        Path parent = path.getParent();
        FileUtils.deleteDirectory(parent.toFile());

        // clear model
        model.clear();

        return result;
    }
}
