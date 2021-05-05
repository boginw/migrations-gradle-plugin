package com.github.boginw.mybatis_migrations.task;

import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;
import com.github.boginw.mybatis_migrations.ClassLoaderFactory;
import com.github.boginw.mybatis_migrations.CommandFactory;
import com.github.boginw.mybatis_migrations.MigrationsExtension;

public abstract class MigrationsTask extends DefaultTask implements Runnable {
    public static final String TASK_PREFIX = "migrate";
    protected final CommandFactory factory;
    protected ClassLoaderFactory classLoaderFactory;
    protected MigrationsExtension extension = getProject().getExtensions().getByType(MigrationsExtension.class);

    protected MigrationsTask(CommandFactory factory, ClassLoaderFactory classLoaderFactory) {
        this.factory = factory;
        this.classLoaderFactory = classLoaderFactory;
    }

    public void setExtension(MigrationsExtension extension) {
        this.extension = extension;
    }

    @TaskAction
    public abstract void run();

    @Internal
    protected SelectedOptions getSelectedOptions() {
        SelectedOptions options = new SelectedOptions();
        options.setEnvironment(extension.getEnvironment().get());
        options.getPaths().setBasePath(extension.getBaseDir().getAsFile().get());
        options.setForce(extension.getForce().get());
        return options;
    }
}
