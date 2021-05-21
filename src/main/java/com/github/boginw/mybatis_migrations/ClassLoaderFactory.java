package com.github.boginw.mybatis_migrations;

import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderFactory {
    public ClassLoader getClassLoader(Project project) {
        URL[] urls = project.getConvention()
            .getPlugin(JavaPluginConvention.class)
            .getSourceSets()
            .getByName(SourceSet.MAIN_SOURCE_SET_NAME)
            .getRuntimeClasspath()
            .getFiles()
            .stream().map(t -> {
                try {
                    return t.toURI().toURL();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }).toArray(URL[]::new);

        return new URLClassLoader(urls);
    }
}
