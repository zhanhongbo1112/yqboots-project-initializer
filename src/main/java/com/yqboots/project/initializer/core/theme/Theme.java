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
package com.yqboots.project.initializer.core.theme;

import java.io.Serializable;

/**
 * The displaying theme, which contains skin and color selected.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class Theme implements Serializable {
    /**
     * The skin.
     */
    private ThemeSkin skin = ThemeSkin.LIGHT;

    /**
     * The color.
     */
    private ThemeColor color = ThemeColor.GREEN;

    /**
     * Gets the skin.
     *
     * @return the skin
     */
    public ThemeSkin getSkin() {
        return skin;
    }

    /**
     * Sets the skin.
     *
     * @param skin the skin
     */
    public void setSkin(ThemeSkin skin) {
        this.skin = skin;
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public ThemeColor getColor() {
        return color;
    }

    /**
     * Sets the color.
     *
     * @param color the color
     */
    public void setColor(ThemeColor color) {
        this.color = color;
    }
}
