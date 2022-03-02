package com.github.boginw.mybatis_migrations.task;

import com.github.boginw.mybatis_migrations.ClassLoaderFactory;
import com.github.boginw.mybatis_migrations.CommandFactory;
import com.github.boginw.mybatis_migrations.PrintStreamFactory;
import org.apache.ibatis.migration.commands.NewCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import javax.inject.Inject;

public class NewTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "New";
    private String name;

    @Inject
    public NewTask(
        CommandFactory factory,
        ClassLoaderFactory classLoaderFactory,
        PrintStreamFactory printStreamFactory
    ) {
        super(factory, classLoaderFactory, printStreamFactory);
    }

    @Option(option = "name", description = "Name of the migration")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        NewCommand command = factory.create(NewCommand.class, options);
        executeCommandWithPrintStream(command, name);
    }
}
