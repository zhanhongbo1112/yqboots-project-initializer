package com.yqboots.project.initializer.web.controller;

import com.yqboots.project.initializer.core.ProjectContext;
import com.yqboots.project.initializer.core.ProjectInitializer;
import com.yqboots.project.web.WebKeys;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
    public ProjectContext projectContext() {
        return new ProjectContext();
    }

    @RequestMapping
    public String index() {
        return HOME_URL;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Object startup(@ModelAttribute(WebKeys.MODEL) @Valid final ProjectContext context,
                          final BindingResult bindingResult,
                          final ModelMap model) throws IOException {
        if (bindingResult.hasErrors()) {
            return HOME_URL;
        }

        Path path = initializer.startup(context);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "zip", StandardCharsets.UTF_8));
        header.set("Content-Disposition", "attachment; filename=" + path.getFileName());
        header.setContentLength(Files.size(path));

        model.clear();

        final byte[] result = Files.readAllBytes(path);

        FileUtils.deleteDirectory((path.getParent().toFile()));

        return new HttpEntity<>(result, header);
    }
}
