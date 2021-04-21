package org.mybatis.gradle.task;

import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.mybatis.gradle.CommandFactory;
import org.mybatis.gradle.MigrationsExtension;

public abstract class MigrationsTask extends DefaultTask implements Runnable {
    public static final String TASK_PREFIX = "migrate";
    protected final CommandFactory factory;
    protected MigrationsExtension extension = MigrationsExtension.defaultValues();

    protected MigrationsTask(CommandFactory factory) {
        this.factory = factory;
    }

    public void setExtension(MigrationsExtension extension) {
        this.extension = extension;
    }

    @TaskAction
    public abstract void run();

    protected SelectedOptions getSelectedOptions() {
        SelectedOptions options = new SelectedOptions();
        options.setEnvironment(extension.getEnvironment().get());
        options.getPaths().setBasePath(extension.getBaseDir().get());
        options.setForce(extension.getForce().get());
        return options;
    }
}
