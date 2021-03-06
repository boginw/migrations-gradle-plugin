package com.github.boginw.mybatis_migrations.task;

import com.github.boginw.mybatis_migrations.ClassLoaderFactory;
import com.github.boginw.mybatis_migrations.CommandFactory;
import com.github.boginw.mybatis_migrations.PrintStreamFactory;
import org.apache.ibatis.migration.commands.DownCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import javax.inject.Inject;

public class DownTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Down";
    private String steps;

    @Inject
    public DownTask(
        CommandFactory factory,
        ClassLoaderFactory classLoaderFactory,
        PrintStreamFactory printStreamFactory
    ) {
        super(factory, classLoaderFactory, printStreamFactory);
    }

    @Option(option = "steps", description = "Number of steps")
    public void setSteps(String steps) {
        this.steps = steps;
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        DownCommand command = factory.create(DownCommand.class, options);
        executeCommandWithPrintStream(command, steps);
    }
}
