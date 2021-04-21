package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.PendingCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.mybatis.gradle.CommandFactory;

import javax.inject.Inject;

public class PendingTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Pending";

    @Inject
    public PendingTask(CommandFactory factory) {
        super(factory);
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        factory.create(PendingCommand.class, options).execute();
    }
}
