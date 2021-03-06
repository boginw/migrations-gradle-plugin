package com.github.boginw.mybatis_migrations.task;

import com.github.boginw.mybatis_migrations.ClassLoaderFactory;
import com.github.boginw.mybatis_migrations.CommandFactory;
import com.github.boginw.mybatis_migrations.PrintStreamFactory;
import org.apache.ibatis.migration.commands.StatusCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;

public class StatusTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Status";

    @Inject
    public StatusTask(
        CommandFactory factory,
        ClassLoaderFactory classLoaderFactory,
        PrintStreamFactory printStreamFactory
    ) {
        super(factory, classLoaderFactory, printStreamFactory);
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        StatusCommand command = factory.create(StatusCommand.class, options);
        executeCommandWithPrintStream(command);
    }
}
