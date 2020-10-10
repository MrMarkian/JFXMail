package com.email.Controller;

import com.email.EmailManager;
import com.email.View.ViewFactory;

public abstract class BaseController {

    protected EmailManager emailManager;
    protected ViewFactory viewFactory;
    private String fxmlName;
    private int windowID;

    public BaseController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public int getWindowID() {
        return windowID;
    }

    public void setWindowID(int windowID) {
        this.windowID = windowID;
    }

    public String getFXMLName(){
        return fxmlName;
    }
}
