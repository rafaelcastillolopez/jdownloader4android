package org.jdownloader.plugins.controller;

import java.io.File;

import jd.plugins.Plugin;

public class PluginInfo<T extends Plugin> {

    private String   className;
    private Class<T> clazz;

    public PluginInfo(String className, Class<T> clazz) {
        this.className = className;
        this.clazz = clazz;
    }

    public String getClassName() {
        return className;
    }

    public Class<T> getClazz() {
        return clazz;
    }

}
