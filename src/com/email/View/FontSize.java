package com.email.View;

public enum FontSize {
    SMALL,
    MEDIUM,
    LARGE;

    public static String getCssPath(FontSize fontSize){
        return switch (fontSize) {
            case MEDIUM -> "CSS/fontMedium.css";
            case LARGE -> "CSS/fontBig.css";
            case SMALL -> "CSS/fontSmall.css";
        };
    }
}
