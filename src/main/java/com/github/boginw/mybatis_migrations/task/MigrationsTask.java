package com.github.boginw.mybatis_migrations.task;

import com.github.boginw.mybatis_migrations.ClassLoaderFactory;
import com.github.boginw.mybatis_migrations.CommandFactory;
import com.github.boginw.mybatis_migrations.MigrationsExtension;
import com.github.boginw.mybatis_migrations.PrintStreamFactory;
import org.apache.ibatis.migration.commands.BaseCommand;
import org.apache.ibatis.migration.options.SelectedOptions;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public abstract class MigrationsTask extends DefaultTask implements Runnable {
    public static final String TASK_PREFIX = "migrate";
    protected final CommandFactory factory;
    protected final PrintStreamFactory printStreamFactory;
    protected final ClassLoaderFactory classLoaderFactory;
    protected MigrationsExtension extension = getProject()
        .getExtensions()
        .getByType(MigrationsExtension.class);
    protected String output;

    protected MigrationsTask(
        CommandFactory factory,
        ClassLoaderFactory classLoaderFactory,
        PrintStreamFactory printStreamFactory
    ) {
        this.factory = factory;
        this.printStreamFactory = printStreamFactory;
        this.classLoaderFactory = classLoaderFactory;
    }

    public void setExtension(MigrationsExtension extension) {
        this.extension = extension;
    }

    @Option(option = "env", description = "Environment to configure")
    public void setEnvironment(String environment) {
        extension.getEnvironment().set(environment);
    }

    @Option(option = "output", description = "Path to where to output resulting script")
    public void setOutput(String output) {
        this.output = output;
    }

    @TaskAction
    public abstract void run();

    @Internal
    protected SelectedOptions getSelectedOptions() {
        SelectedOptions options = new SelectedOptions();
        options.setEnvironment(extension.getEnvironment().get());
        options.getPaths().setBasePath(extension.getBaseDir().getAsFile().get());
        options.setForce(extension.getForce().get());
        return options;
    }

    protected void executeCommandWithPrintStream(BaseCommand command, String... params) {
        try (PrintStream ignored = setPrintStreamOnCommand(command)) {
            command.execute(params);
        }
    }

    private PrintStream setPrintStreamOnCommand(BaseCommand command) {
        PrintStream printStream = new PrintStream(PrintStream.nullOutputStream());

        if (output != null) {
            try {
                printStream = printStreamFactory.makeFromFileName(output);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            command.setPrintStream(printStream);
        }

        return printStream;
    }
}
