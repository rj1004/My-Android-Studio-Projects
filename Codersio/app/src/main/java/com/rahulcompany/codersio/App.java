package com.rahulcompany.codersio;

import android.app.Application;

import io.github.kbiakov.codeview.classifier.CodeProcessor;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CodeProcessor.init(this);
    }
}
