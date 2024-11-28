package com.github.boginw.mybatis_migrations;

import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.stream.Stream;

public class ClassLoaderFactory {
    public ClassLoader getClassLoader(Project project) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        Stream<File> mainFiles = getMainSourceSetFiles(project);
        Stream<File> migrationFiles = getMigrationsRuntimeFiles(project);

        URL[] urls = Stream.concat(mainFiles, migrationFiles).distinct().map(t -> {
            try {
                return t.toURI().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }).toArray(URL[]::new);

        return new URLClassLoader(urls, contextClassLoader);
    }

    private Stream<File> getMigrationsRuntimeFiles(Project project) {
        return project.getConfigurations()
            .getByName("migrationsRuntime")
            .getFiles()
            .stream();
    }

    private Stream<File> getMainSourceSetFiles(Project project) {
        var extension = Objects.requireNonNull(
            project.getExtensions().findByType(JavaPluginExtension.class)
        );

        return extension
            .getSourceSets()
            .getByName(SourceSet.MAIN_SOURCE_SET_NAME)
            .getRuntimeClasspath()
            .getFiles()
            .stream();
    }
}
