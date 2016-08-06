package com.yqboots.project.initializer.core.theme;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-06-04.
 */
@SuppressWarnings("serial")
public class Theme implements Serializable {
    private ThemeSkin skin = ThemeSkin.LIGHT;

    private ThemeColor color = ThemeColor.GREEN;

    public ThemeSkin getSkin() {
        return skin;
    }

    public void setSkin(ThemeSkin skin) {
        this.skin = skin;
    }

    public ThemeColor getColor() {
        return color;
    }

    public void setColor(ThemeColor color) {
        this.color = color;
    }
}
