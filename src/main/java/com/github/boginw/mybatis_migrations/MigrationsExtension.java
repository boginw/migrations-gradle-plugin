package com.github.boginw.mybatis_migrations;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

public class MigrationsExtension {
    private final DirectoryProperty baseDir;
    private final Property<String> environment;
    private final Property<Boolean> force;

    @Inject
    public MigrationsExtension(ObjectFactory factory, ProjectLayout layout) {
        this.baseDir = factory.directoryProperty().convention(layout.getProjectDirectory().dir("migrations"));
        this.environment = factory.property(String.class).convention("development");
        this.force = factory.property(Boolean.class).convention(false);
    }

    public DirectoryProperty getBaseDir() {
        return baseDir;
    }

    public Property<String> getEnvironment() {
        return environment;
    }

    public Property<Boolean> getForce() {
        return force;
    }
}
