package com.email.View;

public enum ColorTheme {
    LIGHT,
    DEFAULT,
    DARK;

    public static String getCssPath(ColorTheme colorTheme){
        return switch (colorTheme){
            case LIGHT -> "CSS/themeLight.css";

            case DEFAULT -> "CSS/themeDefault.css";
            case DARK -> "CSS/themeDark.css";
        };
    }
}
