package org.mybatis.gradle.task;

import org.apache.ibatis.migration.commands.UpCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import org.mybatis.gradle.CommandFactory;

import javax.inject.Inject;

public class UpTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Up";
    private String steps;

    @Inject
    public UpTask(CommandFactory factory) {
        super(factory);
    }

    @Option(option = "steps", description = "Number of steps")
    public void setSteps(String steps) {
        this.steps = steps;
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        factory.create(UpCommand.class, options).execute(steps);
    }
}
