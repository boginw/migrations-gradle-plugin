package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.BootstrapCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.mybatis.gradle.CommandFactory;

import javax.inject.Inject;

public class BootstrapTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Bootstrap";

    @Inject
    public BootstrapTask(CommandFactory factory) {
        super(factory);
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        factory.create(BootstrapCommand.class, options).execute();
    }
}
