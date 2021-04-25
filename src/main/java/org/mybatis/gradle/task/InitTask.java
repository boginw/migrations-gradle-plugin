package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.InitializeCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import org.mybatis.gradle.ClassLoaderFactory;
import org.mybatis.gradle.CommandFactory;

import javax.inject.Inject;

public class InitTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Init";
    private String idPattern;

    @Inject
    public InitTask(CommandFactory factory, ClassLoaderFactory classLoaderFactory) {
        super(factory, classLoaderFactory);
    }

    @Option(option = "idPattern", description = "Default file prefix")
    public void setIdPattern(String idPattern) {
        this.idPattern = idPattern;
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        options.setIdPattern(idPattern);
        InitializeCommand command = factory.create(InitializeCommand.class, options);
        command.setDriverClassLoader(classLoaderFactory.getClassLoader(getProject()));
        command.execute();
    }
}
