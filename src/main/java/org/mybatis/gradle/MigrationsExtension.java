package org.mybatis.gradle;

import org.gradle.api.internal.provider.DefaultProperty;
import org.gradle.api.internal.provider.PropertyHost;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

import java.io.File;

public abstract class MigrationsExtension {
    @Input
    abstract public Property<File> getBaseDir();

    @Input
    abstract public Property<String> getEnvironment();

    @Input
    abstract public Property<Boolean> getForce();

    public static MigrationsExtension defaultValues() {
        return new MigrationsExtension() {
            private final Property<Boolean> force = getDefaultValue(Boolean.class, false);
            private final Property<String> environment = getDefaultValue(String.class, "development");
            private final Property<File> baseDir = getDefaultValue(File.class, new File("./"));

            @Override
            public Property<File> getBaseDir() {
                return baseDir;
            }

            @Override
            public Property<String> getEnvironment() {
                return environment;
            }

            @Override
            public Property<Boolean> getForce() {
                return force;
            }

            private <T> Property<T> getDefaultValue(Class<T> klass, T value) {
                return new DefaultProperty<>(PropertyHost.NO_OP, klass).value(value);
            }
        };
    }
}
