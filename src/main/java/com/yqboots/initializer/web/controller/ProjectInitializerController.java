/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.initializer.web.controller;

import com.yqboots.initializer.core.ProjectInitializer;
import com.yqboots.initializer.web.form.ProjectInitializerForm;
import com.yqboots.web.support.WebKeys;
import com.yqboots.web.util.FileWebUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
 * The controller for project initializer.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/projects/initializer")
public class ProjectInitializerController {
    public static final String HOME_URL = "projects/initializer/form";

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

        final MultipartFile file = form.getFile();
        if (StringUtils.isNotBlank(file.getName())) {
            try (InputStream inputStream = file.getInputStream()) {
                path = initializer.startup(form.getMetadata(), form.getTheme(), inputStream);
            }
        } else {
            path = initializer.startup(form.getMetadata(), form.getTheme());
        }

        final HttpEntity<byte[]> result = FileWebUtils.downloadFile(path, new MediaType("application", "zip",
                StandardCharsets.UTF_8));

        // clear temporary folder
        final Path parent = path.getParent();
        FileUtils.deleteDirectory(parent.toFile());

        // clear model
        model.clear();

        return result;
    }
}
