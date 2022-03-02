package com.github.boginw.mybatis_migrations.task;

import com.github.boginw.mybatis_migrations.ClassLoaderFactory;
import com.github.boginw.mybatis_migrations.CommandFactory;
import com.github.boginw.mybatis_migrations.PrintStreamFactory;
import org.apache.ibatis.migration.commands.ScriptCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import javax.inject.Inject;

public class ScriptTask extends MigrationsTask {
    public static final String TASK_NAME = TASK_PREFIX + "Script";
    private String from;
    private String to;

    @Inject
    public ScriptTask(
        CommandFactory factory,
        ClassLoaderFactory classLoaderFactory,
        PrintStreamFactory printStreamFactory
    ) {
        super(factory, classLoaderFactory, printStreamFactory);
    }

    @Option(option = "from", description = "Version to migrate from")
    public void setFrom(String from) {
        this.from = from;
    }

    @Option(option = "to", description = "Version to migrate to")
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    @TaskAction
    public void run() {
        SelectedOptions options = getSelectedOptions();
        ScriptCommand command = factory.create(ScriptCommand.class, options);

        String args = to != null ? from + " " + to : from;
        executeCommandWithPrintStream(command, args);
    }
}
