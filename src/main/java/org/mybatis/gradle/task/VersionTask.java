package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.VersionCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import org.mybatis.gradle.ClassLoaderFactory;
import org.mybatis.gradle.CommandFactory;

import javax.inject.Inject;

public class VersionTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Version";
    private String version;

    @Inject
    public VersionTask(CommandFactory factory, ClassLoaderFactory classLoaderFactory) {
        super(factory, classLoaderFactory);
    }

    @Option(option = "version", description = "Version to migrate to")
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        VersionCommand command = factory.create(VersionCommand.class, options);
        command.setDriverClassLoader(classLoaderFactory.getClassLoader(getProject()));
        command.execute(version);
    }
}
