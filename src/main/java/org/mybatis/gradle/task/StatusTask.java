package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.StatusCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.mybatis.gradle.CommandFactory;

import javax.inject.Inject;

public class StatusTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Status";

    @Inject
    public StatusTask(CommandFactory factory) {
        super(factory);
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        factory.create(StatusCommand.class, options).execute();
    }
}
